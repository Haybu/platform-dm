package com.companyname.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hmohamed on 6/05/14.
 */

@Controller
public class HomeController {

    @RequestMapping(value = {"/"})
    public String home(Model model
            , @CookieValue("plat-access-token") String accessToken 
            , @CookieValue("plat-refresh-token") String refreshToken) 
    {                
                                                   
        model.addAttribute("accessToken", accessToken);
        model.addAttribute("refreshToken", refreshToken);
        model.addAttribute("technology", "the fantastic Spring Boot");
        
        return "home";
    }      

}
