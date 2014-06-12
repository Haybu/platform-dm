package com.companyname.controllers;

import com.companyname.services.OnLogoutHandler;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import java.util.logging.Logger;

/**
 * Created by hmohamed on 6/05/14.
 */

@Controller
public class HomeController {

    private static final Logger logger =
            Logger.getLogger(HomeController.class.getName());
    
    //@Value("${plat.base.loginUrl}")
    //String loginURL;

    @RequestMapping(value = {"/app"})
    public String home(Model model) 
    {
        logger.info("Inside the redirect app");

        model.addAttribute("technology", "the fantastic Spring Boot");        
        return "home";
    }

    private String getLoginURL(HttpServletRequest request)
    {
        Cookie agentHost = WebUtils.getCookie(request, "agent-host-name");

        if (agentHost == null) {
            throw new RuntimeException("System Error, agent Host cookis is not null. Please make sure your system supports cookies");
        }

        String url = "http://" + agentHost.getValue() + "/login";
        return url;
    }

    @RequestMapping(value = {"/loginRedirect"})
    public String loginRedirect(HttpServletRequest request)
    {
        String url = getLoginURL(request);
        // now go to the user's preferred application
        logger.info("user login page is at: " + url);
        return "redirect:" + url;
    }
    
    @RequestMapping(value = {"/403"})
    public String loginError(Model model, HttpServletRequest request)
            throws ServletException
    {
        logger.info("403 authorization redirect");

        // logout me out
        logout(request);

        // take me back to the original login page
        // TODO: should be retrieved from user's first login action
        String loginErrorPageRedirect = "redirect:"+getLoginURL(request)+"?role_error";
        logger.info("user should be landed back at " + loginErrorPageRedirect);
        return loginErrorPageRedirect;
    }

    private void logout(HttpServletRequest request) throws ServletException {
        logger.info("logging out invoked.");
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();

        // if user logged in but not enough authorization
        if (authentication != null && authentication.isAuthenticated()) {
            logger.info("user still logged in at " + request.getContextPath()
                    + " and will be logged out.");
            request.logout();
            //request.setAttribute("role_error", "unauthorized request");
        }
    }

}
