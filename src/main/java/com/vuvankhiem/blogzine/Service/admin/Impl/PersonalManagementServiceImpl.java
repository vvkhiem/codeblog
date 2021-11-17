package com.vuvankhiem.blogzine.Service.admin.Impl;

import com.vuvankhiem.blogzine.Entity.Account;
import com.vuvankhiem.blogzine.Repository.AccountRepository;
import com.vuvankhiem.blogzine.Service.admin.PersonalManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalManagementServiceImpl implements PersonalManagementService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account getAccountByAccountID(int id) {
        return accountRepository.findByAccountID(id);
    }

    @Override
    public void updateAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return accountRepository.existsByUsername(username);
    }
}
