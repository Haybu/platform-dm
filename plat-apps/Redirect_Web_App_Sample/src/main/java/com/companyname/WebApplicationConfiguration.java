/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.companyname;

import java.util.logging.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 *
 * @author hmohamed
 */
@Configuration
@EnableAutoConfiguration
@Import({ServicesConfiguration.class, SecurityConfiguration.class})
@ComponentScan(excludeFilters = @ComponentScan.Filter({Service.class, Repository.class}))
@Order(3)
public class WebApplicationConfiguration //extends WebMvcConfigurerAdapter                
{

    private static final Logger logger
            = Logger.getLogger(WebApplicationConfiguration.class.getName());

    public WebApplicationConfiguration() {
        logger.info("User redirect app web Config is loaded");
    }

    /**
     * @Override public void addViewControllers(ViewControllerRegistry registry)
     * { registry.addViewController("/login").setViewName("login"); }
     */
}
