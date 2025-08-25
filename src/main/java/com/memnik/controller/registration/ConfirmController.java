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

import static com.memnik.common.constants.Constants.CONFIRM_URL;
import static com.memnik.common.constants.LanguageDictionary.*;

@Slf4j
@Controller
@RequestMapping(CONFIRM_URL)
public class ConfirmController {
    @Autowired
    private LanguageResolver languageResolver;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/{code}")
    public String confirmEmail(@PathVariable String code, Model model) {
        log.info("Confirming email for code %s".formatted(code));
        String title = "Confirming email";
        boolean wasConfirmed = userDetailsService.confirmUserEmail(code);
        List<String> messages = getConfirmationMessages(wasConfirmed);

        model.addAttribute("title", title);
        model.addAttribute("messages", messages);
        return "main";
    }

    private List<String> getConfirmationMessages(boolean wasConfirmed) {
        if(wasConfirmed) {
            return List.of(
                    languageResolver.resolve(CONFIRM_EMAIL_1_OK),
                    languageResolver.resolve(CONFIRM_EMAIL_2_OK)
            );
        } else {
            return List.of(
                    languageResolver.resolve(CONFIRM_EMAIL_1_FAIL),
                    languageResolver.resolve(CONFIRM_EMAIL_2_FAIL)
            );
        }
    }
}
