package com.vuvankhiem.blogzine.Service.user.Impl;

import com.vuvankhiem.blogzine.Entity.Account;
import com.vuvankhiem.blogzine.Repository.AccountRepository;
import com.vuvankhiem.blogzine.Service.user.HandleAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HandleAuthenticationServiceImpl implements HandleAuthenticationService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public boolean existsAccount(String user) {
        Account account = accountRepository.findAccountByUsernameOrEmailAndAuth_provider(user, "WEB");
        if (account != null) {
            return true;
        }
        return false;
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account getAccountByUsernameOrEmail(String us, String auth_provider) {
        return accountRepository.findAccountByUsernameOrEmailAndAuth_provider(us, auth_provider);
    }
}
