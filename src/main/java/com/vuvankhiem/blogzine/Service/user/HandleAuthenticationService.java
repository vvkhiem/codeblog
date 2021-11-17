package com.vuvankhiem.blogzine.Service.user;

import com.vuvankhiem.blogzine.Entity.Account;

public interface HandleAuthenticationService {

    boolean existsAccount(String user);

    void saveAccount(Account account);

    Account getAccountByUsernameOrEmail(String us, String auth_provider);

}
