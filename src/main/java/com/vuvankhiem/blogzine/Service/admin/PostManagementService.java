package com.vuvankhiem.blogzine.Service.admin;

import com.vuvankhiem.blogzine.DTO.PostDTO;
import com.vuvankhiem.blogzine.Entity.Account;
import com.vuvankhiem.blogzine.Entity.Category;
import com.vuvankhiem.blogzine.Entity.Post;
import com.vuvankhiem.blogzine.Entity.Tag;

import java.util.List;

public interface PostManagementService {

    void deletePost(int id);

    String getStringTags(List<Tag> tags);

    List<Category> getAllCategories();

    List<Tag> getTagsByStringTags(String strTags);

    List<Tag> getAllTags();

    Account getAccountByAccountID(int accountID);

    void saveAndUpdatePost(PostDTO postDTO, Post post);

    Post getPostByID(int postID);


}
