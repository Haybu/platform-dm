/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.controller;

import com.companyname.plat.security.extension.PlatAOuthPasswordGrant;
import com.companyname.plat.security.extension.PlatAuthentication;
import com.companyname.plat.security.extension.PlatformTokens;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 *
 * @author hmohamed
 */
public class OnLoginSuccessHandler 
        extends SavedRequestAwareAuthenticationSuccessHandler {
    
    private static final Logger logger = 
                Logger.getLogger(OnLoginSuccessHandler.class.getName());
    
    @Autowired
    PlatAOuthPasswordGrant passwordGrant;
    
        @Override
        public void onAuthenticationSuccess(final HttpServletRequest request,
                final HttpServletResponse response, final Authentication authentication)
                throws IOException, ServletException 
        {                                                                                    
            try {   
                logger.info("platform: On login success handler, setting Oauth2 tokens");  
                
                passwordGrant.generatePlatTokens(authentication);                                                                                             
                
                // store OAuth tokens in a session
                /**
                HttpSession session = request.getSession(true);  
                   
                session.setAttribute("x-access-token", 
                        auth.getTokens().getAccessToken());         
                session.setAttribute("x-refresh-token", 
                        auth.getTokens().getRefreshToken());  
                        * **/
                
            } catch (Exception e) {
                    logger.severe("Error in getting User token for userName: " 
                            + authentication.getName() + " , reason: "+ e.getMessage());
            }
   
            super.onAuthenticationSuccess(request, response, authentication);
            
        }
    
}
