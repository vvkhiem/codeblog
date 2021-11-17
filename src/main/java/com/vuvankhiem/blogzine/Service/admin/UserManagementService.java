package com.vuvankhiem.blogzine.Service.admin;

import com.vuvankhiem.blogzine.DTO.AccountDTO;
import com.vuvankhiem.blogzine.Entity.Account;

import java.util.List;

public interface UserManagementService {

    List<Account> getAllUsers();

    List<AccountDTO> getUserByPage(List<Account> accounts, int index);

    List<Account> getUsersByTxtSearch(String search);

    // Interactive : Tuong tac
    List<Account> getUserMostInteractive();

    List<Account> getAllUsersBlocked();

    void deleteUserById(int id);

    boolean updateStatusUser(String tt, int id);
}
