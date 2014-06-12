/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.companyname;

import com.companyname.services.OnLoginSuccessHandler;
import com.companyname.services.OnLogoutHandler;

import java.util.logging.Logger;

import com.companyname.services.PlatRequestMatcher;
import com.companyname.services.PlatUserAuthenticationCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.AntPathRequestMatcher;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@Import({
        PlatWebSecurityConfig.class
        , PlatOauth2AuthServerConfig.class
        , PlatOauth2ResourceServerConfig.class
       })
@Order(1)
public class JdbcBasedSecurityConfig
        extends WebSecurityConfigurerAdapter {
    
    private static final Logger logger
            = Logger.getLogger(JdbcBasedSecurityConfig.class.getName());

    @Autowired
    OnLoginSuccessHandler onLoginHandler;

    @Autowired
    OnLogoutHandler onLogoutHandler;

    @Autowired
    PlatUserAuthenticationCache userAuthenticationCache;

    public JdbcBasedSecurityConfig() {

        logger.info("Security Module: Jdbc based Config is loaded");
    }

    public PlatRequestMatcher requestMatcher() {
        return new PlatRequestMatcher("/login", "localhost.drillmap.com,localhost");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("Security module: Jdbc based HTTP configuration stack loaded.");
        http
                //.addFilterBefore(cacheAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .requestMatcher(requestMatcher())
                .formLogin()
                .successHandler(onLoginHandler)
                .loginPage("/login").permitAll()
                .and()
                .logout()
                .addLogoutHandler(onLogoutHandler)
                .invalidateHttpSession(true)
                .deleteCookies().permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize")).disable()
                ;
    }    
}
