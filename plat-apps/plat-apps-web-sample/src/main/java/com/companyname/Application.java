package com.companyname;

import com.companyname.plat.commons.context.SprintContextPrinter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class Application {
    
    private static final boolean PRINT_MY_BEANS = false;

    public static void main(String[] args) 
    {
        SpringApplication app = new SpringApplication(Application.class);
        app.setShowBanner(false);
        ApplicationContext ctx = app.run(args);
        if (PRINT_MY_BEANS) {
                SprintContextPrinter.print(ctx);
            }
    }

}
