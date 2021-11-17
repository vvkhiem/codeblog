package com.vuvankhiem.blogzine.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CommentDTO {
    int commentID;
    int reply;
    int accountID;
    int postID;
    long numberOfComments;
    String commentContent;
    String commentDate;
    String avatarUser;
    String nameUser;
    int commentLevel;
    int index;
}
