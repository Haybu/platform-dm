/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.security;

import com.companyname.plat.commons.context.SprintContextPrinter;
import com.companyname.plat.repository.PlatPersistenceComponentApplication;
import com.companyname.plat.repository.persistence.dao.AccountRepository;
import com.companyname.plat.repository.persistence.dao.GroupAuthorizationRepository;
import com.companyname.plat.repository.persistence.dao.GroupMembersRepository;
import com.companyname.plat.security.providers.DAOAuthenticationProvider;
import com.companyname.plat.security.services.PlatUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 *
 * @author hmohamed
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class PlatSecurityComponentApplication 
    extends PlatPersistenceComponentApplication   
{    
    
        private static final boolean PRINT_MY_BEANS = false;
            
        public static void main(String[] args) throws Exception {
            SpringApplication app = new SpringApplication(PlatSecurityComponentApplication.class);
            app.setShowBanner(false);               
            app.run(args);               
        }  
        
        @Autowired
        AccountRepository accountRepo;
        
        @Autowired
        GroupMembersRepository groupMemberRepo;
        
        @Autowired
        GroupAuthorizationRepository authRepo;
        
        @Bean       
        public PlatUserDetailsService platUserDetailsService() {
            PlatUserDetailsService _userService = new PlatUserDetailsService();
            _userService.setAccountRepository(accountRepo);
            _userService.setGroupAuthorizationRepository(authRepo);
            _userService.setGroupMembersRepository(groupMemberRepo);
            return _userService;
        }
    
        @Bean
        public DAOAuthenticationProvider dAOAuthenticationProvider() {
            DAOAuthenticationProvider _authProvider = new DAOAuthenticationProvider();
            _authProvider.setUserDetailsService(platUserDetailsService());
            return _authProvider;
        }            
    
  
}
