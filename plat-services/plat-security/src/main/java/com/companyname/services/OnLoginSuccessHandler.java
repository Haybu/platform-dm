/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.companyname.services;

import com.companyname.plat.commons.Constants;
import com.companyname.extension.PlatAuthentication;
import com.companyname.extension.PlatOAuthPasswordGrant;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author hmohamed
 */
@Service
@ConfigurationProperties(prefix = "plat.security")
public class OnLoginSuccessHandler
        extends SavedRequestAwareAuthenticationSuccessHandler
        implements InitializingBean {

    private static final Logger logger
            = Logger.getLogger(OnLoginSuccessHandler.class.getName());

    @Autowired
    private PlatOAuthPasswordGrant passwordGrant;

    //@Autowired
    //UserSecurityPreferences userPrefs;
    String cookiePath;
    int cookieExpireTimeLength;
    String accessTokenCookieName;
    String refreshTokenCookieName;
    String cookieDomain;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        try {
            logger.info("platform: On login success handler, setting Oauth2 tokens");

            // Obtain OAuth2 tokens and set then into the authentication object
            passwordGrant.generatePlatTokens(authentication);

            // clear any existing cookies
            invalidateCookiesIfAny(request, response); 

            // set tokens in Cookies
            setCookies(request, response, authentication
         );
                
            // redirect to user preferred app
            // setRedirectPerUserPreference(request, authentication);

        } catch (Exception e) {
            logger.severe("Error on Success Login for userName: "
                    + authentication.getName() + " , reason: " + e.getMessage());
        }

        super.onAuthenticationSuccess(request, response, authentication);

    }

    private void invalidateCookiesIfAny(HttpServletRequest request,
            HttpServletResponse response) 
    {
        logger.info("Clear any existing old cookie before logging in");
        PlatCookieService cookieService = new PlatCookieService();
        cookieService.setAccessTokenCookieName(getAccessTokenCookieName());
        cookieService.setRefreshTokenCookieName(getRefreshTokenCookieName());
        cookieService.setCookieDomain(this.getCookieDomain());
        cookieService.setCookiePath(this.getCookiePath(request));
        cookieService.invalidateCookies(request, response);
    }

    private void setCookies(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logger.info("setting new security cookies");
        PlatAuthentication auth = (PlatAuthentication) authentication;

        if (auth != null) {
            response.addCookie(createCookie(request, getAccessTokenCookieName(), (String) auth.getTokens().getAccessToken()));

            response.addCookie(createCookie(request, getRefreshTokenCookieName(), (String) auth.getTokens().getRefreshToken()));
        }
    }

    private Cookie createCookie(HttpServletRequest request, String name, String value) {
        logger.info("create a new token with name: " + name);
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(getCookieDomain());
        cookie.setPath(getCookiePath(request));
        cookie.setMaxAge(getCookieExpireTimeLength());
        return cookie;
    }

    /**
     * override the default page in the request with a user preferred page if
     * any
     *
     * @param request
     * @param authentication
     */
    /**
     * protected void setRedirectPerUserPreference(HttpServletRequest request ,
     * Authentication authentication) { Assert.notNull(request, "Request passed
     * as null into setRedirectPerUserPreference(...)");
     *
     * logger.info("Getting the preferred app ") ;      *
     * // get username String userName = (authentication.getPrincipal() == null)
     * ? "NONE_PROVIDED" : authentication.getName();      *
     * String userRefPage = userPrefs.getPreferredURL(request.getServerName(),
     * userName);
     *
     * logger.info("User will be redirected to a prefered application at " +
     * userRefPage);      *
     * if (StringUtils.hasText(userRefPage)) { logger.info("User should be
     * redirected to " + userRefPage); this.setAlwaysUseDefaultTargetUrl(true);
     * this.setDefaultTargetUrl(userRefPage); } }
    * *
     */
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

    public int getCookieExpireTimeLength() {
        return cookieExpireTimeLength;
    }

    public void setCookieExpireTimeLength(int cookieExpireTimeLength) {
        this.cookieExpireTimeLength = cookieExpireTimeLength;
    }

    public String getAccessTokenCookieName() {
        return (accessTokenCookieName == null)
                ? Constants.DEFAULT_ACCESS_TOKEN_COOKIE_NAME : accessTokenCookieName;
    }

    public void setAccessTokenCookieName(String accessTokenCookieName) {
        this.accessTokenCookieName = accessTokenCookieName;
    }

    public String getRefreshTokenCookieName() {
        return (refreshTokenCookieName == null)
                ? Constants.DEFAULT_REFRESH_TOKEN_COOKIE_NAME : refreshTokenCookieName;
    }

    public void setRefreshTokenCookieName(String refreshTokenCookieName) {
        this.refreshTokenCookieName = refreshTokenCookieName;
    }

    public String getCookieDomain() {
        return cookieDomain;
    }

    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(cookieDomain, "cookie domain property is null");
        Assert.notNull(cookiePath, "cookie path property is null");
        Assert.notNull(cookieExpireTimeLength, "cookie expiry time property is null");
        Assert.notNull(accessTokenCookieName, "cookie accessToken name property is null");
        Assert.notNull(refreshTokenCookieName, "cookie refreshToken name property is null");

        logger.info("cookie properties ["
                + cookieDomain + "," + cookiePath + "," + cookieExpireTimeLength
                + "," + accessTokenCookieName + "," + refreshTokenCookieName + "]");
    }

}
