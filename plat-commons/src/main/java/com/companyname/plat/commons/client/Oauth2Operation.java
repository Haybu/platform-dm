/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.commons.client;

import java.util.Arrays;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 *
 * @author hmohamed
 */
public class Oauth2Operation {
    
    private static final String END_POINT = "http://localhost:8080/greeting";
	private static final String ACCESS_TOKEN_URI = "http://localhost:8080/oauth/token";
	
	public static void main(String[] args) {
		// call oauth2 secured API resource
		getTrustedMessage();
	}
	
	public static void getTrustedMessage() 
	{
		//Greeting greeting = trustedClientRestTemplate().getForObject(URI.create(END_POINT), Greeting.class);		       
	}
	
	public static OAuth2RestTemplate trustedClientRestTemplate() 
	{
		return new OAuth2RestTemplate(readOnlyResourceDetails(), new DefaultOAuth2ClientContext());
	}

	public static OAuth2ProtectedResourceDetails readOnlyResourceDetails() 
	{
		ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();		                
                details.setClientId("my-client-with-registered-redirect");
                details.setClientSecret("somesecret");
                details.setScope(Arrays.asList("read"));
		details.setId("dmApp/trusted");
		details.setUsername("marissa");
		details.setPassword("koala");		
		details.setAccessTokenUri(URLProvider.getUrl("/sparklr2/oauth/token"));
		return details;
	}
        
        
        public static OAuth2ClientContext getOauth2ClientContext(OAuth2AccessToken accessToken) {
            return null;
        }
        
        /**
	 * Get the current access token. Should be available inside a test method as long as a resource has been setup with
	 * {@link OAuth2ContextConfiguration &#64;OAuth2ContextConfiguration}.
	 * 
	 * @return the current access token initializing it if necessary
	 */
	public static OAuth2AccessToken getAccessToken(OAuth2ProtectedResourceDetails resource,OAuth2RestTemplate client ) {
		if (resource == null || client == null) {
			return null;
		}
		
		try {			
			return client.getAccessToken();
		}
		catch (OAuth2AccessDeniedException e) {
			Throwable cause = e.getCause();
			if (cause instanceof RuntimeException) {
				throw (RuntimeException) cause;
			}
			if (cause instanceof Error) {
				throw (Error) cause;
			}
			throw e;
		}
	}
    
}
