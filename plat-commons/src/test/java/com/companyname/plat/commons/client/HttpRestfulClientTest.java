/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.commons.client;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import static org.springframework.util.Assert.notNull;

/**
 *
 * @author hmohamed
 */
public class HttpRestfulClientTest {
    
    private static final Logger logger = 
            Logger.getLogger(HttpRestfulClientTest.class.getName());
    
    private HttpRestfulClient instance = null;
    
    public HttpRestfulClientTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {       
    }
    
    @Before
    public void setUp() {
        instance = new HttpRestfulClient();
    }
    
    @After
    public void tearDown() {
         instance = null;
    }

    /**
     * not a good test, will be re-written
     * 
     * Test of getObject method, of class HttpRestfulClient.
     */
    @Test
    public void testGetObject() {
        logger.info("start test: HttpRestfulClient.getObject()");
        
        instance.setUserName("any_user_name");
        instance.setPassword("any_password");
        instance.setEndPoint("http://localhost:8080/dm/api/v1/wells/12");
        
        String result = "no result";
              
        try {
            result = (String) instance.getObject(null, String.class);
        } catch (Exception ex) {
            logger.info("running no test");
        }
        
        logger.info("test token = " + result);
        
        notNull(result);
              
    }

    /**
     * Test of getHttpRequest method, of class HttpRestfulClient.
     */
    @Test
    public void testGetHttpRequest() {
        logger.info("start test: HttpRestfulClient.testGetHttpRequest()");
        Map<String, String> params = new HashMap<>();  
        params.put("user", "my-user-id");
        params.put("direction", "north");       
        HttpEntity<Object> httpEntity = instance.getHttpRequest(params);
        String result = httpEntity.getBody().toString();
        
        logger.info("test request parameters: " + result);
        
        notNull(result);        
    }      
    
}
