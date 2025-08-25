package com.memnik.controller.registration;

import com.memnik.common.LanguageResolver;
import com.memnik.dto.LoginDto;
import com.memnik.service.user.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

import static com.memnik.common.constants.Constants.REGISTRATION_URL;
import static com.memnik.common.constants.LanguageDictionary.*;

@Slf4j
@Controller
@RequestMapping(REGISTRATION_URL)
public class RegistrationController {
    @Autowired
    private LanguageResolver languageResolver;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @GetMapping
    public String registration(Model model) {
        model.addAttribute("user", LoginDto.builder().build());
        return REGISTRATION_URL;
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") @Valid LoginDto user, BindingResult bindingResult, Model model) {
        log.info("Creating user with name %s".formatted(user.getName()));
        if (bindingResult.hasErrors()) {
            String message = bindingResult
                    .getFieldErrors()
                    .stream()
                    .map(f -> f.getField() + ": " + f.getDefaultMessage())
                    .collect(Collectors.joining("; "));
            model.addAttribute("error", "Error: %s".formatted(message));
            return REGISTRATION_URL;
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())){
            model.addAttribute("error", "Passwords must be equal");
            return REGISTRATION_URL;
        }

        if (!userDetailsService.createUser(user)){
            model.addAttribute("error", "User with name %s already exists".formatted(user.getName()));
            return REGISTRATION_URL;
        }
        languageResolver.setCurrentLanguage(user.getLanguage());

        String title = "User registration";
        boolean emailWasSent = userDetailsService.sendConfirmMail(user.getName());
        List<String> messages = getRegistrationMessages(emailWasSent, user.getName(), user.getEmail());

        model.addAttribute("title", title);
        model.addAttribute("messages", messages);
        return "main";
    }

    private List<String> getRegistrationMessages(boolean emailWasSent, String name, String email) {
        if(emailWasSent) {
            return List.of(
                    languageResolver.resolve(REGISTRATION_1).formatted(name),
                    languageResolver.resolve(REGISTRATION_2),
                    languageResolver.resolve(REGISTRATION_3_WAS_SENT).formatted(email),
                    languageResolver.resolve(REGISTRATION_4_WAS_SENT).formatted(name)
            );
        } else {
            return List.of(
                    languageResolver.resolve(REGISTRATION_1).formatted(name),
                    languageResolver.resolve(REGISTRATION_2),
                    languageResolver.resolve(REGISTRATION_3_CANT_SENT).formatted(email),
                    languageResolver.resolve(REGISTRATION_4_CANT_SENT).formatted(name)
            );
        }
    }
}
