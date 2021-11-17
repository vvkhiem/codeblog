package com.vuvankhiem.blogzine.Service.admin;

import com.vuvankhiem.blogzine.Entity.Account;

public interface PersonalManagementService {

    Account getAccountByAccountID(int id);

    void updateAccount( Account account);

    boolean checkExistUsername(String username);

}
