/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.services;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author hmohamed
 */
@Service
public class PlatUserAuthenticationCache {
    
    private static final Logger logger = 
            Logger.getLogger(PlatUserAuthenticationCache.class.getName()); 
    
    private Map<String, Authentication> cache;
    
    public PlatUserAuthenticationCache() {}
    
    private void init() {
        if (cache == null) {
            cache = new HashMap<>();
        }        
    }
    
    private String getHashKey(Authentication authentication) 
    {    
        String auth = authentication.getName() + ":" +
                (String) authentication.getCredentials();
        byte[] encodedAuth = Base64.encode(auth.getBytes(Charset.forName("US-ASCII")));
	String key = "Key-" + new String( encodedAuth );
        
        logger.info("authentication caching operation uses a key = " + key);
        
        return key;
    }
    
    public void add(Authentication authentication) 
    {              
        Assert.notNull(authentication, "cannot cache a null authentication");        
        init();
        String key = getHashKey(authentication);
        
        if (!cache.containsKey(key)) {            
            cache.put(key, authentication);
            logger.info("authentication request is cached for user " 
                    + authentication.getName());
        } else {
            logger.info("authentication request was already cached for user " 
                    + authentication.getName());
        }
    }
    
    public void remove(Authentication authentication)
    {      
       Assert.notNull(authentication, "cann't remove a null authentication");        
       init(); 
       String key = getHashKey(authentication);
       
       if (cache.containsKey(key)) {  
           cache.remove(key);
           logger.info("authentication request is removed from cache for user " 
                    + authentication.getName());
       } else {
           logger.info("authentication request is not in the cache to remove for user " 
                    + authentication.getName());
       }
    }
    
    public Authentication get(Authentication authentication) 
    {       
       Assert.notNull(authentication, "cann't retreive a null authentication");        
       init(); 
       String key = getHashKey(authentication);
       
       if (cache.containsKey(key)) {             
           logger.info("authentication request is retreived from cache for user " 
                    + authentication.getName());
           return cache.get(key);
       } else {
           logger.info("authentication request is not found in cache for user " 
                   + authentication.getName());
           return null;
       }              
    }
    
    public boolean isCached(Authentication authentication) 
    {       
        Assert.notNull(authentication, "cann't validate a null authentication from cache");  
        init();
        String key = getHashKey(authentication);     
        return cache.containsKey(key);
    }
    
    public Authentication authenticateFromCache (Authentication authentication) {
        logger.info("Attemptting to authenticate from cache ");
        Assert.notNull(authentication, "cann't authenticate a null authentication object from cache");  
        
        Authentication cachedAuthentication = get(authentication);
        
        if (cachedAuthentication != null) {
            remove(cachedAuthentication);
            return cachedAuthentication;
        } else {
            return authentication;
        }                
    }
    
}
