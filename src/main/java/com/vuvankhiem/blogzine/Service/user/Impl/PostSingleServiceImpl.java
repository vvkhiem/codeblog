package com.vuvankhiem.blogzine.Service.user.Impl;

import com.vuvankhiem.blogzine.DTO.CommentDTO;
import com.vuvankhiem.blogzine.DTO.PostDTO;
import com.vuvankhiem.blogzine.Entity.Account;
import com.vuvankhiem.blogzine.Entity.Comment;
import com.vuvankhiem.blogzine.Entity.Post;
import com.vuvankhiem.blogzine.Repository.AccountRepository;
import com.vuvankhiem.blogzine.Repository.CommentRepository;
import com.vuvankhiem.blogzine.Repository.PostRepository;
import com.vuvankhiem.blogzine.Service.user.PostSingleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PostSingleServiceImpl implements PostSingleService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Post getPostById(int id) {
        return postRepository.findByPostID(id);
    }

    @Override
    public List<PostDTO> getAllRelatedPosts(int postID) {
        String categoryName = postRepository.findByPostID(postID).getCategory().getCategoryName();
        List<Post> posts = postRepository.findAllByCategory_CategoryNameOrderByPostIDDesc(categoryName);
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts
        ) {
            if (post.getPostID() != postID) {
                PostDTO postDTO = new PostDTO();
                postDTO.setPostID(post.getPostID());
                postDTO.setCategory_categoryName(post.getCategory().getCategoryName());
                postDTO.setPostTitle(post.getPostTitle());
                postDTO.setPostImage(post.getPostImage());
                postDTO.setPostDescription(post.getPostDescription());
                postDTOs.add(postDTO);
            }
        }
        return postDTOs;
    }


    @Override
    public HashMap<Integer, PostDTO> addRecentPost(HashMap<Integer, PostDTO> recentPosts, int id, Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostID(post.getPostID());
        postDTO.setPostTitle(post.getPostTitle());
        postDTO.setPostDescription(post.getPostDescription());
        postDTO.setPostImage(post.getPostImage());
        recentPosts.put(id, postDTO);
        return recentPosts;
    }

    @Override
    public int updateView(int postID) {
        Post post = postRepository.findByPostID(postID);
        post.setPostViews(post.getPostViews() + 1);
        postRepository.save(post);
        return post.getPostViews();
    }

    @Override
    public CommentDTO addComment(CommentDTO commentDTO, String currentDate) {
        Account account = accountRepository.findByAccountID(commentDTO.getAccountID());
        Post post = postRepository.findByPostID(commentDTO.getPostID());

        Comment comment = new Comment();
        comment.setCommentDate(currentDate);
        comment.setAccount(account);
        comment.setPost(post);
        comment.setCommentContent(commentDTO.getCommentContent());
        comment.setReply(commentDTO.getReply());
        comment.setCommentLevel(commentDTO.getCommentLevel());

        Comment newComment = commentRepository.save(comment);
        commentDTO.setCommentID(newComment.getCommentID());
        commentDTO.setCommentDate(currentDate);
        commentDTO.setAvatarUser(account.getAvatar());
        commentDTO.setNameUser(account.getFullName());
        return commentDTO;
    }

    @Override
    public long countCommentByPostID(int postID) {
        return commentRepository.countByPost_PostID(postID);
    }


}
