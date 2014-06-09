/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.extension;

import java.util.Collection;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.common.OAuth2RefreshToken;

/**
 *
 * @author hmohamed
 */
public class PlatAuthentication extends UsernamePasswordAuthenticationToken {
    
    PlatformTokens tokens;
    Object userCredentials;   
    
    public static Authentication getPlatAuthentication (Authentication authentication) {
        
        if (authentication == null) {
            return null;
        }
        
        PlatAuthentication auth = new PlatAuthentication(
                authentication.getPrincipal()
                , authentication.getCredentials()
                , authentication.getAuthorities());
        
        BeanUtils.copyProperties(authentication, auth, new String[] {"authenticated"});       
          
        return auth;
    }
    
    public PlatAuthentication (Object principal, Object credentials) {
        super(principal, credentials);
        this.userCredentials = credentials;
    }
    
    public PlatAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.userCredentials = credentials;
    }
    
    public PlatformTokens getTokens() {
        return tokens;
    }

    public void setTokens(PlatformTokens _tokens) {
        this.tokens = _tokens;
    }
    
    public Object getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(Object userCredentials) {
        this.userCredentials = userCredentials;
    }
         
}
