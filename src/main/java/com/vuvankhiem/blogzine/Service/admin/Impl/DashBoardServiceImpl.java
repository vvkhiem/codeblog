package com.vuvankhiem.blogzine.Service.admin.Impl;

import com.vuvankhiem.blogzine.DTO.CategoryDTO;
import com.vuvankhiem.blogzine.DTO.PostDTO;
import com.vuvankhiem.blogzine.Entity.Category;
import com.vuvankhiem.blogzine.Entity.Post;
import com.vuvankhiem.blogzine.Entity.Tag;
import com.vuvankhiem.blogzine.Repository.CategoryRepository;
import com.vuvankhiem.blogzine.Repository.PostRepository;
import com.vuvankhiem.blogzine.Repository.TagRepository;
import com.vuvankhiem.blogzine.Service.admin.DashBoardService;
import com.vuvankhiem.blogzine.Service.user.Impl.HomeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashBoardServiceImpl extends HomeServiceImpl implements DashBoardService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    TagRepository tagRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        return super.getAllCategories();
    }

    @Override
    public List<PostDTO> getPostByPage(List<Post> posts, int index, int quantity) {
        return super.getPostByPage(posts, index, quantity);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getPostsByCategoryName(String categoryName) {
        return postRepository.findAllByCategory_CategoryNameOrderByPostIDDesc(categoryName);
    }

    @Override
    public List<Post> getPostsOrderByPostIDDesc() {
        return postRepository.findByOrderByPostIDDesc();
    }

    @Override
    public List<Post> getPostsMostView() {
        return postRepository.findByOrderByPostViewsDesc();
    }

    @Override
    public List<Post> getPostsByTxtSearch(String txtSearch) {
        return postRepository.findByTxtSearchOrderByPostIDDesc(txtSearch);
    }

    @Override
    public List<Post> getFeaturedPosts() {
        return postRepository.getPostSildes();
    }

    @Override
    public List<Post> getPostsByDate(String date) {
        return postRepository.findByPostDate(date);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public List<Post> getPostByTag(String tagName) {
        return tagRepository.findByTagName(tagName).getPosts();
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    @Override
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

}
