package com.companyname.controller;

import com.companyname.plat.preferences.UserSecurityPreferences;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String home(Model model, HttpServletRequest request, HttpServletResponse response)
    {
        logger.info("inside security app home");
        
        model.addAttribute("technology", "Spring Boot");

        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            String principalName = request.getUserPrincipal().getName();

            logger.info("==> At security home controller, user is authenticated and will be redirected to the preferred app");

            /**
             * Here the user is authenticated successfully in this login app context.
             * In other words, a valid authenticated token exists in the context of this security app.
             * Before we redirect to a different application context - where the application there is responsible to build
             * it's own authentication token from the cookies (oauth2 access token cookies) - we should invalidate the generated
             * authentication token in this login context.
             */
            //logger.info("inside security app: before redirect, invalidate the current authentication token");
            //SecurityContextHolder.getContext().setAuthentication(null);


            // redirect to a preferred app if any
            String userRefPage =
                    userPrefs.getPreferredURL(principalName);

            if (StringUtils.hasText(userRefPage)) {
                logger.info("inside security app: redirecting to user preferred app at " + userRefPage);
                return "redirect:" + userRefPage;
            }
        }
        
        return "home";
    }
    
    @RequestMapping(value = {"/login"})
    public String login(Model model) {        
        return "login";
    }

}
