package com.vuvankhiem.blogzine.Service.admin;

import com.vuvankhiem.blogzine.DTO.CategoryDTO;
import com.vuvankhiem.blogzine.DTO.PostDTO;
import com.vuvankhiem.blogzine.Entity.Category;
import com.vuvankhiem.blogzine.Entity.Post;
import com.vuvankhiem.blogzine.Entity.Tag;

import java.util.List;

public interface DashBoardService {

    List<CategoryDTO> getAllCategories();

    List<PostDTO> getPostByPage(List<Post> posts, int index, int quantity);

    List<Post> getAllPosts();

    List<Post> getPostsByCategoryName(String categoryName);

    List<Post> getPostsOrderByPostIDDesc();

    List<Post> getPostsMostView();

    List<Post> getPostsByTxtSearch(String txtSearch);

    List<Post> getFeaturedPosts();

    List<Post> getPostsByDate(String date);

    List<Tag> getAllTags();

    List<Post> getPostByTag(String tagName);

    Category getCategoryByName(String categoryName);

    void addCategory(Category category);

}
