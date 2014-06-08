/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 *
 * @author hmohamed
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(includeFilters = @ComponentScan.Filter({Service.class, Repository.class}), useDefaultFilters = false)
@Order(1)
public class ServicesConfiguration {
    
}
