package com.companyname.controllers;

import com.companyname.services.OnLogoutHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

/**
 * Created by hmohamed on 6/05/14.
 */

@Controller
public class HomeController {

    private static final Logger logger =
            Logger.getLogger(HomeController.class.getName());
    
    @Value("${plat.base.loginUrl}")
    String loginURL;

    @RequestMapping(value = {"/app"})
    public String home(Model model) 
    {
        logger.info("---> from JRebel");

        model.addAttribute("technology", "the fantastic Spring Boot");        
        return "home";
    }   
    
    @RequestMapping(value = {"/403"})
    public String loginError(Model model, HttpServletRequest request) 
            throws ServletException
    {
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
                
        // if user logged in but not enough authorization
        if (authentication != null) {
            request.logout();
        }
        
        return "redirect:" + loginURL + "?role_error";
    }   

}
