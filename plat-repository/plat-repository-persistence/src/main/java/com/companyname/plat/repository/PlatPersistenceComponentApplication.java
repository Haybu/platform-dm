package com.companyname.plat.repository;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.companyname.plat.commons.context.SprintContextPrinter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author hmohamed
 */
@EnableAutoConfiguration
@ComponentScan
public class PlatPersistenceComponentApplication         
{
    private static final boolean PRINT_MY_BEANS = false;
            
    public static void main(String[] args) throws Exception 
    {
        SpringApplication app = new SpringApplication(PlatPersistenceComponentApplication.class);
        app.setShowBanner(false);
        ApplicationContext context = app.run(args);  
        if (PRINT_MY_BEANS) {
            SprintContextPrinter.print(context);
        }
    }
             
}