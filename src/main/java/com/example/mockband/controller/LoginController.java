package com.example.mockband.controller;


import com.example.mockband.entity.User;
import com.example.mockband.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    public UserInfoService userInfoService;

    @RequestMapping("/cbank")
    public String toAdmin(){
        return "/welcome-1";
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ModelAndView modelAndView = null;
        switch (user.getAccountType())
        {
            case 1: modelAndView = new ModelAndView("/index");break;
            case 2: modelAndView = new ModelAndView("/welcome-2");break;
            case 3: modelAndView = new ModelAndView("/welcome-3");break;
        }
        modelAndView.addObject("abc","kkkkksdfsfsf");

        return modelAndView;
    }

    @RequestMapping("/password")
    public String password(){
        return "/user-password";
    }
}
