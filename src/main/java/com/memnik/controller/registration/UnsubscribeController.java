package com.memnik.controller.registration;

import com.memnik.common.LanguageResolver;
import com.memnik.service.user.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.memnik.common.constants.Constants.UNSUBSCRIBE_URL;
import static com.memnik.common.constants.LanguageDictionary.*;

@Slf4j
@Controller
@RequestMapping(UNSUBSCRIBE_URL)
public class UnsubscribeController {
    @Autowired
    private LanguageResolver languageResolver;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/{code}")
    public String unsubscribe(@PathVariable String code, Model model) {
        log.info("Unsubscribing from emails for code %s".formatted(code));
        String title = "Unsubscribing from emails";
        boolean unsubscribed = userDetailsService.unsubscribeUser(code);
        List<String> messages = getUnsubscribeMessages(unsubscribed);

        model.addAttribute("title", title);
        model.addAttribute("messages", messages);
        return "main";
    }

    private List<String> getUnsubscribeMessages(boolean unsubscribed) {
        if(unsubscribed) {
            return List.of(
                    languageResolver.resolve(UNSUBSCRIBE_1_OK),
                    languageResolver.resolve(UNSUBSCRIBE_2_OK)
            );
        } else {
            return List.of(
                    languageResolver.resolve(UNSUBSCRIBE_1_FAIL)
            );
        }
    }
}
