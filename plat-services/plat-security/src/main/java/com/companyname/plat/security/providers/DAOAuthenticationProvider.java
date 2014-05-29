/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.security.providers;

import com.companyname.plat.security.extension.PlatAOuthPasswordGrant;
import com.companyname.plat.security.extension.PlatAuthentication;
import com.companyname.plat.security.extension.PlatformTokens;
import com.companyname.plat.security.services.PlatUserAuthenticationCache;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author hmohamed
 * 
 * could be injected with three dependencies:
 *  1- userDetailsService
 *  2- saltSource
 *  3- passwordEncoder
 */
@Service
public class DAOAuthenticationProvider extends DaoAuthenticationProvider {
    
    private static final Logger logger = 
            Logger.getLogger(DAOAuthenticationProvider.class.getName()); 
    
    @Autowired
    UserDetailsService userDetailsService;
    
    @Autowired
    PlatUserAuthenticationCache cache;
    
    public DAOAuthenticationProvider() {}
   
   @Override
   public Authentication authenticate(Authentication authentication) 
           throws AuthenticationException {
       
        // Determine username and password
        String username = (authentication.getPrincipal() == null) ? 
                "NONE_PROVIDED" : authentication.getName();
        
        String credentials = (authentication.getPrincipal() == null) ? 
                "NONE_PROVIDED" : (String)authentication.getCredentials();
        
       logger.info("platform: Start authenticating user [" + username + "]");
       
       try {    
            Authentication auth = null;
           
            // authenticate from cache first to enhance performance
            auth = cache.authenticateFromCache(authentication);
           
            // perform authentication against our user's database store
            if (auth.isAuthenticated()) {            
                logger.info("User [" + username 
                        + "] is successfully authenticated against the cache");
            } else {
                auth = super.authenticate(authentication);
                cache.add(auth);
                logger.info("User [" + username 
                        + "] is successfully authenticated against DB store");
            }
            
            // build platform authentication object
            Authentication platformAuthentication = 
                    PlatAuthentication.getPlatAuthentication(auth);
            
            ((PlatAuthentication)platformAuthentication).setUserCredentials(credentials);                        
                       
            return platformAuthentication;
            
       } catch (AuthenticationException ex1) {
           logger.log(Level.SEVERE, "Unsuccessfully authenticating user [" 
                   + username + "] ", ex1);          
       } 
       
       return null;
   }
   
   protected void doAfterPropertiesSet() throws Exception {
       this.setUserDetailsService(userDetailsService);
       this.setPasswordEncoder(new BCryptPasswordEncoder());       
       super.doAfterPropertiesSet();        
    }
   
   /**
    * Using OAuth2 "password" flow, this method obtains an OAuth2 token using a users
    * login and password.
    * 
    * @param userName
    * @param password
    * @return 
    */
   protected Authentication getOauth2Tokens(Object userName, Object password) {
       
       return null;
   }
    
}
