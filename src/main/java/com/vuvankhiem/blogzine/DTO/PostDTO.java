package com.vuvankhiem.blogzine.DTO;

import com.vuvankhiem.blogzine.Entity.Comment;
import com.vuvankhiem.blogzine.Entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private int postID;
    private String postDate;
    private int postViews;
    private String postTitle;
    private String postContent;
    private String postImage;
    private String category_categoryName;
    private boolean postSlide;
    private String postDescription;
    private String account_avatar;
    private String account_fullname;
    private String strTags;
    private List<Tag> tags = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
}
