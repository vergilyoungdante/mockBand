package com.example.mockband.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @RequestMapping("/index")
    public String toAdmin(){
        return "/index";
    }

    @RequestMapping("/bank")
    public String toBank(){
        return "/welcome-2";
    }

    @RequestMapping("/people")
    public String toPeople(){
        return "/welcome-3";
    }

    @GetMapping("/welcome")
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView("/welcome-1");
        modelAndView.addObject("abc","kkkkksdfsfsf");

        return modelAndView;
    }
}
