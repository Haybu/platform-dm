package com.companyname;

import com.companyname.controller.OnLoginSuccessHandler;
import com.companyname.plat.commons.context.SprintContextPrinter;
import com.companyname.plat.repository.PlatPersistenceComponentApplication;
import com.companyname.plat.security.providers.DAOAuthenticationProvider;
import java.util.logging.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public class Application {
    
     private static final Logger logger = 
                Logger.getLogger(Application.class.getName());
    
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
        public ServicesConfig(){
            logger.info("... Inner Services Config is loaded");
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            //print("Services", applicationContext, logger);
        }
    }
    
    @Configuration
    @EnableAutoConfiguration
    @Import({ServicesConfig.class
                , SecurityConfig.class
                , ResourceServerConfiguration.class
                , AuthorizationServerConfiguration.class
                , Stuff.class})
    @ComponentScan(excludeFilters = 
            @ComponentScan.Filter({ Service.class, Repository.class }))
    static class WebApplicationConfiguration 
                //extends WebMvcConfigurerAdapter
                implements ApplicationContextAware
    {                      
            public WebApplicationConfiguration(){
                logger.info("... Inner web Config is loaded");
            }
            
            /**
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/login").setViewName("login");
             }  
             */
            
            @Override
            public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
                //print("Web", applicationContext, logger);
            }
    }
    
    @Configuration
    @EnableAutoConfiguration
    static class SecurityConfig extends WebSecurityConfigurerAdapter {   
    
            @Autowired
            DAOAuthenticationProvider dAOAuthenticationProvider;
            
            public SecurityConfig() {
                logger.info("... Inner security Config is loaded");
            }
                        
            @Override
            public void configure (AuthenticationManagerBuilder auth) 
                    throws Exception 
            {
                logger.info("... configuring web app with a custom DAO authentication provider");
                auth.authenticationProvider(dAOAuthenticationProvider);
            }
            
            @Bean
            public OnLoginSuccessHandler loginSuccessHandler() {
                return new OnLoginSuccessHandler();
            }

             @Override
            protected void configure(HttpSecurity http) throws Exception {
                http
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize")).disable()
                    .formLogin()
                    .successHandler(loginSuccessHandler())
                    .loginPage("/login").permitAll()
                    .and()
                    .logout()                        
                        .invalidateHttpSession(true)
                        .deleteCookies().permitAll();
            } 
            
            @Override
            @Bean
            public AuthenticationManager authenticationManagerBean() throws Exception {
                return super.authenticationManagerBean();
            }
    }
    
    
    // OAuth2 configuration
    //--------------------------------------------------------------------------
    // not a significant name
    private static final String RESOURCE_ID = "dmAPI";
        
    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
        public void configure(ResourceServerSecurityConfigurer resources)
        {
            logger.info(">>> inside ResourceServerConfiguration.configure(...)");

            resources.resourceId(RESOURCE_ID);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception
        {
            logger.info(">>> inside ResourceServerConfiguration.configure(...)");

            http
		.requestMatchers().antMatchers("/api/greeting/**", "/oauth/users/**", "/oauth/clients/**")
		.and()
		.authorizeRequests()					
		.antMatchers("/api/greeting").access("#oauth2.hasScope('read')")
		.antMatchers("/api/greeting").access("#oauth2.hasScope('trust')")
		.antMatchers("/api/greeting/**").access("#oauth2.hasScope('read')")
		.antMatchers("/api/greeting/**").access("#oauth2.hasScope('trust')")
		.regexMatchers(HttpMethod.DELETE, "/oauth/users/([^/].*?)/tokens/.*")
			.access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('write')")
		.regexMatchers(HttpMethod.GET, "/oauth/clients/([^/].*?)/users/.*")
			.access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('read')")
		.regexMatchers(HttpMethod.GET, "/oauth/clients/.*")
		.access("#oauth2.clientHasRole('ROLE_CLIENT') and #oauth2.isClient() and #oauth2.hasScope('read')");
	}

    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration 
            extends AuthorizationServerConfigurerAdapter
    {
	@Autowired
	private TokenStore tokenStore;
		
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception
        {
            logger.info(">>> inside AuthorizationServerConfiguration.configure(...)");
		
            clients.inMemory()                                     
                    .withClient("my-trusted-client-with-secret")
                    .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit", "client_credentials")
                    .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                    .scopes("read", "write", "trust")
                    .secret("somesecret");	 				
        }

	@Bean
	public TokenStore tokenStore() {
            logger.info(">>> inside AuthorizationServerConfiguration.tokenStore()");

            return new InMemoryTokenStore();
        }

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception
        {
            logger.info(">>> inside AuthorizationServerConfiguration.configure(...)");

            endpoints.tokenStore(tokenStore)
                     .authenticationManager(authenticationManager);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception
        {
            logger.info(">>> inside AuthorizationServerConfiguration.configure(...)");
		oauthServer.realm("myApp/client");
	}

    }
	
    @Configuration
    static class Stuff {
	
	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private TokenStore tokenStore;

	@Bean
	public ApprovalStore approvalStore() throws Exception
        {
            logger.info(">>> B8 inside Stuff.approvalStore()");

            TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		return store;
            }
	} 
    
    
    public static void print(String contextName, ApplicationContext applicationContext, Logger logger) 
    {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        StringBuilder printBuilder = new StringBuilder(
                    "Spring Beans in the " + contextName + " Context: ");        
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
