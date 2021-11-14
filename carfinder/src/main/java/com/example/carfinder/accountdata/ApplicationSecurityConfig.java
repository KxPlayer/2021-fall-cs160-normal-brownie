package com.example.carfinder.accountdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//disable csrf, all requests require auth, users can use http
@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and().httpBasic();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authentication)
            throws Exception
    {
        authentication.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("pass"))
                .authorities("ROLE_USER")
                .and()
                .withUser("tom")
                .password(passwordEncoder().encode("password"))
                .authorities("ROLE_USER")
                .and()
                .withUser("tim")
                .password(passwordEncoder().encode("password"))
                .authorities("ROLE_USER")
                .and()
                .withUser("tam")
                .password(passwordEncoder().encode("password"))
                .authorities("ROLE_USER")
                .and()
                .withUser("tum")
                .password(passwordEncoder().encode("password"))
                .authorities("ROLE_USER")
                .and()
                .withUser("tommy") //isnt actually in db. just testing.
                .password(passwordEncoder().encode("newpassword"))
                .authorities("ROLE_USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}