/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.companyname;

import com.companyname.filters.Oauth2ReAuthenticationFilter;
import java.util.logging.Logger;
import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger
            = Logger.getLogger(WebSecurityConfig.class.getName());
    
    @Autowired
    DefaultTokenServices defaultTokenServices;
    
    @Autowired
    TokenStore tokenStore;

    public WebSecurityConfig() {
        logger.info("Redirect User app security Config is loaded");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }

    @Bean
    public Filter oauth2Filter() {
        Oauth2ReAuthenticationFilter filter = new Oauth2ReAuthenticationFilter();
        filter.setAccessTokenCookieName("plat-access-token");
        filter.setRefreshTokenCookieName("plat-refresh-token");
        filter.setClientId("my-trusted-client-with-secret");
        filter.setTokenService(defaultTokenServices);
        filter.setTokenStore(tokenStore);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                addFilterAfter(oauth2Filter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean()
            throws Exception {
        return super.authenticationManagerBean();
    }
    
}
