package com.vuvankhiem.blogzine.Common.Outh2.Google;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vuvankhiem.blogzine.Common.CustomUser;
import com.vuvankhiem.blogzine.Entity.Account;
import org.apache.http.client.fluent.Form;
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
public class RestGG {
    @Autowired
    Environment env;

    public String getToken(String code) throws IOException {
        String response = Request.Post(env.getProperty("google.link.get.token"))
                .bodyForm(Form.form()
                        .add("client_id", env.getProperty("google.app.id"))
                        .add("client_secret", env.getProperty("google.app.secret"))
                        .add("redirect_uri", env.getProperty("google.redirect.uri"))
                        .add("code", code)
                        .add("grant_type", "authorization_code")
                        .build()
                )
                .execute()
                .returnContent()
                .asString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response).get("access_token");
        return jsonNode.textValue();
    }

    public GooglePojo getUserInfo(final String accessToken) throws IOException {
        String link = env.getProperty("google.link.get.user_info") + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        GooglePojo googlePojo = mapper.readValue(response, GooglePojo.class);
        return googlePojo;
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
