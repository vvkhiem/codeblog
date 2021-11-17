package com.vuvankhiem.blogzine.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subscriber")
@Entity
public class Subscriber implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subscriberID;

    @NotEmpty(message = "Email đăng kí không được bỏ trống !")
    @Email(message = "Email đăng kí không hợp lệ !")
    private String subscriberEmail;


}