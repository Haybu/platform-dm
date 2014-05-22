package com.companyname;

import com.companyname.plat.commons.context.SprintContextPrinter;
import com.companyname.plat.repository.PlatPersistenceComponentApplication;
import com.companyname.plat.security.PlatSecurityComponentApplication;
import com.companyname.plat.security.providers.DAOAuthenticationProvider;
import java.util.logging.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public class Application {
    
    private static final boolean PRINT_MY_BEANS = false;

    public static void main(String[] args) 
    {
        SpringApplication app = new SpringApplication(WebApplicationConfiguration.class);
        app.setShowBanner(false);
        ApplicationContext ctx = app.run(args);
        if (PRINT_MY_BEANS) {
                SprintContextPrinter.print(ctx);
            }
    }  
    
    
    @Configuration
    @EnableAutoConfiguration
    @ComponentScan(includeFilters = @ComponentScan.Filter({Service.class, Repository.class}), useDefaultFilters = false)
    //@PropertySources(value = {@PropertySource("classpath*:application.properties")})
    static class ServicesConfig            
            extends PlatPersistenceComponentApplication 
            implements ApplicationContextAware           
    {
        private static final Logger logger = 
            Logger.getLogger(ServicesConfig.class.getName()); 
        
        public ServicesConfig(){
            logger.info("Inner Services Config is loaded");
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            print("Services", applicationContext, logger);
        }
    }
    
    @Configuration
    @EnableAutoConfiguration
    @Import({ServicesConfig.class})
    @ComponentScan(excludeFilters = 
            @ComponentScan.Filter({ Service.class, Repository.class, Configuration.class }))
    static class WebApplicationConfiguration implements ApplicationContextAware
    {
            private static final Logger logger = 
                Logger.getLogger(WebApplicationConfiguration.class.getName());
            
            public WebApplicationConfiguration(){
                logger.info("Inner web Config is loaded");
            }
            
            @Override
            public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
                print("Web", applicationContext, logger);
            }
    }
    
    /**
    @Configuration
    static class SecurityConfig extends WebSecurityConfigurerAdapter {   
    
            @Autowired
            DAOAuthenticationProvider dAOAuthenticationProvider;
                        
            @Override
            public void configure (AuthenticationManagerBuilder auth) 
                    throws Exception 
            {
                auth.authenticationProvider(dAOAuthenticationProvider);
            }


             @Override
            protected void configure(HttpSecurity http) throws Exception {
                http
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login").permitAll()
                    .and()
                    .logout().deleteCookies().permitAll();

            } 
    }
    * */
    
    
     public static void print(String contextName, ApplicationContext applicationContext, Logger logger) 
        {
            String[] beanNames = applicationContext.getBeanDefinitionNames();
            StringBuilder printBuilder = new StringBuilder(
                    "Spring Beans the " + contextName + " Context: ");        
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
