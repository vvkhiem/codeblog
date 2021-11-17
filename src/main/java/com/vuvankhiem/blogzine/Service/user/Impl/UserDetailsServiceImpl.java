package com.vuvankhiem.blogzine.Service.user.Impl;


import com.vuvankhiem.blogzine.Common.CustomUser;
import com.vuvankhiem.blogzine.Entity.Account;
import com.vuvankhiem.blogzine.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByUsernameOrEmailAndAuth_provider(username, "WEB");
        if (account == null) {
            new UsernameNotFoundException("Not Found !");
            return null;
        }
        List<GrantedAuthority> listGrantedAuthorities = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + account.getRole());
        listGrantedAuthorities.add(authority);

        CustomUser customUser = new CustomUser(
                account.getUsername(),
                account.getPassword(),
                true,
                true,
                true,
                account.isActive(),
                listGrantedAuthorities
        );
        customUser.setAvatar(account.getAvatar());
        customUser.setFullName(account.getFullName());
        customUser.setAccountId(account.getAccountID());
        return customUser;

    }
}
