package com.example.mockband.controller;

import com.example.mockband.repository.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    public UserInfoService userInfoService;

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

    @RequestMapping("/test")
    public void countUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int num = userInfoService.countUser();
        response.setStatus(200);
        response.getWriter().write(String.valueOf(num));
    }

    @RequestMapping("/password")
    public String password(){
        return "/user-password";
    }
}
