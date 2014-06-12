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
    
    public String getPreferredURL (String userName)
    {
        //TODO: should be read from the database user refs store
        // for now, hard-coded to one value
        logger.info("Reading the preferred app from the ref store") ;                                
        return "http://leander.drillmap.com:9090/w1/app";
    }
    
}
