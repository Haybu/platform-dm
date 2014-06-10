/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.services;

import com.companyname.plat.commons.Constants;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author hmohamed
 */
@Service
@ConfigurationProperties(prefix="plat.security")
public class OnLogoutHandler implements LogoutHandler {
    
    private static final Logger logger = 
                Logger.getLogger(OnLogoutHandler.class.getName());
        
    private String accessTokenCookieName;
    private String refreshTokenCookieName; 
    private String cookiePath;
    private String cookieDomain; 
    
    @Autowired
    @Qualifier("defaultTokenServices")
    DefaultTokenServices tokenService;               

    public void logout(HttpServletRequest request
                , HttpServletResponse response
                , Authentication authentication) 
    {
        PlatCookieService cookieService = new PlatCookieService();
        cookieService.setAccessTokenCookieName(getAccessTokenCookieName());
        cookieService.setRefreshTokenCookieName(getRefreshTokenCookieName());
        cookieService.setCookieDomain(getCookieDomain());
        cookieService.setCookiePath(getCookiePath(request));
        cookieService.setTokenService(getTokenService());
        
        // will take the tokens values out of the store
        if (cookieService.removeTokenValues(request, authentication)) {
            logger.info("OAuth2 tokens are revoked from DB store after logging out.");
        }
                
        // clear tokens on cookies
        cookieService.invalidateCookies(request, response); 
        logger.info("Oauth2 tokens cookies are cancelled after logging out");

    }     
    
    public String getAccessTokenCookieName() {
        return (accessTokenCookieName == null)?
                Constants.DEFAULT_ACCESS_TOKEN_COOKIE_NAME : accessTokenCookieName;
    }

    public void setAccessTokenCookieName(String accessTokenCookieName) {
        this.accessTokenCookieName = accessTokenCookieName;
    }

    public String getRefreshTokenCookieName() {
        return (refreshTokenCookieName == null)?
                Constants.DEFAULT_REFRESH_TOKEN_COOKIE_NAME : refreshTokenCookieName;
    }

    public void setRefreshTokenCookieName(String refreshTokenCookieName) {
        this.refreshTokenCookieName = refreshTokenCookieName;
    }
    
    public String getCookiePath(HttpServletRequest request) {        
        if (cookiePath == null) {
    		String contextPath = request.getContextPath();
    		return contextPath.length() > 0 ? contextPath : "/";
    	} else {
    		return cookiePath;
    	}
    }

    public void setCookiePath(String cookiePath) {
        this.cookiePath = cookiePath;
    }
    
    public String getCookieDomain() {
        return cookieDomain;
    }

    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }
    
     public DefaultTokenServices getTokenService() {
        return tokenService;
    }

    public void setTokenService(DefaultTokenServices tokenService) {
        this.tokenService = tokenService;
    }
    
}
