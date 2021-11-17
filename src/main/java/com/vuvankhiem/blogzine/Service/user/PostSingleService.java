package com.vuvankhiem.blogzine.Service.user;

import com.vuvankhiem.blogzine.DTO.CommentDTO;
import com.vuvankhiem.blogzine.DTO.PostDTO;
import com.vuvankhiem.blogzine.Entity.Post;

import java.util.HashMap;
import java.util.List;

public interface PostSingleService {

    Post getPostById(int id);

    List<PostDTO> getAllRelatedPosts(int postID);

    HashMap<Integer, PostDTO> addRecentPost(HashMap<Integer, PostDTO> recentPosts, int id, Post post);

    int updateView(int postID);

    CommentDTO addComment(CommentDTO commentDTO, String currentDate);

    long countCommentByPostID(int postID);

}
