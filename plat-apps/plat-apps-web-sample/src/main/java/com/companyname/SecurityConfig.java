package com.companyname;

import com.companyname.plat.security.providers.DAOAuthenticationProvider;
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
           
            @Autowired
            DAOAuthenticationProvider dAOAuthenticationProvider;                        
            
            @Override
            public void configure (AuthenticationManagerBuilder auth) 
                    throws Exception 
            {
                auth.authenticationProvider(dAOAuthenticationProvider);
            }


             @Override
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
        
                            
        /*
        --------------------------- backups -----------------------------
        */

                /**
        
        // DB authentication
        public void configure (AuthenticationManagerBuilder auth) throws Exception {
            auth                                        
                    .jdbcAuthentication() 
                    .dataSource(dataSource)
                    .withDefaultSchema()
                    .withUser("user").password("password").roles("USER")
                    .and()
                    .withUser("admin").password("password").roles("USER", "ADMIN");
        }
        *  */


        // in memory authentication
    /**
        @Override
        public void configure (AuthenticationManagerBuilder auth) throws Exception {
            auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password("password").roles("USER", "ADMIN");
        }
        * */               

}
