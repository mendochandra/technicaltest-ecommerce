package com.example.eureka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration {

    @Value("$eureka.username")
    private String username;

    @Value("$eureka.password")
    private String password;

    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser(username).password(password)
                .authorities("USER");
    }

    public void configure(HttpSecurity httpSecurity)throws Exception{
        httpSecurity.csrf().disable()
                .authorizeHttpRequests().anyRequest()
                .authenticated()
                .and().httpBasic();
    }
}
