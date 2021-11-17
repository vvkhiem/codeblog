package com.vuvankhiem.blogzine.Service.admin.Impl;

import com.vuvankhiem.blogzine.DTO.AccountDTO;
import com.vuvankhiem.blogzine.Entity.Account;
import com.vuvankhiem.blogzine.Entity.Comment;
import com.vuvankhiem.blogzine.Repository.AccountRepository;
import com.vuvankhiem.blogzine.Repository.CommentRepository;
import com.vuvankhiem.blogzine.Service.admin.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<Account> getAllUsers() {
        return accountRepository.findAll();
    }

    @Override
    public List<AccountDTO> getUserByPage(List<Account> accounts, int index) {
        List<AccountDTO> accountDTOS = new ArrayList<>();
        for (int i = index - 5; i < index; i++) {
            if (i <= accounts.size() - 1) {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setAccountID(accounts.get(i).getAccountID());
                accountDTO.setFullName(accounts.get(i).getFullName());
                accountDTO.setUsername(accounts.get(i).getUsername());
                accountDTO.setEmail(accounts.get(i).getEmail());
                accountDTO.setAvatar(accounts.get(i).getAvatar());
                accountDTO.setRole(accounts.get(i).getRole());
                accountDTO.setActive(accounts.get(i).isActive());
                accountDTO.setAuth_provider(accounts.get(i).getAuth_provider());
                accountDTOS.add(accountDTO);
            } else {
                return accountDTOS;
            }
        }
        return accountDTOS;
    }

    @Override
    public List<Account> getUsersByTxtSearch(String search) {
        return accountRepository.findByFullNameContainingOrEmailContainingOrUsernameContaining(search, search, search);
    }

    @Override
    public List<Account> getUserMostInteractive() {
        List<Account> accounts = new ArrayList<>();
        List<Comment> comments = commentRepository.findByGroupByAccount();
        for (Comment comment : comments
        ) {
            Account account = accountRepository.findByAccountID(comment.getAccount().getAccountID());
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public List<Account> getAllUsersBlocked() {
        return accountRepository.findByActive(false);
    }

    @Override
    public void deleteUserById(int id) {
        accountRepository.deleteById(id);
    }

    @Override
    public boolean updateStatusUser(String tt, int id) {
        Account account = accountRepository.findByAccountID(id);
        boolean status = true;
        if (!account.getRole().equals("ADMIN")) {
            if (tt.equals("Block"))
                status = false;
            account.setActive(status);
            accountRepository.save(account);
        }
        return status;
    }
}
