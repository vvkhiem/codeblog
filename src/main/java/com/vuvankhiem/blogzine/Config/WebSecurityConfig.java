package com.vuvankhiem.blogzine.Config;

import com.vuvankhiem.blogzine.Service.user.Impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/admin/**").access("hasAnyRole('ROLE_ADMIN')")
                .anyRequest().permitAll();

        http.formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/dang-nhap")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/login-success")
                .failureUrl("/dang-nhap?err=true");

        http.logout()
                .logoutSuccessUrl("/trang-chu")
                .deleteCookies("JSESSIONID", "remember-me")
                .invalidateHttpSession(true);

        http.rememberMe()
                .tokenValiditySeconds(86400)
                .rememberMeParameter("remember-me");

    }
}
