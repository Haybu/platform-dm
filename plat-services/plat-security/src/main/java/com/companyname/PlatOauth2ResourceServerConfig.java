/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.companyname;

import java.util.logging.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * OAuth2 Resources Server Configuration
 *
 * @author hmohamed
 */
@Configuration
@EnableResourceServer
public class PlatOauth2ResourceServerConfig
        extends ResourceServerConfigurerAdapter {
    
    private static final Logger logger
            = Logger.getLogger(PlatOauth2ResourceServerConfig.class.getName());
    
    private static final String RESOURCE_ID = "dmAPI";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        logger.info("inside ResourceServerConfiguration.configure(...)");
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        logger.info("inside ResourceServerConfiguration.configure(...)");

        http
                .requestMatchers().antMatchers("/api/greeting/**", "/oauth/users/**", "/oauth/clients/**")
                .and()
                .authorizeRequests()
                .antMatchers("/api/greeting").access("#oauth2.hasScope('read')")
                .antMatchers("/api/greeting").access("#oauth2.hasScope('trust')")
                .antMatchers("/api/greeting/**").access("#oauth2.hasScope('read')")
                .antMatchers("/api/greeting/**").access("#oauth2.hasScope('trust')")
                .regexMatchers(HttpMethod.DELETE, "/oauth/users/([^/].*?)/tokens/.*")
                .access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('write')")
                .regexMatchers(HttpMethod.GET, "/oauth/clients/([^/].*?)/users/.*")
                .access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('read')")
                .regexMatchers(HttpMethod.GET, "/oauth/clients/.*")
                .access("#oauth2.clientHasRole('ROLE_CLIENT') and #oauth2.isClient() and #oauth2.hasScope('read')")
                .regexMatchers(HttpMethod.GET, "/oauth/authentications/.*")
                .access("#oauth2.clientHasRole('ROLE_CLIENT') and #oauth2.isClient() and #oauth2.hasScope('read')");
    }

}
