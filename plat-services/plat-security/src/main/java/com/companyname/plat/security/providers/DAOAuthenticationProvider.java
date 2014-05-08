/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.security.providers;

import java.util.logging.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author hmohamed
 */
public class DAOAuthenticationProvider extends DaoAuthenticationProvider {
    
    private static final Logger logger = Logger.getLogger(DAOAuthenticationProvider.class.getName());
        
    /**
     * for auditing and logging purposes only
     * @param userDetails
     * @param authentication
     * @throws AuthenticationException 
     */
   @Override
   protected void additionalAuthenticationChecks(UserDetails userDetails
           , UsernamePasswordAuthenticationToken authentication) 
          
   {       
       logger.info("platform: Start authenticating user [" + userDetails.getUsername() + "]");
       
       try {
        super.additionalAuthenticationChecks(userDetails, authentication);
       } catch (AuthenticationException ex) {
           logger.info("platform: End authenticating user [" + userDetails.getUsername() + "] successfully");
           throw ex;
       }       
   }
    
}
