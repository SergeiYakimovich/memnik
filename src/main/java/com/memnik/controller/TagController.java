package com.memnik.controller;

import com.memnik.common.CurrentUserResolver;
import com.memnik.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.memnik.common.constants.Constants.TAG_URL;

@Slf4j
@Controller
@RequestMapping(TAG_URL)
public class TagController {
    @Autowired
    private TagService tagService;
    @Autowired
    private CurrentUserResolver currentUserResolver;

    @GetMapping
    public String getTags(Model model) {
        model.addAttribute("tags", tagService.getTags());
        model.addAttribute("title", "Existing tags");

        return "tag-page";
    }

    @PostMapping
    public String createTag(@RequestParam("name") String name, Model model) {
        String author= currentUserResolver.getCurrentUser().getName();

        if(tagService.createTag(name, author)) {
            model.addAttribute("error", "Tag with name %s has been created".formatted(name));
        } else {
            model.addAttribute("error", "Tag with name %s already exists".formatted(name));
        }

        model.addAttribute("tags", tagService.getTags());
        model.addAttribute("title", "Existing tags");
        return "tag-page";
    }

}
