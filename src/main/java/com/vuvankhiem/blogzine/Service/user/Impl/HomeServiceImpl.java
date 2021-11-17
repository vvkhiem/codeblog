package com.vuvankhiem.blogzine.Service.user.Impl;

import com.vuvankhiem.blogzine.DTO.CategoryDTO;
import com.vuvankhiem.blogzine.DTO.PostDTO;
import com.vuvankhiem.blogzine.Entity.Category;
import com.vuvankhiem.blogzine.Entity.Post;
import com.vuvankhiem.blogzine.Entity.Subscriber;
import com.vuvankhiem.blogzine.Entity.Tag;
import com.vuvankhiem.blogzine.Repository.CategoryRepository;
import com.vuvankhiem.blogzine.Repository.PostRepository;
import com.vuvankhiem.blogzine.Repository.SubscriberRepository;
import com.vuvankhiem.blogzine.Repository.TagRepository;
import com.vuvankhiem.blogzine.Service.user.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("homeServiceImpl")

public class HomeServiceImpl implements HomeService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    SubscriberRepository subscriberRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TagRepository tagRepository;

    @Override
    public List<Post> getAllRandomPost() {
        return postRepository.getAllRandomPost();
    }

    @Override
    public List<PostDTO> getPostByPage(List<Post> posts, int index, int quantity) {
        List<PostDTO> postDTOS = new ArrayList<>();
        for (int i = index - quantity; i < index; i++) {
            if (i <= posts.size() - 1) {
                PostDTO postDTO = new PostDTO();
                postDTO.setPostID(posts.get(i).getPostID());
                postDTO.setAccount_avatar(posts.get(i).getAccount().getAvatar());
                postDTO.setAccount_fullname(posts.get(i).getAccount().getFullName());
                postDTO.setPostDate(posts.get(i).getPostDate());
                postDTO.setPostTitle(posts.get(i).getPostTitle());
                postDTO.setCategory_categoryName(posts.get(i).getCategory().getCategoryName());
                postDTO.setPostDescription(posts.get(i).getPostDescription());
                postDTO.setPostImage(posts.get(i).getPostImage());
                postDTO.setPostViews(posts.get(i).getPostViews());
                postDTOS.add(postDTO);
            } else {
                return postDTOS;
            }
        }
        return postDTOS;
    }

    @Override
    public List<Post> getPostSildes() {
        return postRepository.getPostSildes();
    }

    @Override
    public List<PostDTO> getTop15PostMostViewed() {
        List<Post> posts = postRepository.findTop15ByOrderByPostViewsDesc();
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts
        ) {
            PostDTO postDTO = new PostDTO();
            postDTO.setPostID(post.getPostID());
            postDTO.setPostTitle(post.getPostTitle());
            postDTOs.add(postDTO);
        }
        return postDTOs;
    }

    @Override
    public List<PostDTO> getTop8PostLastest() {
        List<Post> posts = postRepository.findTop8ByOrderByPostIDDesc();
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts
        ) {
            PostDTO postDTO = new PostDTO();
            postDTO.setPostID(post.getPostID());
            postDTO.setPostImage(post.getPostImage());
            postDTO.setAccount_fullname(post.getAccount().getFullName());
            postDTO.setAccount_avatar(post.getAccount().getAvatar());
            postDTO.setPostDate(post.getPostDate());
            postDTO.setPostTitle(post.getPostTitle());
            postDTOs.add(postDTO);
        }
        return postDTOs;
    }

    @Override
    public void saveSubscriber(Subscriber subscriber) {
        subscriberRepository.save(subscriber);
    }

    @Override
    public boolean existsSubscriberBySubscriberEmail(String email) {
        return subscriberRepository.existsSubscriberBySubscriberEmail(email);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories
        ) {
            long count = postRepository.countByCategory_CategoryName(category.getCategoryName());
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setCategoryName(category.getCategoryName());
            categoryDTO.setNumberPosts(count);
            categoryDTOS.add(categoryDTO);
        }
        return categoryDTOS;
    }

    @Override
    public List<Tag> getAllTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<PostDTO> getTop4PostLastest() {
        List<PostDTO> postDTOS = new ArrayList<>();
        List<Post> posts = postRepository.findTop4ByOrderByPostIDDesc();
        for (Post post : posts
        ) {
            PostDTO postDTO = new PostDTO();
            postDTO.setPostID(post.getPostID());
            postDTO.setPostImage(post.getPostImage());
            postDTO.setAccount_fullname(post.getAccount().getFullName());
            postDTO.setPostTitle(post.getPostTitle());
            postDTO.setPostDate(post.getPostDate());
            postDTOS.add(postDTO);
        }
        return postDTOS;
    }


}
