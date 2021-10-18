package com.example.mockband.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/admin")
    public String toAdmin(){
        return "/adminPage";
    }

    @RequestMapping("/bank")
    public String toBank(){
        return "/bankPage";
    }

    @RequestMapping("/people")
    public String toPeople(){
        return "/peoplePage";
    }

}
