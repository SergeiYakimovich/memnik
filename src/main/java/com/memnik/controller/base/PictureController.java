package com.memnik.controller.base;

import com.memnik.common.constants.Languages;
import com.memnik.dao.base.BaseEntity;
import com.memnik.dto.BaseDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.memnik.common.constants.Constants.*;

public abstract class PictureController<T extends BaseEntity> extends TextController<T> {

    @PostMapping(SEND_URL)
    public String send(@RequestParam String email, @RequestParam String picture, Model model) {
        String userName = currentUserResolver.getCurrentUser().getName();
        String pictureAddress = picture.substring(1);
        if(service.sendPictureToEmail(email, pictureAddress, userName)) {
            model.addAttribute("error", "%s was sent to %s".formatted(getEntityName(), email));
        } else {
            model.addAttribute("error", "Can't send %s to %s".formatted(getEntityName(), email));
        }
        model.addAttribute("title", "Sending %s".formatted(getEntityName()));

        return "main";
    }

    @Override
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
        } else if(file == null || file.isEmpty() || file.getSize() == 0 || file.getSize() > 10_000_000) {
            model.addAttribute("error", "Need file with size less than 10MB");
        } else {
            String author = currentUserResolver.getCurrentUser().getName();
            service.addNewFile(language, author, tags, file, getEntity());
            model.addAttribute("error", "New %s was added".formatted(getEntityName()));
        }

        addAddModelAttributes(model);
        return "add-page";
    }

}
