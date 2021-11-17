package com.vuvankhiem.blogzine.Controller.admin;

import com.vuvankhiem.blogzine.Service.admin.CommentManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class CommentManagementController {

    @Autowired
    CommentManagementService commentManagementService;

    @GetMapping("/xem-binh-luan/{id}")
    public String userComments(@PathVariable int id, Model model) {
        model.addAttribute("active", 2);
        model.addAttribute("posts", commentManagementService.getPostsByAccountAndComment(id));
        model.addAttribute("account", commentManagementService.getAccountByAccountID(id));
        return "ad/comment-management";
    }

    @ResponseBody
    @DeleteMapping("/xoa-binh-luan/{id}")
    public boolean deleteComment(@PathVariable int id) {
        commentManagementService.deleteCommentByCommentID(id);
        return true;
    }

    @ResponseBody
    @DeleteMapping("/xoa-tat-ca-binh-luan")
    public boolean deleteAllComments() {
        commentManagementService.deleteAllComments();
        return true;
    }

}
