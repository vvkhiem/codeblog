package com.vuvankhiem.blogzine.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
@Entity
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postID;

    private String postDate;

    private int postViews;

    private String postTitle;

    private String postContent;

    private String postImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_categoryID")
    private Category category;

    private boolean postSlide;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_accountID")
    private Account account;

    private String postDescription;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "post_tag",
            joinColumns = {@JoinColumn(name = "post_postID")},
            inverseJoinColumns = {@JoinColumn(name = "tag_tagID")})
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;


}