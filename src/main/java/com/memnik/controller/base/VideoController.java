package com.memnik.controller.base;

import com.memnik.dao.base.BaseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.memnik.common.constants.Constants.SEND_URL;

public abstract class VideoController<T extends BaseEntity> extends PictureController<T> {
    @Override
    @PostMapping(SEND_URL)
    public String send(@RequestParam String email, @RequestParam String picture, Model model) {
        String userName = currentUserResolver.getCurrentUser().getName();
        String videoAddress = picture.substring(1);
        if(service.sendVideoToEmail(email, videoAddress, userName)) {
            model.addAttribute("error", "%s was sent to %s".formatted(getEntityName(), email));
        } else {
            model.addAttribute("error", "Can't send %s to %s".formatted(getEntityName(), email));
        }
        model.addAttribute("title", "Sending %s".formatted(getEntityName()));

        return "main";
    }

}
