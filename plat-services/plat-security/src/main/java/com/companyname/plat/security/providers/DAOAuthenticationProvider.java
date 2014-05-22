/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.security.providers;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author hmohamed
 * 
 * could be injected with three dependencies:
 *  1- userDetailsService
 *  2- saltSource
 *  3- passwordEncoder
 */
public class DAOAuthenticationProvider extends DaoAuthenticationProvider {
    
    private static final Logger logger = 
            Logger.getLogger(DAOAuthenticationProvider.class.getName()); 
    
    //UserDetailsService userDetailsService;
    
    public DAOAuthenticationProvider() {}
   
   @Override
   public Authentication authenticate(Authentication authentication) 
           throws AuthenticationException {
       
       // Determine username
        String username = (authentication.getPrincipal() == null) ? 
                "NONE_PROVIDED" : authentication.getName();
        
       logger.info("platform: Start authenticating user [" + username + "]");
       
       try {
            Authentication auth = super.authenticate(authentication);
            
            logger.info("platform: End authenticating user [" 
                   + username + "] successfully");
            
            return auth;
       } 
       catch (AuthenticationException ex) {
           logger.info("platform: End authenticating user [" 
                   + username + "] unsuccessfully");
           throw ex;
       }                
   }
   
   protected void doAfterPropertiesSet() throws Exception {
       //this.setUserDetailsService(userDetailsService);
       this.setPasswordEncoder(new BCryptPasswordEncoder());       
       super.doAfterPropertiesSet();        
    }
    
}
