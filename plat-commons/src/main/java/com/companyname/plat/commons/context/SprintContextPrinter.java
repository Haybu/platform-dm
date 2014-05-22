/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.commons.context;

import java.util.logging.Logger;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author hmohamed
 */
public class SprintContextPrinter {
    
    private static final Logger logger = 
            Logger.getLogger(SprintContextPrinter.class.getName()); 
    
    public static void print(ApplicationContext applicationContext) 
    {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        StringBuilder printBuilder = new StringBuilder(
                "Spring Beans this module Context: ");        
        printBuilder.append("\n<><<><><<<><><><><><><><><><><><>");
        
        for(String beanName : beanNames)
        {
            printBuilder.append("\n");
            printBuilder.append(" Bean Name: ");
            printBuilder.append(beanName);
            printBuilder.append(" Bean Type: ");
            printBuilder.append(applicationContext.getBean(beanName).getClass());
        }
        logger.info(printBuilder.toString());
    }
    
}
