package com.vuvankhiem.blogzine.Controller.user;

import com.vuvankhiem.blogzine.Service.user.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @Autowired
    AboutService aboutService;

    @GetMapping("/gioi-thieu")
    public String aboutPage(Model model) {
        model.addAttribute("countAllPosts", aboutService.countAllPosts());
        return "us/about";
    }

}
