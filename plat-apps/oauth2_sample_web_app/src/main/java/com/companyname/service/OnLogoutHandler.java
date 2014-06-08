/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.service;

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
    
    
    //private UserCache userCache;

    public void logout(HttpServletRequest request
                , HttpServletResponse response
                , Authentication authentication) 
    {
        if (removeTokens(request, authentication)) {
            logger.info("OAuth2 tokens are revoked after logging out.");
        }
                
        cancelCookies(request, response); 
        logger.info("Oauth2 tokens cookies are cancelled after logging out");
    }
    
    private boolean removeTokens(HttpServletRequest request, Authentication authentication) {
        String accessTokenValue = getCookieValue(request, getAccessTokenCookieName());
        // refresh token will be revoked as well
        return tokenService.revokeToken(accessTokenValue);         
    }
    
    private Cookie getCookie(HttpServletRequest request, String name) {
        return WebUtils.getCookie(request, name);
    }
    
    private String getCookieValue(HttpServletRequest request, String key) {
        Cookie cookie = getCookie(request, key);
        return (cookie==null)? null : cookie.getValue();        
    }
    
    private void cancelCookies(HttpServletRequest request
                , HttpServletResponse response) {
        cancelCookie(request, response, getAccessTokenCookieName());
        cancelCookie(request, response, getRefreshTokenCookieName());
    }
    
    private void cancelCookie(HttpServletRequest request
            , HttpServletResponse response
            , String _cookieName) 
    {
        logger.info("On Logout: cancelling cookie named: " + _cookieName);
        Cookie cookie = new Cookie(_cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath(request));
        cookie.setDomain(getCookieDomain());
        response.addCookie(cookie);
    }

    /**
    @Required
    public void setUserCache(final UserCache userCache) {
        this.userCache = userCache;
    }
    * **/
    
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
}
