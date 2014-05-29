package com.companyname.controller;

import com.companyname.plat.security.extension.PlatAuthentication;
import com.companyname.plat.security.extension.PlatformTokens;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hmohamed on 4/22/14.
 */

@Controller
public class LoginController {
    
     private static final Logger logger = 
                Logger.getLogger(LoginController.class.getName());

    @RequestMapping(value = {"/"})
    public String home(Model model) {
        
       // current authentication object from the security context
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       
       if (auth != null) {
           PlatformTokens tokens = ((PlatAuthentication)auth).getTokens();
           
           logger.info(">> user: " + auth.getName());
           logger.info(">> access Token: " + ((tokens==null)? 
                   "No Value": (String)tokens.getAccessToken()));
           logger.info(">> refresh Token: " + ((tokens==null)? 
                   "No Value": (String)tokens.getRefreshToken()));
       }
        
        model.addAttribute("technology", "Spring Boot");
        return "home";
    }
    
    @RequestMapping(value = {"/login"})
    public String login(Model model) {        
        return "login";
    }

}
