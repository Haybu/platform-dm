/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.security.services;

import com.companyname.plat.repository.persistence.dao.AccountRepository;
import com.companyname.plat.repository.persistence.entity.UserAccount;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author hmohamed
 */
public class PlatCommonService {
    
    private static final Logger logger = 
            Logger.getLogger(PlatCommonService.class.getName());               
    
    public UserAccount getUserAccount(AccountRepository repo, String userName)        
    {
        
        if (repo == null) {            
            throw new RuntimeException("System Error: No account repository is found (null)");            
        } 
        
        if (userName == null) {           
            throw new RuntimeException("System Error: No userName is passed (null)");
        }
        
        logger.info("retrieving account information for user name: " + userName);
        
        List<UserAccount> accounts = repo.findByLoginName(userName);
       
       if (accounts == null) {
           throw new UsernameNotFoundException("No user account found");
       } 
       
       if (accounts.size() > 1 ) {
           throw new UsernameNotFoundException(
                   "More than one active account is found");
       } 
       
       logger.info("user account is rertrieved from backend tables for user name: " + userName);
       
       return accounts.get(0);  
    }
    
}
