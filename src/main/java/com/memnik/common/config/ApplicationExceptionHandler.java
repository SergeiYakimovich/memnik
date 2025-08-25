package com.memnik.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
//@ResponseBody
@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(Exception.class)
//    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public String generalExceptionHandler(Exception exception, Model model) {
        log.error("Exception: %s".formatted(exception.getMessage()));
        model.addAttribute("title", "Error");
        model.addAttribute("error", "Sorry, something went wrong");
        return "main";
    }
}
