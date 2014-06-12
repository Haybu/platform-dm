/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.companyname;

import com.companyname.providers.DAOAuthenticationProvider;
import java.util.logging.Logger;

import com.companyname.services.PlatRequestMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(99)
public class PlatWebSecurityConfig
        extends WebSecurityConfigurerAdapter {
    
    
    private static final Logger logger
            = Logger.getLogger(PlatWebSecurityConfig.class.getName());

    @Autowired
    DAOAuthenticationProvider daoAuthenticationProvider;

    public PlatWebSecurityConfig() {
        logger.info("Common web security Config is loaded");
    }

    public PlatRequestMatcher requestMatcher() {
        return new PlatRequestMatcher("/not-exist", "not-exist");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        logger.info("Common web security setup with a custom DAO authentication provider");
        auth.authenticationProvider(daoAuthenticationProvider);
    }   

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("Platform: Oauth module security configuration loaded.");
        http
          .requestMatcher(requestMatcher())
          .authorizeRequests()
          .anyRequest().denyAll();
                
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean()
            throws Exception {
        return super.authenticationManagerBean();
    }
}
