package com.memnik.controller;

import com.memnik.common.CurrentUserResolver;
import com.memnik.common.LanguageResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.memnik.common.constants.Constants.ANONYMOUS_USER;
import static com.memnik.common.constants.Constants.HOME_URL;
import static com.memnik.common.constants.LanguageDictionary.*;

@Slf4j
@Controller
@RequestMapping(HOME_URL)
public class HomeController {
    @Autowired
    private LanguageResolver languageResolver;
    @Autowired
    private CurrentUserResolver currentUserResolver;
    @Value("${memnik.tgbot.name}")
    private String tgbotName;

    @GetMapping
    public String hello(Model model) {
        List<String> messages = getHelloMessages();
        model.addAttribute("title", languageResolver.resolve(HELLO_TITLE));
        model.addAttribute("messages", messages);
        return "main";
    }

    @PostMapping
    public String language(@RequestParam("language") String language, Model model) {
        if( language != null && !language.isEmpty()) {
            String name = currentUserResolver.getCurrentUser().getName();
            languageResolver.setCurrentLanguage(language);
            log.info("User %s changed language to %s".formatted(name, language));
        }

        return "redirect:/";
    }

    private List<String> getHelloMessages() {
        String name = currentUserResolver.getCurrentUser().getName();
        if(name.equals(ANONYMOUS_USER)) {
            return List.of(
                    languageResolver.resolve(HELLO_1_ANONIMUS),
                    languageResolver.resolve(HELLO_2_ANONIMUS),
                    languageResolver.resolve(HELLO_3),
                    languageResolver.resolve(HELLO_4),
                    languageResolver.resolve(HELLO_5).formatted(tgbotName)

            );
        } else {
            return List.of(
                    languageResolver.resolve(HELLO_1_USER).formatted(name),
                    languageResolver.resolve(HELLO_2_USER),
                    languageResolver.resolve(HELLO_3),
                    languageResolver.resolve(HELLO_4),
                    languageResolver.resolve(HELLO_5).formatted(tgbotName)
            );
        }
    }

}
