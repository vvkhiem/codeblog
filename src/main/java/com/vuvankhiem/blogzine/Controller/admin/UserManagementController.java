package com.vuvankhiem.blogzine.Controller.admin;

import com.vuvankhiem.blogzine.DTO.AccountDTO;
import com.vuvankhiem.blogzine.Entity.Account;
import com.vuvankhiem.blogzine.Service.admin.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/quan-ly-user")
public class UserManagementController {

    static List<Account> st_accounts;

    @Autowired
    UserManagementService userManagementService;

    @GetMapping
    public String userManagement(Model model) {
        st_accounts = userManagementService.getAllUsers();
        model.addAttribute("active", 2);
        model.addAttribute("users", userManagementService.getUserByPage(st_accounts, 5));
        return "ad/user-management";
    }

    @GetMapping("/tim-kiem")
    public String searchUser(@RequestParam String s, Model model) {
        st_accounts = userManagementService.getUsersByTxtSearch(s);
        model.addAttribute("active", 2);
        model.addAttribute("users", userManagementService.getUserByPage(st_accounts, 5));
        return "ad/user-management";
    }

    @GetMapping("/loc/{option}")
    public String userFilter(@PathVariable String option, Model model) {
        if (option.equals("block"))
            st_accounts = userManagementService.getAllUsersBlocked();
        if (option.equals("tt-cao-nhat"))
            st_accounts = userManagementService.getUserMostInteractive();
        model.addAttribute("active", 2);
        model.addAttribute("users", userManagementService.getUserByPage(st_accounts, 5));
        return "ad/user-management";
    }

    @ResponseBody
    @GetMapping("/load-more-user/{index}")
    public List<AccountDTO> loadMoreUser(@PathVariable int index) {
        return userManagementService.getUserByPage(st_accounts, index);
    }

    @ResponseBody
    @DeleteMapping("/xoa-user/{id}")
    public boolean deleteUser(@PathVariable int id) {
        userManagementService.deleteUserById(id);
        return true;
    }

    @ResponseBody
    @GetMapping("/trang-thai")
    public boolean updateStatusUser(@RequestParam String tt, @RequestParam int id) {
        return userManagementService.updateStatusUser(tt, id);
    }
}
