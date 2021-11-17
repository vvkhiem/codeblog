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
@Table(name = "tag")
@Entity
public class Tag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagID;

    private String tagName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "post_tag",
            joinColumns = {@JoinColumn(name = "tag_tagID")},
            inverseJoinColumns = {@JoinColumn(name = "post_postID")})
    private List<Post> posts = new ArrayList<>();

}