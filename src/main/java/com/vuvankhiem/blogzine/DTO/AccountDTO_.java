package com.vuvankhiem.blogzine.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
public class AccountDTO_ {

    @NotEmpty(message = "Password không được bỏ trống")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Mật khẩu tối thiểu tám ký tự, ít nhất một chữ hoa, một chữ thường và một chữ số")
    private String password;

    @NotEmpty(message = "Email không được bỏ trống")
    @Email(message = "Email không hợp lệ")
    private String email;

}
