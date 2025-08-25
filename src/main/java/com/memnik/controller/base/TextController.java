package com.memnik.controller.base;

import com.memnik.common.CurrentUserResolver;
import com.memnik.common.constants.Languages;
import com.memnik.dao.base.BaseEntity;
import com.memnik.dao.base.EntityCreator;
import com.memnik.dto.BaseDto;
import com.memnik.service.base.BaseService;
import com.memnik.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.memnik.common.CommonUtils.tagNamesToString;
import static com.memnik.common.constants.Constants.*;

public abstract class TextController<T extends BaseEntity> {
    @Autowired
    protected BaseService<T> service;
    @Autowired
    protected TagService tagService;
    @Autowired
    protected CurrentUserResolver currentUserResolver;
    @Autowired
    protected EntityCreator entityCreator;

    @GetMapping(FIND_URL)
    public String find(Model model) {
        String language = currentUserResolver.getCurrentUser().getLanguage();
        Optional<BaseDto> baseDto = service.findNew(language, List.of(), ANY_AUTHOR);
        if(baseDto.isEmpty()) {
            model.addAttribute("error", "Can't find random %s for language %s"
                    .formatted(getEntityName(), language));
        } else {
            model.addAttribute(getTypeName(), getInformation(baseDto.get()));
            model.addAttribute("email", currentUserResolver.getCurrentUser().getEmail());
        }

        addFindModelAttributes(model, baseDto);
        return "find-page";
    }

    @PostMapping(FIND_URL)
    public String findNew(@RequestParam("language") String language,
                          @RequestParam(value = "tags", required=false) List<String> tags,
                          @RequestParam(value = "author") String author,
                          Model model) {
        author = (author.equals(ANY_AUTHOR)) ? author : currentUserResolver.getCurrentUser().getName();

        Optional<BaseDto> baseDto = service.findNew(language, tags, author);
        if(baseDto.isEmpty()) {
            model.addAttribute("error", "Can't find new %s with language=%s, tags=%s, author=%s"
                    .formatted(getEntityName(), language, tagNamesToString(tags), author));
        } else {
            model.addAttribute(getTypeName(), getInformation(baseDto.get()));
            model.addAttribute("email", currentUserResolver.getCurrentUser().getEmail());
        }

        addFindModelAttributes(model, baseDto);
        return "find-page";
    }

    private void addFindModelAttributes(Model model, Optional<BaseDto> baseDto) {
        if(baseDto.isPresent()) {
            model.addAttribute("title", "New %s for you (#%s)".formatted(getEntityName(), baseDto.get().getId()));
        } else {
            model.addAttribute("title", "New %s for you".formatted(getEntityName()));
        }

        model.addAttribute("title2", "Find another %s".formatted(getEntityName()));
        model.addAttribute("tags", getFilteredTagNames());
    }

    @GetMapping(ADD_URL)
    public String add(Model model) {
        addAddModelAttributes(model);
        return "add-page";
    }

    @PostMapping(ADD_URL)
    public String addNew(@RequestParam(value = "language", required=false) String language,
                         @RequestParam(value = "tags", required=false) List<String> tags,
                         @RequestParam(value = "text", required=false) String text,
                         @RequestParam(value = "image", required = false) MultipartFile file,
                         Model model) {
        if(language == null || language.isEmpty()) {
            language = Languages.ANY.name();
        }
        if(tags == null || tags.isEmpty()) {
            model.addAttribute("error", "Tags are required");
        } else if(text == null || text.isBlank()) {
            model.addAttribute("error", "Text is required");
        } else {
            String author = currentUserResolver.getCurrentUser().getName();
            service.addNew(language, author, tags, text, getEntity());
            model.addAttribute("error", "New %s was added".formatted(getEntityName()));
        }

        addAddModelAttributes(model);
        return "add-page";
    }

    protected void addAddModelAttributes(Model model) {
        model.addAttribute("title", "Add new %s".formatted(getEntityName()));
        model.addAttribute("tags", tagService.getTagNames());
        model.addAttribute("type", getTypeName());
    }

    public abstract T getEntity();
    public abstract String getEntityName();
    public abstract List<String> getFilteredTagNames();
    public abstract String getTypeName();
    public abstract String getInformation(BaseDto baseDto);
}

