package com.vuvankhiem.blogzine.Service.user;

import com.vuvankhiem.blogzine.DTO.PostDTO;
import com.vuvankhiem.blogzine.Entity.Post;

import java.util.List;

public interface PostFilterService {

    List<Post> getPostsByCategory(String categoryName);

    List<Post> getPostsByTag(String tagName);

    List<PostDTO> getPostsLimit(List<Post> posts, int index);

    List<PostDTO> getPostByPage(List<Post> posts, int index, int quantity);

    long countPostsByCategoryName(String categoryName);

    List<Post> getPostsByTxtSearch(String txtSearch);

    List<Post> getPostByTag(String tagName);
}
