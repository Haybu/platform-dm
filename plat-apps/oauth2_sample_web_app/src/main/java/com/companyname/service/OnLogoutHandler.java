/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.service;

import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 *
 * @author hmohamed
 */
public class OnLogoutHandler implements LogoutHandler {
    
    private static final Logger logger = 
                Logger.getLogger(OnLogoutHandler.class.getName());
    
    //private UserCache userCache;

    public void logout(final HttpServletRequest request
                , final HttpServletResponse response
                , final Authentication authentication) 
    {
        // ....
    }

    /**
    @Required
    public void setUserCache(final UserCache userCache) {
        this.userCache = userCache;
    }
    * **/
}
