package com.vuvankhiem.blogzine.Service.admin.Impl;

import com.vuvankhiem.blogzine.DTO.PostDTO;
import com.vuvankhiem.blogzine.Entity.Account;
import com.vuvankhiem.blogzine.Entity.Category;
import com.vuvankhiem.blogzine.Entity.Post;
import com.vuvankhiem.blogzine.Entity.Tag;
import com.vuvankhiem.blogzine.Repository.*;
import com.vuvankhiem.blogzine.Service.admin.PostManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class PostManagementServiceImpl implements PostManagementService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void deletePost(int id) {
        postRepository.deleteByPostID(id);
    }

    public String getStringTags(List<Tag> tags) {
        String str = "";
        for (Tag tag : tags
             ) {
            if (str.equals(""))
                str = tag.getTagName();
            else
                str = str + "," + tag.getTagName();
        }
        return str;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Tag> getTagsByStringTags(String strTags) {
        String str[] = strTags.split(",");
        List<Tag> tags = new LinkedList<>();
        for (String tagName : str) {
            Tag tag = tagRepository.findByTagName(tagName);
            if(tag == null) {
                tag = new Tag();
                tag.setTagName(tagName);
                tagRepository.save(tag);
                tag = tagRepository.findByTagName(tagName);
            }
            tags.add(tag);
        }
        return tags;
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Account getAccountByAccountID(int accountID) {
        return accountRepository.findByAccountID(accountID);
    }

    @Override
    public void saveAndUpdatePost(PostDTO postDTO, Post post) {
        List<Tag> tags = getTagsByStringTags(postDTO.getStrTags());
        Category category = categoryRepository.findByCategoryName(postDTO.getCategory_categoryName());
        post.setPostSlide(postDTO.isPostSlide());
        post.setPostTitle(postDTO.getPostTitle());
        post.setPostContent(postDTO.getPostContent());
        post.setPostDescription(postDTO.getPostDescription());
        post.setCategory(category);
        post.setTags(tags);
        postRepository.save(post);
    }

    @Override
    public Post getPostByID(int postID) {
        return postRepository.findByPostID(postID);
    }

}
