package com.vuvankhiem.blogzine.Service.admin.Impl;

import com.vuvankhiem.blogzine.Entity.Account;
import com.vuvankhiem.blogzine.Entity.Comment;
import com.vuvankhiem.blogzine.Entity.Post;
import com.vuvankhiem.blogzine.Repository.AccountRepository;
import com.vuvankhiem.blogzine.Repository.CommentRepository;
import com.vuvankhiem.blogzine.Repository.PostRepository;
import com.vuvankhiem.blogzine.Service.admin.CommentManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentManagementServiceImpl implements CommentManagementService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    AccountRepository accountRepository;


    @Override
    public List<Post> getPostsByAccountAndComment(int id) {
        List<Comment> comments = commentRepository.findByAccount_AccountIDAndGroupByPostID(id);
        List<Post> posts = new ArrayList<>();
        for (Comment comment : comments
        ) {
            int c_ID = comment.getPost().getPostID();
            Post post = postRepository.findByPostID(c_ID);
            post.setComments(commentRepository.findByAccount_AccountIDAndPost_PostID(id, c_ID));
            posts.add(post);
        }
        return posts;
    }

    @Override
    public Account getAccountByAccountID(int id) {
        return accountRepository.findByAccountID(id);
    }

    @Override
    public void deleteCommentByCommentID(int id) {
        commentRepository.deleteByCommentID(id);
    }

    @Override
    public void deleteAllComments() {
        commentRepository.deleteAll();
    }
}
