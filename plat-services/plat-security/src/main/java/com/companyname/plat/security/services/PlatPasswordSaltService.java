/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.security.services;

import com.companyname.plat.repository.persistence.dao.AccountRepository;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;


/**
 *
 * @author hmohamed
 * 
 * We will use BCrypt, no need to worry for salting our passwords.
 * We can get rid of this class
 */
public class PlatPasswordSaltService 
            extends PlatCommonService
            implements SaltSource 
{
    
    private static final Logger logger = 
            Logger.getLogger(PlatPasswordSaltService.class.getName()); 
    
     @Autowired
    AccountRepository accountRepository;  

    @Override
    public Object getSalt(UserDetails user) 
    {        
        String username = (user.getUsername() == null) ? 
                "NONE_PROVIDED" : user.getUsername();
        
       logger.info("platform: Start getting a password salt for user [" 
               + username + "]");
                
        try {
            return this.getUserAccount(accountRepository, username);
        } 
        catch (Exception ex) {
            logger.info("platform: Error retrieving a password salt for user [" 
                    + username);
            throw ex;
        }
    }
    
}
