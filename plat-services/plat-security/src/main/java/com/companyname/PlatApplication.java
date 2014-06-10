/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.companyname;

import com.companyname.plat.repository.PlatPersistenceComponentApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author hmohamed
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class PlatApplication
        extends PlatPersistenceComponentApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(PlatApplication.class);
        app.setShowBanner(false);
        app.run(args);
    }
}
