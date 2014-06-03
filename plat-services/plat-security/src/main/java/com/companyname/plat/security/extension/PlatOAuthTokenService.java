/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.security.extension;

import java.util.logging.Logger;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

/**
 * 
 * not in use
 *
 * @author hmohamed
 */
//@Component
//@ConfigurationProperties(prefix="oauth2")
public class PlatOAuthTokenService {
    
    private static final Logger logger = 
            Logger.getLogger(PlatOAuthTokenService.class.getName());
    
    /**
     * supposed to be set and over-writeen automatically by spring 
     * from application.properties file (inside common module). Property should
     * have "oauth2." as a prefix
     */
    String tokenEndPoint = null;  
    String clientId = null;   
    String clientSecret = null;        
    
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
    
    public PlatformTokens generatePlatTokens(String userLoginName, String userPassword)
            throws OAuthSystemException, OAuthProblemException
    {
        logger.info("Getting OAuth tokens from URL: " + getTokenEndPoint().trim() 
                + " for clientID: " + getClientId().trim()
                + " and clientSecret: " + getClientSecret().trim()
                + " for userName: " + userLoginName
                + " and userPassword: " + userPassword);
        
        OAuthClientRequest request = OAuthClientRequest
                .tokenLocation(getTokenEndPoint().trim())
                .setClientId(this.getClientId().trim())
                .setClientSecret(this.getClientSecret().trim())                                
                .setGrantType(GrantType.PASSWORD)
                .setUsername(userLoginName)
                .setPassword(userPassword)                
                .buildBodyMessage();

            OAuthClient client = new OAuthClient(new URLConnectionClient());                        

            OAuthAccessTokenResponse oauthResponse = 
                client.accessToken(request, OAuthJSONAccessTokenResponse.class);
            
            if (oauthResponse == null) {
                logger.info("No oauth response obtained.");
                return null;
            }
            
            PlatformTokens tokens = new PlatformTokens();

            tokens.setAccessToken(isIssued(oauthResponse.getAccessToken()));
            tokens.setExpiry(oauthResponse.getExpiresIn());
            tokens.setRefreshToken(isIssued(oauthResponse.getRefreshToken()));
            
            return tokens;
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
