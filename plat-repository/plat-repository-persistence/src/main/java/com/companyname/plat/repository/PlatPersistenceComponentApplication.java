package com.companyname.plat.repository;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 *
 * @author hmohamed
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.companyname.plat.repository"})
@PropertySources(value = {@PropertySource("classpath:application.properties")})
public class PlatPersistenceComponentApplication         
{                      
    public static void main(String[] args) throws Exception 
    {
        
        SpringApplication app = new SpringApplication(PlatPersistenceComponentApplication.class);
        app.setShowBanner(false);
        app.run(args);  
        
    }          
             
}
