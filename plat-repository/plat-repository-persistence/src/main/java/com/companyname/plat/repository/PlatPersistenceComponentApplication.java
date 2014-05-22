package com.companyname.plat.repository;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.companyname.plat.commons.context.SprintContextPrinter;
import com.companyname.plat.repository.persistence.dao.AccountRepository;
import com.companyname.plat.repository.persistence.dao.GroupAuthorizationRepository;
import com.companyname.plat.repository.persistence.dao.GroupMembersRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author hmohamed
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class PlatPersistenceComponentApplication         
{                      
    public static void main(String[] args) throws Exception 
    {
        SpringApplication app = new SpringApplication(PlatPersistenceComponentApplication.class);
        app.setShowBanner(false);
        app.run(args);  
        
    }          
             
}
