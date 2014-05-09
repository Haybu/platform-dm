package com.companyname;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by hmohamed on 4/23/14.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //@Autowired
    //private DataSource dataSource;

        // DB authentication
        @Autowired
        public void authenticationConfig (AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .inMemoryAuthentication()
                    //.jdbcAuthentication() 
                    //.dataSource(dataSource)
                    //.withDefaultSchema()
                    .withUser("user").password("password").roles("USER")
                    .and()
                    .withUser("admin").password("password").roles("USER", "ADMIN");
        }


        // in memory authentication
        /**
        public void authenticationConfig (AuthenticationManagerBuilder auth) throws Exception {
            auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password("password").roles("USER", "ADMIN");
        }
         */

        protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().deleteCookies().permitAll();

        }

}
