package com.memnik.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.memnik.common.constants.Constants.CONTACTS_URL;

@Controller
@RequestMapping(CONTACTS_URL)
public class ContactsController {
    @GetMapping
    public String showContacts(Model model) {
        model.addAttribute("title", "Contacts");
        List<String> messages = List.of(
                "Copyright © 2022 Sergei Yakimovich",
                "email: ysv0108@yandex.ru",
                "telegram: @ysv0108"
        );
        model.addAttribute("messages", messages);

        return "main";
    }
}
