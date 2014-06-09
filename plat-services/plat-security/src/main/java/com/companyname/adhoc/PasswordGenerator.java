/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.adhoc;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author hmohamed
 */
@Component
public class PasswordGenerator {
    
    public static void main(String[] args) {
         System.out.println("BCrypted password: " + bcryptPassword("toy"));
    }
    
    public static String bcryptPassword(String password) {        
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);        
    }
    
}
