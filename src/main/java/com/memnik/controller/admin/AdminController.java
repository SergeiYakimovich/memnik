package com.memnik.controller.admin;

import com.memnik.dto.UserDto;
import com.memnik.service.*;
import com.memnik.service.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.memnik.common.constants.Constants.ADMIN_URL;

@Controller
@RequestMapping(ADMIN_URL)
public class AdminController {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private TagService tagService;
    @Autowired
    private MemService memService;
    @Autowired
    private JokeService jokeService;
    @Autowired
    private PostcardService postcardService;
    @Autowired
    private QuoteService quoteService;
    @Autowired
    private VideoService videoService;

    @GetMapping
    public String getUsers(Model model) {
        addDeleteModelAttributes(model, "");

        return "admin-page";
    }

    @PostMapping("deleteUser")
    public String  deleteUser(@RequestParam Long userId, Model model) {
        if(userDetailsService.deleteUser(userId)) {
            addDeleteModelAttributes(model, "User with id %d has been deleted".formatted(userId));
        } else {
            addDeleteModelAttributes(model, "Can't delete user with id %d".formatted(userId));
        }
        return "admin-page";
    }
    @PostMapping("deleteTag")
    public String  deleteTag(@RequestParam Long tagId, Model model) {
        if(tagService.deleteTag(tagId)) {
            addDeleteModelAttributes(model, "Tag with id %d has been deleted".formatted(tagId));
        } else {
            addDeleteModelAttributes(model, "Can't delete tag with id %d".formatted(tagId));
        }
        return "admin-page";
    }

    @PostMapping("deleteMem")
    public String  deleteMem(@RequestParam Long memId, Model model) {
        if(memService.deleteMem(memId)) {
            addDeleteModelAttributes(model, "Mem with id %d has been deleted".formatted(memId));
        } else {
            addDeleteModelAttributes(model, "Can't delete mem with id %d".formatted(memId));
        }
        return "admin-page";
    }

    @PostMapping("deleteJoke")
    public String  deleteJoke(@RequestParam Long jokeId, Model model) {
        if(jokeService.deleteJoke(jokeId)) {
            addDeleteModelAttributes(model, "Joke with id %d has been deleted".formatted(jokeId));
        } else {
            addDeleteModelAttributes(model, "Can't delete joke with id %d".formatted(jokeId));
        }
        return "admin-page";
    }

    @PostMapping("deletePostcard")
    public String  deletePostcard(@RequestParam Long postcardId, Model model) {
        if(postcardService.deletePostcard(postcardId)) {
            addDeleteModelAttributes(model, "Postcard with id %d has been deleted".formatted(postcardId));
        } else {
            addDeleteModelAttributes(model, "Can't delete postcard with id %d".formatted(postcardId));
        }
        return "admin-page";
    }

    @PostMapping("deleteQuote")
    public String  deleteQuote(@RequestParam Long quoteId, Model model) {
        if(quoteService.deleteQuote(quoteId)) {
            addDeleteModelAttributes(model, "Quote with id %d has been deleted".formatted(quoteId));
        } else {
            addDeleteModelAttributes(model, "Can't delete quote with id %d".formatted(quoteId));
        }
        return "admin-page";
    }

    @PostMapping("deleteVideo")
    public String  deleteVideo(@RequestParam Long videoId, Model model) {
        if(videoService.deleteVideo(videoId)) {
            addDeleteModelAttributes(model, "Video with id %d has been deleted".formatted(videoId));
        } else {
            addDeleteModelAttributes(model, "Can't delete video with id %d".formatted(videoId));
        }
        return "admin-page";
    }

    private void addDeleteModelAttributes(Model model, String msg) {
        if(!msg.isEmpty()) {
            model.addAttribute("error", msg);
        }
        List<UserDto> users = userDetailsService.findAllUsers();
        model.addAttribute("users", users);
    }
}
