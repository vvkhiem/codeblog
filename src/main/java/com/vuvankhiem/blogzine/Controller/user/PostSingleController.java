package com.vuvankhiem.blogzine.Controller.user;

import com.vuvankhiem.blogzine.Common.Common;
import com.vuvankhiem.blogzine.DTO.CommentDTO;
import com.vuvankhiem.blogzine.DTO.PostDTO;
import com.vuvankhiem.blogzine.Entity.Post;
import com.vuvankhiem.blogzine.Service.user.PostSingleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalTime;
import java.util.HashMap;

@Controller
public class PostSingleController extends Common {

    int st_minute;
    int st_id;

    @Autowired
    PostSingleService postSingleService;

    @GetMapping("/bai-viet/{id}")
    public String postSingle(@PathVariable int id,
                             Model model,
                             HttpSession session) {
        HashMap<Integer, PostDTO> recentPosts = (HashMap<Integer, PostDTO>) session.getAttribute("recentPosts");
        Post post = postSingleService.getPostById(id);

        int view = post.getPostViews();
        //check condition to add recent posts
        if (recentPosts == null)
            recentPosts = new HashMap<>();
        if (!recentPosts.containsKey(id))
            recentPosts = postSingleService.addRecentPost(recentPosts, id, post);
        //check condition to update views
        if (st_id != id) {
            st_id = id;
            st_minute = LocalTime.now().getMinute();
            view = postSingleService.updateView(id);
        } else {
            int currentMinute = LocalTime.now().getMinute();
            if (currentMinute - st_minute >= 3) {
                st_minute = currentMinute;
                view = postSingleService.updateView(id);
            }
        }
        model.addAttribute("post", post);
        model.addAttribute("view", view);
        model.addAttribute("relatedPosts", postSingleService.getAllRelatedPosts(id));
        session.setAttribute("recentPosts", recentPosts);
        return "us/post-single";
    }


    //add comment use web socket
    @ResponseBody
    @MessageMapping("/binh-luan")
    @SendTo("/topic/binh-luan")
    public CommentDTO addComment(@Payload CommentDTO commentDTO) {
        String currentDate = super.getCurrentDate(1);
        commentDTO = postSingleService.addComment(commentDTO, currentDate);
        commentDTO.setNumberOfComments(postSingleService.countCommentByPostID(commentDTO.getPostID()));
        return commentDTO;
    }

}
