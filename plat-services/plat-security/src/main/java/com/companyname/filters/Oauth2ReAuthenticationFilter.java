/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.companyname.filters;

import com.companyname.plat.commons.Constants;
import com.companyname.extension.PlatAuthentication;
import com.companyname.extension.PlatformTokens;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author hmohamed
 */
//@Component
//@ConfigurationProperties(prefix="oauth2")
public class Oauth2ReAuthenticationFilter implements Filter {

    private static final Logger logger
            = Logger.getLogger(Oauth2ReAuthenticationFilter.class.getName());
    
    DefaultTokenServices tokenService;

    TokenStore tokenStore;                

    private String accessTokenCookieName;
    private String refreshTokenCookieName;
    private String clientId;  

  
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException 
    {         
        performFilter(req,res,chain);         
    }
    
    public void performFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException 
    {

        logger.info("Oauth2 Re-Authentication filter starts.");
        
        Boolean authenticated = true;
        
        HttpServletRequest request = (HttpServletRequest) req;

        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            logger.info("user redirected and needs to be re-authenticated via oauth2 token stored in cookies");
            
            logger.info("get access token cookie");
            Cookie accessTokenCookie = getCookie(request, getAccessTokenCookieName());
            logger.info("get refresh token cookie");
            Cookie refreshTokenCookie = getCookie(request, getRefreshTokenCookieName());

            String accessTokenValue = null;
            String refreshTokenValue = null;

            if (refreshTokenCookie != null) {                
                refreshTokenValue = accessTokenCookie.getValue();   
                logger.info("refresh token cookie is retrieved from cookie with value = " + refreshTokenValue);                                               
            }
            
            if (accessTokenCookie != null) {
                accessTokenValue = accessTokenCookie.getValue();
                logger.info("access token cookie is retrieved from cookie with value = " + accessTokenValue);                                                 
            }
            
            if (accessTokenValue != null) {
                authentication = getAuthenticationFromAccessToken(accessTokenValue);
                logger.info("authentication object is obtained successfully via the access token");
            }

            if (authentication != null) {
                /**
                PlatAuthentication appAuthentication
                        = (PlatAuthentication) authentication;
                PlatformTokens tokens = new PlatformTokens();
                tokens.setAccessToken(accessTokenValue);
                tokens.setRefreshToken(refreshTokenValue);
                appAuthentication.setTokens(tokens);
                SecurityContextHolder.getContext().setAuthentication(appAuthentication);
                * **/
                
                if (!authentication.isAuthenticated()) {
                    logger.info("Although the authentication object is retrieved via the access token " +
                            " however, it's marked as not authenticated.");
                }
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("security context is loaded with the user's authentication object");
            } else {
                authenticated = false;
                logger.info("Authentication failure, no authentication is loaded into the security context");
            }
                     
        }        

        if (authenticated) {
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            
            for(String role: roles) {
                logger.info("role found: " + role);
            }

            if (roles.contains("ROLE_USER")) {
                logger.info("This user has role of ROLE_USER");
                request.getSession().setAttribute("myVale", "myvalue");
            }
            chain.doFilter(req, res);
        } else {
            throw new RuntimeException("No Oauth2 authentication object retrieved");
        }

    }
    
    private String refreshAccesTokenIfExpired(String accessTokenValue, Authentication authentication) {
        OAuth2AccessToken accessToken = getTokenService().readAccessToken(accessTokenValue);
        if (accessToken != null && accessToken.isExpired() && authentication != null) {
            logger.info("access token is expired. will refresh");
            accessToken = getTokenService().createAccessToken((OAuth2Authentication)authentication);            
        } else if (accessToken != null && !accessToken.isExpired()) {
            logger.info("access token is not expired");
        }
        
        return (accessToken==null)? null: accessToken.getValue();
    }

    private Cookie getCookie(HttpServletRequest request, String name) {
        return WebUtils.getCookie(request, name);
    }

    private Authentication getAuthenticationFromAccessToken(String _accessTokenValue) {
        logger.info("obtaining authentication object via access token");
        if (_accessTokenValue == null) {
            return null;
        }
        return getTokenService().loadAuthentication(_accessTokenValue);
    }
    
    private Authentication getAuthenticationFromRefreshToken(String _refreshTokenValue) {
        logger.info("obtaining authentication object via refresh token");
        if (_refreshTokenValue == null) {
            return null;
        }
        
        OAuth2RefreshToken refreshToken = getTokenStore().readRefreshToken(_refreshTokenValue);
 	if (refreshToken == null) {
            throw new InvalidGrantException("Invalid refresh token: " + _refreshTokenValue);		
        }
        
        return getTokenStore().readAuthenticationForRefreshToken(refreshToken);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // Do nothing
    }

    @Override
    public void destroy() {
        // Do nothing
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
    
     public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    public DefaultTokenServices getTokenService() {
        return tokenService;
    }

    public void setTokenService(DefaultTokenServices tokenService) {
        this.tokenService = tokenService;
    }

    public TokenStore getTokenStore() {
        return tokenStore;
    }

    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }       

}
