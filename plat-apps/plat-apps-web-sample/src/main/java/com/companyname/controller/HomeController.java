package com.companyname.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hmohamed on 4/22/14.
 */

@Controller
public class HomeController {

    @RequestMapping(value = {"/"})
    public String home(Model model) {
        model.addAttribute("technology", "Spring Boot");
        return "home";
    }
    
    @RequestMapping(value = {"/login"})
    public String login(Model model) {        
        return "login";
    }

}
