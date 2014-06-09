package com.companyname.controller;

import com.companyname.plat.preferences.UserSecurityPreferences;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hmohamed on 4/22/14.
 */

@Controller
public class LoginController {
    
    private static final Logger logger = 
                Logger.getLogger(LoginController.class.getName());
    
    @Autowired
    UserSecurityPreferences userPrefs;

    @RequestMapping(value = {"/"})
    public String home(Model model
            , HttpServletRequest request
            , @CookieValue(value="plat-access-token", required=false) String accessToken 
            , @CookieValue(value="plat-refresh-token", required=false) String refreshToken) 
    {   
        logger.info("inside security app home");
        
        logger.info("User generated Access Token = " + accessToken);
        logger.info("User generated Refresh Token = " + refreshToken);
        
        model.addAttribute("technology", "Spring Boot");  
        
        // redirect to a preferred app if any
         String userRefPage = 
                userPrefs.getPreferredURL(request.getServerName()
                            , request.getUserPrincipal().getName());
         
        if (StringUtils.hasText(userRefPage)) {
            logger.info("User should be redirected to " + userRefPage);  
            return "redirect:"+userRefPage;
        }
        
        return "home";
    }
    
    @RequestMapping(value = {"/login"})
    public String login(Model model) {        
        return "login";
    }        

}
