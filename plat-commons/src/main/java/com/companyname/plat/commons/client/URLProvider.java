/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.commons.client;

/**
 *
 * @author hmohamed
 */
public class URLProvider {
    
    private static String hostName = "localhost";
    private static int port = 8080;
    
    public static String getBaseUrl() {
		return "http://" + hostName + ":" + port;
    }

    public static String getUrl(String path) {
		if (path.startsWith("http")) {
			return path;
		}
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		return "http://" + hostName + ":" + port + path;
    }
    
}
