/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.security.extension;

/**
 *
 * @author hmohamed
 */
public class PlatformTokens {
    
    private Object accessToken;
    private Object refreshToken;
    private long expiry;
    
    public PlatformTokens() {}

    public Object getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(Object accessToken) {
        this.accessToken = accessToken;
    }

    public Object getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(Object refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getExpiry() {
        return expiry;
    }

    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }
    
    
}
