/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.services;

import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author hmohamed
 */
public class PlatCookieService {
    
    private static final Logger logger = 
                Logger.getLogger(PlatCookieService.class.getName());
    
    private String accessTokenCookieName;
    private String refreshTokenCookieName;
    private  String agentHostCookieName;
    private String cookiePath;
    private String cookieDomain;
    DefaultTokenServices tokenService;

    public DefaultTokenServices getTokenService() {
        return tokenService;
    }

    public void setTokenService(DefaultTokenServices tokenService) {
        this.tokenService = tokenService;
    }

    public String getAccessTokenCookieName() {
        return accessTokenCookieName;
    }

    public void setAccessTokenCookieName(String accessTokenCookieName) {
        this.accessTokenCookieName = accessTokenCookieName;
    }

    public String getRefreshTokenCookieName() {
        return refreshTokenCookieName;
    }

    public void setRefreshTokenCookieName(String refreshTokenCookieName) {
        this.refreshTokenCookieName = refreshTokenCookieName;
    }

    public String getCookiePath() {
        return cookiePath;
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
    
    public boolean removeTokenValues(HttpServletRequest request, Authentication authentication) {
        String accessTokenValue = getCookieValue(request, getAccessTokenCookieName());
        // refresh token will be revoked as well
        return tokenService.revokeToken(accessTokenValue);         
    }
    
    public Cookie getCookie(HttpServletRequest request, String name) {
        return WebUtils.getCookie(request, name);
    }
    
    public String getCookieValue(HttpServletRequest request, String key) {
        Cookie cookie = getCookie(request, key);
        if(cookie == null) {
            logger.info("cookie for key \"" + key + "\" is not found");
        } else {
            logger.info("cookie for key \"" + key + "\" = " + cookie.getValue());
        }
        return (cookie==null)? null : cookie.getValue();        
    }
    
    public void invalidateCookies(HttpServletRequest request
                , HttpServletResponse response) 
    {
        invalidateCookie(request, response, getAccessTokenCookieName());
        invalidateCookie(request, response, getRefreshTokenCookieName());
        //invalidateCookie(request, response, getAgentHostCookieName());
    }
    
    public void invalidateCookie(HttpServletRequest request
            , HttpServletResponse response
            , String _cookieName) 
    {
        logger.info("cancelling cookie named: " + _cookieName);
        
        // if cookie does not exist, do nothing
        Cookie cookie = getCookie(request, _cookieName);
        
        if (cookie == null) {
            return;
        }
        
        cookie = new Cookie(_cookieName, null);
        cookie.setValue("");
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath());
        cookie.setDomain(getCookieDomain());
        response.addCookie(cookie);
    }

    public String getAgentHostCookieName() {
        return agentHostCookieName;
    }

    public void setAgentHostCookieName(String agentHostCookieName) {
        this.agentHostCookieName = agentHostCookieName;
    }
    
}
