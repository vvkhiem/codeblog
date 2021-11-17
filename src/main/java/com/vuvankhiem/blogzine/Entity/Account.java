package com.vuvankhiem.blogzine.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountID;

    private String username;

    private String password;

    private String email;

    private String avatar;

    private String role = "USER";

    private boolean active = true;

    private String auth_provider = "WEB";

    private String fullName;

    @OneToMany(mappedBy = "account", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments;

}