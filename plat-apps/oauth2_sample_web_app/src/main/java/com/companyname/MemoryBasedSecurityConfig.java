package com.companyname;

import com.companyname.domain.InMemoryUser;
import com.companyname.providers.InMemoryAuthenticationProvider;
import com.companyname.services.OnLoginSuccessHandler;
import com.companyname.services.OnLogoutHandler;
import com.companyname.services.PlatRequestMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.AntPathRequestMatcher;

import java.util.logging.Logger;

/**
 * Created by hmohamed on 6/11/14.
 */
@Configuration
@EnableWebSecurity
@Import({
        PlatWebSecurityConfig.class
        , PlatOauth2AuthServerConfig.class
        , PlatOauth2ResourceServerConfig.class
})
@Order(2)
public class MemoryBasedSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger
            = Logger.getLogger(MemoryBasedSecurityConfig.class.getName());

    @Autowired
    InMemoryAuthenticationProvider inMemoryAuthenticationProvider;


    @Autowired
    OnLoginSuccessHandler onLoginHandler;

    @Autowired
    OnLogoutHandler onLogoutHandler;

    public MemoryBasedSecurityConfig() {

        logger.info("Security Module: Memory based Config is loaded");
    }

    public PlatRequestMatcher requestMatcher() {
        return new PlatRequestMatcher("/login", "austin.drillmap.com");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        InMemoryUser user1 = new InMemoryUser("myuser", "mypassword", new String[]{"SUPERVISOR"});
        InMemoryUser user2 = new InMemoryUser("hip", "hop", new String[]{"USER"});
        inMemoryAuthenticationProvider.addUser(user1);
        inMemoryAuthenticationProvider.addUser(user2);
        auth.authenticationProvider(inMemoryAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("Security module: Memory based HTTP configuration stack loaded.");
        http
                .requestMatcher(requestMatcher())
                .formLogin()
                .successHandler(onLoginHandler)
                .loginPage("/login").permitAll()
                .and()
                .logout()
                .permitAll()
                .addLogoutHandler(onLogoutHandler)
                .invalidateHttpSession(true)
                .deleteCookies()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize")).disable()

                ;
    }
}
