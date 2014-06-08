package com.companyname;

import org.springframework.boot.SpringApplication;


public class Application {          

    public static void main(String[] args) 
    {
        SpringApplication app = new SpringApplication(WebApplicationConfiguration.class);
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
