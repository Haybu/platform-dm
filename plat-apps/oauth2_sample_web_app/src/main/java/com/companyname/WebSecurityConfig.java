/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.companyname;

import com.companyname.plat.security.providers.DAOAuthenticationProvider;
import com.companyname.service.OnLoginSuccessHandler;
import com.companyname.service.OnLogoutHandler;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfig
        extends WebSecurityConfigurerAdapter {
    
    private static final Logger logger
            = Logger.getLogger(WebSecurityConfig.class.getName());

    @Autowired
    DAOAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    OnLoginSuccessHandler onLoginHandler;

    @Autowired
    OnLogoutHandler onLogoutHandler;

    public WebSecurityConfig() {
        logger.info("Inner security Config is loaded");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        logger.info("Configuring web app with a custom DAO authentication provider");
        auth.authenticationProvider(daoAuthenticationProvider);
    }

    /**
     * @Bean public OnLoginSuccessHandler loginSuccessHandler() { return new
     * OnLoginSuccessHandler(); }
     *
     * @Bean public OnLogoutHandler logoutHandler() { return new
     * OnLogoutHandler(); } *
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
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

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean()
            throws Exception {
        return super.authenticationManagerBean();
    }
}
