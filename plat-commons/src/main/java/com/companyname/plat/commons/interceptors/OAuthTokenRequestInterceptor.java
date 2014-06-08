/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.commons.interceptors;

import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author hmohamed
 */
@ConfigurationProperties(prefix="plat.base")
public class OAuthTokenRequestInterceptor implements HandlerInterceptor 
{    
    private String loginUrl;
             
    private static final Logger logger = 
                Logger.getLogger(OAuthTokenRequestInterceptor.class.getName());

    @Override
    public boolean preHandle(HttpServletRequest request
            , HttpServletResponse response, Object handler) throws Exception 
    {
        logger.info("Executing preHandle(...) method");
        
        Cookie accessTokenCookie = WebUtils.getCookie(request, "plat-access-token");
        Cookie refreshTokenCookie = WebUtils.getCookie(request, "plat-refresh-token");
        
        if (accessTokenCookie != null && refreshTokenCookie != null) {
            
        }
        
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response
            , Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("Executing postHandle(...) method");       
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response
            , Object handler, Exception ex) throws Exception {
        logger.info("Executing afterCompletion(...) method");
    }
    
    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
   
}
