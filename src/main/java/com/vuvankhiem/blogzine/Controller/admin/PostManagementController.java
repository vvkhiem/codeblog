package com.vuvankhiem.blogzine.Controller.admin;

import com.vuvankhiem.blogzine.Common.Common;
import com.vuvankhiem.blogzine.DTO.PostDTO;
import com.vuvankhiem.blogzine.Entity.Account;
import com.vuvankhiem.blogzine.Entity.Post;
import com.vuvankhiem.blogzine.Service.admin.PostManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/admin/bai-viet")
public class PostManagementController extends Common{

    @Value("${spring.storage.image.imageTitle}")
    private String imageTitle_Path;

    @Autowired
    PostManagementService postManagementService;

    @GetMapping("/")
    public String showFormPost (@ModelAttribute("post") PostDTO postDTO,
                                @RequestParam(name = "pid", required = false, defaultValue = "0") int pid,
                                Model model) {

        String strTags = "";
        if (pid > 0) {
            Post p = postManagementService.getPostByID(pid);
            model.addAttribute("p",p);
            strTags = postManagementService.getStringTags(p.getTags());
        } else {
            strTags = postManagementService.getStringTags(postManagementService.getAllTags());
        }
        model.addAttribute("pid",pid);
        model.addAttribute("active", 1);
        model.addAttribute("stringTag",strTags);
        model.addAttribute("categories",postManagementService.getAllCategories());
        return "ad/addUpdatePost";
    }

    @PostMapping("/")
    public String addNewPost(@ModelAttribute("post") PostDTO postDTO,
                             @RequestParam MultipartFile image,
                             HttpSession session) throws IOException {
        int accountID = (int) session.getAttribute("accountID");
        Account account = postManagementService.getAccountByAccountID(accountID);
        Post p = new Post();
        p.setAccount(account);
        p.setPostDate(super.getCurrentDate(2));
        p.setPostImage("/public/image/" + super.saveFile(image,imageTitle_Path));
        postManagementService.saveAndUpdatePost(postDTO, p);
        return "redirect:/admin/";
    }

    @PutMapping("/{id}")
    public String updatePost(@ModelAttribute("post") PostDTO postDTO,
                             @PathVariable int id,
                             @RequestParam MultipartFile image) throws IOException {
        long x = image.getSize();
        Post p = postManagementService.getPostByID(id);
        if(x != 0) {
            p.setPostImage("/public/image/" + super.saveFile(image,imageTitle_Path));
        }
        postManagementService.saveAndUpdatePost(postDTO, p);
        return "redirect:/admin/";
    }



    @ResponseBody
    @DeleteMapping("/{id}")
    public boolean deletePost(@PathVariable int id) {
        postManagementService.deletePost(id);
        return true;
    }

}
