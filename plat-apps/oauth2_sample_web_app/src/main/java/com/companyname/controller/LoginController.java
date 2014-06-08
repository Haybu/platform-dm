package com.companyname.controller;

import com.companyname.service.OnLoginSuccessHandler;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hmohamed on 4/22/14.
 */

@Controller
public class LoginController {
    
    private static final Logger logger = 
                Logger.getLogger(LoginController.class.getName());

    @RequestMapping(value = {"/"})
    public String home(Model model
            , @CookieValue(value="plat-access-token", required=false) String accessToken 
            , @CookieValue(value="plat-refresh-token", required=false) String refreshToken) 
    {   
        logger.info("inside security app home");
        
        logger.info("User generated Access Token = " + accessToken);
        logger.info("User generated Refresh Token = " + refreshToken);
        
        model.addAttribute("technology", "Spring Boot");                                                        
        return "home";
    }
    
    @RequestMapping(value = {"/login"})
    public String login(Model model) {        
        return "login";
    }        

}
