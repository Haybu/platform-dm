package com.companyname;

import com.companyname.plat.repository.PlatPersistenceComponentApplication;
import com.companyname.plat.security.providers.DAOAuthenticationProvider;
import com.companyname.service.OnLoginSuccessHandler;
import com.companyname.service.OnLogoutHandler;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public class Application {
    
     private static final Logger logger = 
                Logger.getLogger(Application.class.getName());      

    public static void main(String[] args) 
    {
        SpringApplication app = new SpringApplication(WebApplicationConfiguration.class);
        app.setShowBanner(false);
        app.run(args);        
    }    
    
    @Configuration
    @EnableAutoConfiguration
    @ComponentScan(includeFilters = @ComponentScan.Filter({Service.class, Repository.class}), useDefaultFilters = false)
    //@PropertySources(value = {@PropertySource("classpath*:application.properties")})
    static class ServicesConfig            
            extends PlatPersistenceComponentApplication                       
    {                
        public ServicesConfig(){
            logger.info("... Inner Services Config is loaded");
        }       
    }
    
    @Configuration
    @EnableAutoConfiguration
    @Import({Stuff.class, ServicesConfig.class
                , SecurityConfig.class
                , ResourceServerConfiguration.class
                , AuthorizationServerConfiguration.class})
    @ComponentScan(excludeFilters = 
            @ComponentScan.Filter({ Service.class, Repository.class }))
    static class WebApplicationConfiguration 
                //extends WebMvcConfigurerAdapter                
    {                          
            public WebApplicationConfiguration(){
                logger.info("Inner web Config is loaded");
            }
            
            /**
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/login").setViewName("login");
             }  
             */                       
    }
    
    @Configuration
    @EnableAutoConfiguration
    static class SecurityConfig 
        extends WebSecurityConfigurerAdapter 
    {       
        @Autowired
        DAOAuthenticationProvider daoAuthenticationProvider;
        
        @Autowired
        OnLoginSuccessHandler onLoginHandler;
        
        @Autowired
        OnLogoutHandler onLogoutHandler;
            
        public SecurityConfig() {
            logger.info("Inner security Config is loaded");
        }
                        
        @Override
        public void configure (AuthenticationManagerBuilder auth) 
                throws Exception 
        {
            logger.info("Configuring web app with a custom DAO authentication provider");
            auth.authenticationProvider(daoAuthenticationProvider);
        }
         
        /**
        @Bean
        public OnLoginSuccessHandler loginSuccessHandler() {
            return new OnLoginSuccessHandler();
        }
        
        @Bean
        public OnLogoutHandler logoutHandler() {
            return new OnLogoutHandler();
        }
        * **/
               

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().requireCsrfProtectionMatcher(
                        new AntPathRequestMatcher("/oauth/authorize")).disable()
                .formLogin()
                .successHandler(onLoginHandler)
                .loginPage("/login").permitAll()
                .and()
                .logout()    
                .addLogoutHandler(onLogoutHandler)
                .invalidateHttpSession(true)
                .deleteCookies().permitAll();
        } 
            
        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() 
                throws Exception 
        {
            return super.authenticationManagerBean();
        }
    }
    
    
    // OAuth2 configuration
    //--------------------------------------------------------------------------
    // not a significant name
    private static final String RESOURCE_ID = "dmAPI";
        
    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration 
            extends ResourceServerConfigurerAdapter 
    {
	@Override
        public void configure(ResourceServerSecurityConfigurer resources)
        {
            logger.info("inside ResourceServerConfiguration.configure(...)");
            resources.resourceId(RESOURCE_ID);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception
        {
            logger.info("inside ResourceServerConfiguration.configure(...)");

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
                    .access("#oauth2.clientHasRole('ROLE_CLIENT') and #oauth2.isClient() and #oauth2.hasScope('read')")
                .regexMatchers(HttpMethod.GET, "/oauth/authentications/.*")
                    .access("#oauth2.clientHasRole('ROLE_CLIENT') and #oauth2.isClient() and #oauth2.hasScope('read')");
	}

    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration 
            extends AuthorizationServerConfigurerAdapter          
    {	
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;  
        
        @Autowired
        DataSource appDataSource;
        
        @Bean
        public JdbcClientDetailsService clientDetailsService() {
            return new JdbcClientDetailsService(appDataSource);
        }

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception
        {
            logger.info("inside AuthorizationServerConfiguration.configure(...)");                       		
            clients.jdbc(appDataSource).clients(clientDetailsService())
                ;                    				
        }

	@Bean    
	public TokenStore tokenStore() {
            logger.info("inside AuthorizationServerConfiguration.tokenStore(...)");                           
            return new JdbcTokenStore(appDataSource);
        }                

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception
        {
            logger.info("inside AuthorizationServerConfiguration.configure(...)");
            endpoints.tokenStore(tokenStore())
                     .authenticationManager(authenticationManager);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception
        {
            logger.info("inside AuthorizationServerConfiguration.configure(...)");
            oauthServer.realm("myApp/client");
	}

    }
	
    // not in use now. But keep it for future 3 legs scenarios
    @Configuration
    static class Stuff 
    {	
	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private TokenStore tokenStore;

	@Bean
	public ApprovalStore approvalStore() throws Exception
        {
            logger.info("inside Stuff.approvalStore()");

            TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		return store;
        }
        
        @Bean
        public DefaultTokenServices defaultTokenServices() {
            DefaultTokenServices defaultTokenService = new DefaultTokenServices();
            defaultTokenService.setClientDetailsService(clientDetailsService);
            defaultTokenService.setSupportRefreshToken(true);
            defaultTokenService.setTokenStore(tokenStore);
            return defaultTokenService;
        }
    }  
  
}
