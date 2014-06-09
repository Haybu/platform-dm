/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.companyname;

import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

/**
 * OAuth2 Authorization Server Configuration
 *
 * @author hmohamed
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2AuthServerConfig
        extends AuthorizationServerConfigurerAdapter {

    private static final Logger logger
            = Logger.getLogger(Oauth2AuthServerConfig.class.getName());

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    DataSource appDataSource;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Bean
    public JdbcClientDetailsService clientDetailsService() {
        return new JdbcClientDetailsService(appDataSource);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        logger.info("inside AuthorizationServerConfiguration.configure(...)");
        clients.jdbc(appDataSource).clients(clientDetailsService());
    }

    @Bean
    public TokenStore tokenStore() {
        logger.info("inside AuthorizationServerConfiguration.tokenStore(...)");
        return new JdbcTokenStore(appDataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        logger.info("inside AuthorizationServerConfiguration.configure(...)");
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        logger.info("inside AuthorizationServerConfiguration.configure(...)");
        oauthServer.realm("myApp/client");
    }

    @Bean
    public ApprovalStore approvalStore() throws Exception {
        logger.info("inside Stuff.approvalStore()");

        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore());
        return store;
    }

    @Bean
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices defaultTokenService = new DefaultTokenServices();
        defaultTokenService.setClientDetailsService(clientDetailsService);
        defaultTokenService.setSupportRefreshToken(true);
        defaultTokenService.setTokenStore(tokenStore());
        return defaultTokenService;
    }

}
