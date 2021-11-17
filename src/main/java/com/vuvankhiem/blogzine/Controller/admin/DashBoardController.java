package com.vuvankhiem.blogzine.Controller.admin;

import com.vuvankhiem.blogzine.DTO.PostDTO;
import com.vuvankhiem.blogzine.Entity.Category;
import com.vuvankhiem.blogzine.Entity.Post;
import com.vuvankhiem.blogzine.Service.admin.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/admin")
public class DashBoardController {

    static List<Post> st_posts;

    @Autowired
    DashBoardService dashBoardService;

    @GetMapping("/")
    public String dashBoard(Model model,
                            @PathVariable(required = false) String option) {
        st_posts = dashBoardService.getAllPosts();
        return commonIngredients(st_posts, model);
    }

    @PostMapping("/")
    public String addCategory(@RequestParam String c, Model model) {
        Category category = dashBoardService.getCategoryByName(c);
        if( category != null )
            return commonIngredients(st_posts, model);
        category = new Category();
        category.setCategoryName(c);
        dashBoardService.addCategory(category);
        return commonIngredients(st_posts, model);
    }

    @GetMapping("/tag/{t}")
    public String tag(Model model,
                      @PathVariable String t) {
        st_posts = dashBoardService.getPostByTag(t);
        return commonIngredients(st_posts, model);
    }

    @GetMapping("/danh-muc")
    public String category(Model model,
                           @RequestParam String c) {
        st_posts = dashBoardService.getPostsByCategoryName(c);
        return commonIngredients(st_posts, model);
    }

    @GetMapping("/sap-xep/{option}")
    public String sort(Model model,
                       @PathVariable String option) {
        if (option.equals("cu-moi"))
            st_posts = dashBoardService.getAllPosts();
        if (option.equals("moi-cu"))
            st_posts = dashBoardService.getPostsOrderByPostIDDesc();
        if (option.equals("lout-xem-nhieu-nhat"))
            st_posts = dashBoardService.getPostsMostView();
        return commonIngredients(st_posts, model);
    }

    @GetMapping("/tim-kiem")
    public String search(Model model,
                         @RequestParam String s) {
        st_posts = dashBoardService.getPostsByTxtSearch(s);
        return commonIngredients(st_posts, model);
    }

    @GetMapping("/loc-bai-viet/{option}")
    public String filter(Model model,
                         @PathVariable String option,
                         @RequestParam(name = "d", required = false) String date) {
        if (option.equals("noi-bat"))
            st_posts = dashBoardService.getFeaturedPosts();
        if (option.equals("ngay-dang")) {
            String str[] = date.split("-");
            Date d = new Date(str[1] + "/" + str[2] + "/" + str[0]);
            DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            st_posts = dashBoardService.getPostsByDate(dateFormat.format(d));
            if (st_posts == null) {
                return "ad/dashboard";
            }
        }
        return commonIngredients(st_posts, model);
    }


    @ResponseBody
    @GetMapping("/load-more/{index}")
    public List<PostDTO> loadMoreItems(@PathVariable int index) {
        return dashBoardService.getPostByPage(st_posts, index, 5);
    }

    //Ingredients : thanh phan
    public String commonIngredients(List<Post> posts, Model model) {
        model.addAttribute("tags",dashBoardService.getAllTags());
        model.addAttribute("active", 1);
        model.addAttribute("categories", dashBoardService.getAllCategories());
        model.addAttribute("posts", dashBoardService.getPostByPage(st_posts, 5, 5));
        return "ad/dashboard";
    }
}
