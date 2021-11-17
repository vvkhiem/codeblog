package com.vuvankhiem.blogzine.Service.user.Impl;

import com.vuvankhiem.blogzine.DTO.PostDTO;
import com.vuvankhiem.blogzine.Entity.Post;
import com.vuvankhiem.blogzine.Repository.PostRepository;
import com.vuvankhiem.blogzine.Repository.TagRepository;
import com.vuvankhiem.blogzine.Service.user.PostFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("postFilterServiceImpl")
public class PostFilterServiceImpl extends HomeServiceImpl implements PostFilterService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    TagRepository tagRepository;

    @Override
    public List<Post> getPostsByCategory(String categoryName) {
        return postRepository.findAllByCategory_CategoryNameOrderByPostIDDesc(categoryName);
    }

    @Override
    public List<Post> getPostsByTag(String tagName) {
        return null;
    }

    @Override
    public List<PostDTO> getPostsLimit(List<Post> posts, int index) {
        return null;
    }

    @Override
    public List<PostDTO> getPostByPage(List<Post> posts, int index, int quantity) {
        return super.getPostByPage(posts, index, quantity);
    }

    @Override
    public long countPostsByCategoryName(String categoryName) {
        return postRepository.countByCategory_CategoryName(categoryName);
    }

    @Override
    public List<Post> getPostsByTxtSearch(String txtSearch) {
        return postRepository.findByTxtSearchOrderByPostIDDesc(txtSearch);
    }

    @Override
    public List<Post> getPostByTag(String tagName) {
        return tagRepository.findByTagName(tagName).getPosts();
    }


}
