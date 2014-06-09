/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.extension;

import java.util.logging.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.stereotype.Service;

/**
 *
 * @author hmohamed
 */
@Service
@ConfigurationProperties(prefix="oauth2")
public class PlatOAuthPasswordGrant {
    
    private static final Logger logger = 
            Logger.getLogger(PlatOAuthPasswordGrant.class.getName());
    
    /**
     * supposed to be set and over-written automatically by spring 
     * from application.properties file (inside common module). Property should
     * have "oauth2." as a prefix
     */
    String tokenEndPoint = null; 
    String authorizationEndPoint = null;   
    String clientId = null;   
    String clientSecret = null; 
    
    public PlatOAuthPasswordGrant() {}
    
     public String getAuthorizationEndPoint() {
        return authorizationEndPoint;
    }

    public void setAuthorizationEndPoint(String authorizationEndPoint) {
        this.authorizationEndPoint = authorizationEndPoint;
    }
    
    public String getTokenEndPoint() {
        return tokenEndPoint;
    }

    public void setTokenEndPoint(String tokenEndPoint) {
        this.tokenEndPoint = tokenEndPoint;
    }
    
     public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    
     public void generatePlatTokens(Authentication authentication)
           
    {
        PlatAuthentication auth = (PlatAuthentication)authentication;
        
        String userLoginName = auth.getName();
        String userPassword = (String) auth.getUserCredentials();
        
        logger.info("Getting OAuth tokens from URL: " + getTokenEndPoint().trim() 
                + " for clientID: " + getClientId().trim()
                + " and clientSecret: " + getClientSecret().trim()
                + " for userName: " + userLoginName
                + " and userPassword: " + userPassword);
        
        OAuth2Template template = new OAuth2Template(getClientId().trim(),
                      getClientSecret().trim(),
                      getAuthorizationEndPoint().trim(),
                      getTokenEndPoint().trim());
        
        //template.setUseParametersForClientAuthentication(false);

        AccessGrant grant = template.exchangeCredentialsForAccess(userLoginName, userPassword, null);
            
        if (grant == null) {
            logger.info("No OAuth tokens are obtained for user: " + auth.getName());
            auth.setUserCredentials(null);  // clear user credentials/password            
        } else {
            logger.info("OAuth tokens are obtained successfully for user: " + auth.getName());
            PlatformTokens tokens = new PlatformTokens();
            tokens.setAccessToken(isIssued(grant.getAccessToken()));
            tokens.setExpiry(grant.getExpireTime());
            tokens.setRefreshToken(isIssued(grant.getRefreshToken()));
            auth.setTokens(tokens);
        }

        auth.setUserCredentials(null);  // clear user credentials/password            
    }  
    
    private static String isIssued(String value) {
        if (isEmpty(value)) {
            return "(Not Issued)";
        }
        return value;
    }
     
    private static boolean isEmpty(String value) {
        return value == null || "".equals(value);
    }
}
