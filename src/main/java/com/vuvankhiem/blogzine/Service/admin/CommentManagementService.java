package com.vuvankhiem.blogzine.Service.admin;

import com.vuvankhiem.blogzine.Entity.Account;
import com.vuvankhiem.blogzine.Entity.Post;

import java.util.List;

public interface CommentManagementService {

    List<Post> getPostsByAccountAndComment(int id);

    Account getAccountByAccountID(int id);

    void deleteCommentByCommentID(int id);

    void deleteAllComments();

}
