package com.vuvankhiem.blogzine.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contact")
@Entity
public class Contact implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contactID;

    @NotEmpty(message = "Tiêu đề không được bỏ trống")
    @Size(min = 5, max = 100, message = "Tiêu đề nằm ngoài khoảng cho phép")
    private String contactTitle;

    @NotEmpty(message = "Nội dung tin nhắn không được bỏ trống")
    @Size(min = 5, max = 500, message = "Nội dung tin nhắn nằm ngoài khoảng cho phép")
    private String contactSubject;

    private boolean contactStatus = false;

    @Email(message = "Email không hợp lệ")
    private String contactEmail;

    @Pattern(regexp = "^0(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$", message = "Số điện thoại không hợp lệ")
    private String contactPhone;

    @NotEmpty(message = "Họ tên không được bỏ trống")
    @Pattern(regexp = "[^!@#$%^&*<>0-9]+", message = "Họ tên không hợp lệ")
    @Size(max = 50, message = "Họ tên tối đa 50 kí tự")
    private String contactName;

    private boolean contactReply = false;

    private String contactDate;

}