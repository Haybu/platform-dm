/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname;

import java.util.logging.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.AntPathRequestMatcher;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  
{
    
    private static final Logger logger = 
                Logger.getLogger(SecurityConfiguration.class.getName());
    
      public SecurityConfiguration() {
            logger.info("Redirect User app security Config is loaded");
        }
                        
        @Override
        public void configure (AuthenticationManagerBuilder auth) 
                throws Exception 
        {
            auth
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
        }                    

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                .anyRequest().hasRole("USER")
                .and()
                .formLogin()                                
                .and()
                .logout()    
               ;
        } 
}
