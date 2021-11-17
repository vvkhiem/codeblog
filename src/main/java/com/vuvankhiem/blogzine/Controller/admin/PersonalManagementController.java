package com.vuvankhiem.blogzine.Controller.admin;

import com.vuvankhiem.blogzine.Common.Common;
import com.vuvankhiem.blogzine.Entity.Account;
import com.vuvankhiem.blogzine.Service.admin.PersonalManagementService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/admin/qltt-ca-nhan")
public class PersonalManagementController extends Common {

    @Value("${spring.storage.image.avatar}")
    private String avatar_path;

    @Autowired
    PersonalManagementService personalManagementService;

    @Autowired
    PasswordEncoder passwordEncoder;

    static String err = "";

    @GetMapping("/")
    public String personalManagement(@ModelAttribute("account") Account account,
                                     Model model,
                                     HttpSession session) {

        int id = (int) session.getAttribute("accountID");
        Account acc = personalManagementService.getAccountByAccountID(id);
        model.addAttribute("err",err);
        model.addAttribute("acc", acc);
        model.addAttribute("active", 4);
        err = "";
        return "ad/personal-management";
    }


    @PutMapping("/avatar")
    @SneakyThrows
    public String updateAvatar(@RequestParam(required = false) MultipartFile avatar,
                               Model model,
                               HttpSession session) throws IOException {

        long x = avatar.getSize() ;
        if (x == 0) {
            err = "Bạn chưa chọn ảnh đại diện";
            return "redirect:/admin/qltt-ca-nhan/";
        }
        int accID = (int) session.getAttribute("accountID");
        Account acc = personalManagementService.getAccountByAccountID(accID);
        String path = "/avatar/" + super.saveFile(avatar, avatar_path);
        acc.setAvatar(path);
        personalManagementService.updateAccount(acc);
        session.setAttribute("avatarUser", path);
        return "redirect:/admin/qltt-ca-nhan/";
    }

    @PutMapping("/info")
    public String updateInfor(@ModelAttribute("account") Account account,
                              Model model,
                              HttpSession session) {

        int accID = (int) session.getAttribute("accountID");
        Account acc = personalManagementService.getAccountByAccountID(accID);
        acc.setFullName(account.getFullName());
        acc.setEmail(account.getEmail());
        if (personalManagementService.checkExistUsername(account.getUsername())) {
            err = "Username đã tồn tại trên hệ thống !";
        } else {
            acc.setUsername(account.getUsername());
        }
        personalManagementService.updateAccount(acc);
        session.setAttribute("fullNameUser",account.getFullName());
        return "redirect:/admin/qltt-ca-nhan/";
    }

    @PostMapping("/pass")
    public String changePassword(@RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 Model model,
                                 HttpSession session) {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
        int accID = (int) session.getAttribute("accountID");
        Account account = personalManagementService.getAccountByAccountID(accID);
        if (!newPassword.equals(confirmPassword)) {
            err = "Mật khẩu xác thực không khớp !";
            return "redirect:/admin/qltt-ca-nhan/";
        }
        if (!pattern.matcher(newPassword).find()) {
            err = "Mật khẩu tối thiểu tám ký tự, ít nhất một chữ hoa, một chữ thường và một chữ số";
            return "redirect:/admin/qltt-ca-nhan/";
        }
        account.setPassword(passwordEncoder.encode(newPassword));
        personalManagementService.updateAccount(account);
        return "redirect:/admin/qltt-ca-nhan/";
    }


}
