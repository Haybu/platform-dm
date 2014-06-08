package com.companyname;

import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@EnableAutoConfiguration
@ComponentScan
public class Application {
    
     private static final Logger logger = 
                Logger.getLogger(Application.class.getName());       

    public static void main(String[] args) 
    {
        SpringApplication app = new SpringApplication(Application.class);
        app.setShowBanner(false);
        app.run(args);       
    }             
            /**
    @Configuration
    @EnableWebMvc
    @ComponentScan(excludeFilters = @ComponentScan.Filter({ Service.class, Repository.class }))
    public static class WebApplicationConfig extends WebMvcConfigurerAdapter 
    {

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new OAuthTokenRequestInterceptor()).addPathPatterns("/app/**");;           
        }
        
         // ---------------------------------------------------- Static Resources    
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {      
          registry.addResourceHandler("/static/**").addResourceLocations("/static/");
          registry.addResourceHandler("/css/**").addResourceLocations("/css/");
          registry.addResourceHandler("/images/**").addResourceLocations("/images/");
          registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        }
        
    }    
    * 
    *         * **/
      
}
