/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.companyname;

import com.companyname.filters.Oauth2ReAuthenticationFilter;
import com.companyname.services.OnLogoutHandler;
import java.util.logging.Logger;
import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.AntPathRequestMatcher;

@Configuration
@Import({
    PlatWebSecurityConfig.class
        , PlatOauth2AuthServerConfig.class
        , PlatOauth2ResourceServerConfig.class
})
@Order(2)
public class WebSecurityConfig
        extends WebSecurityConfigurerAdapter {

    private static final Logger logger
            = Logger.getLogger(WebSecurityConfig.class.getName());
    
    @Value("${plat.security.accessTokenCookieName}") 
    String accessTokenName;
    
    @Value("${plat.security.refreshTokenCookieName}")
    String refreshTokenName;
    
    @Value("${oauth2.clientId}")
    String clientId;
    
    @Value("${plat.base.loginUrl}")
    String loginURL;

    @Autowired
    DefaultTokenServices defaultTokenServices;

    @Autowired
    TokenStore tokenStore;
    
    @Autowired
    OnLogoutHandler onLogoutHandler;

    public WebSecurityConfig() {
        logger.info("Redirect User app security Config is loaded");
    }

    @Bean
    public Filter oauth2Filter() {
        Oauth2ReAuthenticationFilter filter = new Oauth2ReAuthenticationFilter();
        filter.setAccessTokenCookieName(accessTokenName);
        filter.setRefreshTokenCookieName(refreshTokenName);
        filter.setClientId(clientId);
        filter.setTokenService(defaultTokenServices);
        filter.setTokenStore(tokenStore);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterAfter(oauth2Filter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .anyRequest().hasRole("SUPERVISOR")
            .and().exceptionHandling().accessDeniedPage("/403")
            .and()  
                .formLogin()
                .loginPage(loginURL)
                //.permitAll()
            .and()
                .logout()
                .addLogoutHandler(onLogoutHandler)
                //.invalidateHttpSession(true)
                //.deleteCookies("JSESSIONID")
                //.permitAll()
            .and()
                .csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("/logout")).disable();
    }

}
