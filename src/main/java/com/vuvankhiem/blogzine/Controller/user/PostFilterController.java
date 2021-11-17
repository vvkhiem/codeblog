package com.vuvankhiem.blogzine.Controller.user;

import com.vuvankhiem.blogzine.DTO.PostDTO;
import com.vuvankhiem.blogzine.Entity.Post;
import com.vuvankhiem.blogzine.Service.user.PostFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PostFilterController {

    static List<Post> st_posts;
    @Autowired
    @Qualifier("postFilterServiceImpl")
    PostFilterService postFilterService;

    //filter posts by category
    @GetMapping("/danh-muc")
    public String byCategory(@RequestParam(name = "c") String categoryName,
                             Model model) {
        st_posts = postFilterService.getPostsByCategory(categoryName);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("numberOfPosts", st_posts.size());
        model.addAttribute("posts", postFilterService.getPostByPage(st_posts, 6, 6));
        return "us/categories";
    }

    //api get posts by category
    @ResponseBody
    @GetMapping("api/category/{index}")
    public List<PostDTO> apiGetPostsByCategory(@PathVariable int index) {
        return postFilterService.getPostByPage(st_posts, index, 6);
    }

    //filter post by text search
    @GetMapping("/tim-kiem")
    public String bySearch(@RequestParam(value = "s") String txtSearch,
                           Model model) {
        st_posts = postFilterService.getPostsByTxtSearch(txtSearch);
        model.addAttribute("txtSearch", txtSearch);
        if (st_posts == null) {
            model.addAttribute("not_found", "Không tìm thấy : " + txtSearch);
        } else {
            model.addAttribute("numberOfPosts", st_posts.size());
            model.addAttribute("posts", postFilterService.getPostByPage(st_posts, 5, 5));
        }
        return "us/search-result";
    }

    //api get posts by txt search
    @ResponseBody
    @GetMapping("/api/search/{index}")
    public List<PostDTO> apiGetPostsByTxtSearch(@PathVariable int index) {
        return postFilterService.getPostByPage(st_posts, index, 5);
    }

    //filter posts by tags
    @GetMapping("/tag")
    public String byTags(@RequestParam(value = "t") String tagName,
                         Model model) {
        st_posts = postFilterService.getPostByTag(tagName);
        model.addAttribute("tag", tagName);
        model.addAttribute("posts", postFilterService.getPostByPage(st_posts, st_posts.size(), st_posts.size()));
        model.addAttribute("numberOfPosts", st_posts.size());
        return "us/tag";
    }

}
