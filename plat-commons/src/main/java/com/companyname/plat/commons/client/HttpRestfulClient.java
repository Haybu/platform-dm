/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.commons.client;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author hmohamed
 */
public class HttpRestfulClient {
    
    private static final Logger logger = 
            Logger.getLogger(HttpRestfulClient.class.getName());
    
    String endPoint;
    String userName;
    String password;
    
    MediaType acceptHeader;
    MediaType contentTypeHeader; 
    
    String responseStatus;    
    
    public HttpRestfulClient() {}
    
    public Object getObject(Map<String, String> params, Class clz) 
    {       
        RestTemplate restTemplate = new RestTemplate();
                		
	try {				
            ResponseEntity<Object> entity = 
		restTemplate.exchange(getEndPoint() , HttpMethod.GET
			, getHttpRequest(params), clz);
                
            setResponseStatus(entity.getStatusCode().toString());					        
	    return entity.getBody();	       	        	        	                
	} 
        catch (HttpClientErrorException ex) {
            if (HttpStatus.UNAUTHORIZED == ex.getStatusCode()) {
		System.out.println("Unauthorized call to " + this.getEndPoint()
		    	+ "\nWrong login and/or password (\"" 
                        + this.getUserName() + "\" / \"" + this.getPassword() + "\")");

                System.out.println("Cause: \n" + ex.getMessage() );
            }
	}  
        
        return null;
    }
    
    // builds http header with authorization
    // authorization in the form of: "login:password"
    private HttpHeaders getHeaders() 
    {
        HttpHeaders headers = this.getUnsecuredHeaders();
        
        String auth = ((getUserName()==null)? "":getUserName()) 
                + ":" 
                + ((getPassword()==null)? "":getPassword());
        
        if (":".equals(auth)) {
            return headers;
        }
        	
	byte[] encodedAuth = Base64.encode(auth.getBytes(Charset.forName("US-ASCII")));
	String authHeader = "Basic " + new String( encodedAuth );	    
	logger.info("http Restfule client has a header auth: " + authHeader);	    
	headers.add("Authorization", authHeader);    
	return headers;
    }
    
    private HttpHeaders getUnsecuredHeaders() 
    {                
        HttpHeaders headers = new HttpHeaders();
        
        if (getContentTypeHeader() != null) {
            headers.setContentType(getContentTypeHeader());
        }
        
        if (getAcceptHeader() != null) {
            headers.setAccept(Arrays.asList(getAcceptHeader()));
        }
        
	return headers;
    }
	
    public HttpEntity<Object> getHttpRequest(Map<String, String> params) 
    {
	// construct headers with login and password        
	HttpHeaders headers = getHeaders();		
	// request body to send
	String requestBodyJSON = buildRequestJasonObject(params);                		
	// HTTP request
	HttpEntity<Object> requestEntity = new HttpEntity<Object> (
			requestBodyJSON, headers);						
	return requestEntity;
    } 
    
    private String buildRequestJasonObject(Map<String, String> params) 
    {
        if (params == null) {
            return null;
        }
        
        Iterator iterator = params.entrySet().iterator(); 
        Map.Entry entry  = null;
        StringBuilder keyValPair = new StringBuilder();
        while (iterator.hasNext()) {
            entry = (Map.Entry<String, String>) iterator.next();
            keyValPair.append(entry.getKey());
            keyValPair.append(":");
            keyValPair.append("\"" + entry.getValue()+ "\",");            
        }
        
        return "{" 
                + StringUtils.trimTrailingCharacter(keyValPair.toString(), ',')
                + "}";               
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }    
    
     public MediaType getAcceptHeader() {
        return acceptHeader;
    }

    public void setAcceptHeader(MediaType acceptHeader) {
        this.acceptHeader = acceptHeader;
    }

    public MediaType getContentTypeHeader() {
        return contentTypeHeader;
    }

    public void setContentTypeHeader(MediaType contentTypeHeader) {
        this.contentTypeHeader = contentTypeHeader;
    }
    
    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }
    
}
