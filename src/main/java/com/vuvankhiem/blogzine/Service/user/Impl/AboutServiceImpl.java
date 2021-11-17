package com.vuvankhiem.blogzine.Service.user.Impl;

import com.vuvankhiem.blogzine.Repository.PostRepository;
import com.vuvankhiem.blogzine.Service.user.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AboutServiceImpl implements AboutService {

    @Autowired
    PostRepository postRepository;

    @Override
    public long countAllPosts() {
        return postRepository.countAllBy();
    }
}
