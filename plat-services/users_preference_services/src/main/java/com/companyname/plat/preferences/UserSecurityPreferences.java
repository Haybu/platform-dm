/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.preferences;


import java.util.logging.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author hmohamed
 */
@Component
public class UserSecurityPreferences {
    
    private static final Logger logger = 
                Logger.getLogger(UserSecurityPreferences.class.getName());
    
    public String getPreferredURL (String serverName, String userName) 
    {
        //TODO: should be read from the database user refs store
        // for now, hard-coded to one value
        
        String app = "/w1/app";
        String port = ":9090";
            
        logger.info("Reading the preferred app from the ref store") ;                                
        return "http://" + serverName + port + app;
    }
    
}
