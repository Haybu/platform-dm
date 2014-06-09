/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname;

import com.companyname.providers.DAOAuthenticationProvider;
import java.util.logging.Logger;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author hmohamed
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {
        Application.class
       })
public class LoginTest {
    
    private static final Logger logger = 
            Logger.getLogger(LoginTest.class.getName());
    
    @Autowired
    DAOAuthenticationProvider provider;
    
    @Test
    public void authenticateUserTest() 
    {      
        logger.info("running test to authenticate a user ");
	try {
            Authentication request = 
                    new UsernamePasswordAuthenticationToken("toy", "toy");
            Authentication result = provider.authenticate(request);
            
            //SecurityContextHolder.getContext().setAuthentication(result);
            //SecurityContextHolder.getContext().getAuthentication()
                  
            logger.info("Successfully authenticated. Security context contains: " +
                result);
     
            assertThat(result, IsNull.notNullValue());
            
         } catch(AuthenticationException e) {
            System.out.println("Authentication Test failed: " + e.getMessage());
        }
    }
        
}
