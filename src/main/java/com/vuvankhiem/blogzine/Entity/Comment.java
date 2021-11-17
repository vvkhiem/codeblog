package com.vuvankhiem.blogzine.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "comment")
@Entity
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentID;

    private String commentContent;

    private int reply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_accountID")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_postID")
    private Post post;

    private String commentDate;

    private int commentLevel;

}