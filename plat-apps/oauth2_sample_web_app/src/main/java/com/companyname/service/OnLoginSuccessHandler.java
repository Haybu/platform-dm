/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.service;

import com.companyname.plat.security.extension.PlatAuthentication;
import com.companyname.plat.security.extension.PlatOAuthPasswordGrant;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 *
 * @author hmohamed
 */
public class OnLoginSuccessHandler 
        extends SavedRequestAwareAuthenticationSuccessHandler {
    
    private static final Logger logger = 
                Logger.getLogger(OnLoginSuccessHandler.class.getName());
    
    @Autowired
    PlatOAuthPasswordGrant passwordGrant;
    
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
                HttpServletResponse response, Authentication authentication)
                throws IOException, ServletException 
        {                                                                                    
            try {   
                logger.info("platform: On login success handler, setting Oauth2 tokens");  
                
                // Obtain OAuth2 tokens and set then into the authentication object
                passwordGrant.generatePlatTokens(authentication);                                                                                             
                
                // set tokens in Cookies
                setCookies(response, authentication);
                
                // redirect to user preferred app
                setRedirectPerUserPreference(request);

            } catch (Exception e) {
                    logger.severe("Error on Success Login for userName: " 
                            + authentication.getName() + " , reason: "+ e.getMessage());
            }
   
            super.onAuthenticationSuccess(request, response, authentication);
            
        }
        
        private void setCookies(HttpServletResponse response, Authentication authentication) {
             PlatAuthentication auth = (PlatAuthentication) authentication;
             
             if (auth != null) {
                 response.addCookie(new Cookie("plat-access-token"
                         ,  (String)auth.getTokens().getAccessToken()));
                 
                 response.addCookie(new Cookie("plat-refresh-token"
                         ,  (String)auth.getTokens().getRefreshToken()));
             }
        }
        
        /**
         * override the default page in the request with a user 
         * preferred page if any
         * 
         * @param request 
         */
        protected void setRedirectPerUserPreference(HttpServletRequest request) 
        {          
            Assert.notNull(request, "Request passed as null into setRedirectPerUserPreference(...)");
            
            logger.info("Getting the preferred app ") ;  
            
            String userRefPage = getPreferredURL(request);
            
            logger.info("Preferred app is " + userRefPage);           
                
            if (StringUtils.hasText(userRefPage)) {
                logger.info("User should be redirected to " + userRefPage);  
                this.setAlwaysUseDefaultTargetUrl(true);
                this.setDefaultTargetUrl(userRefPage);
            }
              
        }
        
        /**
         * 
         * @param request
         * @return 
         */
        private String getPreferredURL (final HttpServletRequest request) {
            //TODO: should be read from the database user refs store
            // for now, hard-coded to one value
            
            logger.info("Reading the preferred app from the ref store") ; 
            
            String appServerName = request.getServerName();
            String appPortNumber = "9090";
            String appContext = "w1";
            
            return "http://" + appServerName + ":" + appPortNumber + "/" + appContext;
        }
    
}
