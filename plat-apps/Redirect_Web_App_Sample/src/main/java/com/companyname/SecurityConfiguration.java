/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.companyname;

import com.companyname.plat.security.filters.Oauth2ReAuthenticationFilter;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableAutoConfiguration
@Import({ServicesConfiguration.class})
@Order(2)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger logger
            = Logger.getLogger(SecurityConfiguration.class.getName());
    
    @Autowired
    DefaultTokenServices defaultTokenServices;
    
    @Autowired
    TokenStore tokenStore;

    public SecurityConfiguration() {
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

    //------- oauth2 authorization server
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration
            extends AuthorizationServerConfigurerAdapter {

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Autowired
        DataSource appDataSource;

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

    }

    // not in use now. But keep it for future 3 legs scenarios
    @Configuration
    static class Stuff {

        @Autowired
        private ClientDetailsService clientDetailsService;

        @Autowired
        private TokenStore tokenStore;

        @Bean
        public ApprovalStore approvalStore() throws Exception {
            logger.info("inside Stuff.approvalStore()");

            TokenApprovalStore store = new TokenApprovalStore();
            store.setTokenStore(tokenStore);
            return store;
        }

        @Bean
        public DefaultTokenServices defaultTokenServices() {
            DefaultTokenServices defaultTokenService = new DefaultTokenServices();
            defaultTokenService.setClientDetailsService(clientDetailsService);
            defaultTokenService.setSupportRefreshToken(true);
            defaultTokenService.setTokenStore(tokenStore);
            return defaultTokenService;
        }
    }
}
