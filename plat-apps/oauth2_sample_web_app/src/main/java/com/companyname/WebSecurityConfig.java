/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.companyname;

import com.companyname.services.OnLoginSuccessHandler;
import com.companyname.services.OnLogoutHandler;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Import({
        PlatWebSecurityConfig.class
        , PlatOauth2AuthServerConfig.class
        , PlatOauth2ResourceServerConfig.class
       })
@Order(2)
public class WebSecurityConfig
        extends WebSecurityConfigurerAdapter {
    
    private static final Logger logger
            = Logger.getLogger(PlatWebSecurityConfig.class.getName());   

    @Autowired
    OnLoginSuccessHandler onLoginHandler;

    @Autowired
    OnLogoutHandler onLogoutHandler;

    public WebSecurityConfig() {
        logger.info("Oauth2 security Config is loaded");
    }    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("Platform: Oauth module security configuration loaded.");
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().requireCsrfProtectionMatcher(
                        new AntPathRequestMatcher("/oauth/authorize")).disable()
                .formLogin()
                .successHandler(onLoginHandler)
                .loginPage("/login").permitAll()
                .and()
                .logout()
                .addLogoutHandler(onLogoutHandler)
                .invalidateHttpSession(true)
                .deleteCookies().permitAll();
    }    
}
