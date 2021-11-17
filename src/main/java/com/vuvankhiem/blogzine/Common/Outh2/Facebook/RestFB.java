package com.vuvankhiem.blogzine.Common.Outh2.Facebook;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.User;
import com.vuvankhiem.blogzine.Common.CustomUser;
import com.vuvankhiem.blogzine.Entity.Account;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RestFB {

    @Autowired
    private Environment env;

    public String getToken(final String code) throws IOException {

        String link = String.format(
                env.getProperty("facebook.link.get.token"),
                env.getProperty("facebook.app.id"),
                env.getProperty("facebook.app.secret"),
                env.getProperty("facebook.redirect.uri"),
                code);
        String response = Request.Get(link).execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response).get("access_token");
        return jsonNode.textValue();

    }

    public User getUserInfo(final String accessToken) {
        FacebookClient facebookClient = new DefaultFacebookClient(
                accessToken,
                env.getProperty("facebook.app.secret"),
                Version.LATEST
        );
        return facebookClient.fetchObject("me", User.class, Parameter.with("fields", "email,id,name,picture"));
    }

    public UserDetails userDetails(Account account) {
        List<GrantedAuthority> listGrantedAuthorities = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + account.getRole());
        listGrantedAuthorities.add(authority);
        CustomUser customUser = new CustomUser(
                account.getUsername(),
                "",
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
