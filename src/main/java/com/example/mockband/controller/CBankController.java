package com.example.mockband.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cbank")
public class CBankController {

    @RequestMapping("/total")
    public ModelAndView totalCount(){
        ModelAndView modelAndView = new ModelAndView("/cbank/total-count");
        //TODO:获得所有中央银行数据
        //
        //我这里把数据加进去给前端用
        //modelAndView.addObject();

        return modelAndView;
    }

    @RequestMapping("/register")
    public ModelAndView register(){
        ModelAndView modelAndView = new ModelAndView("/cbank/register");
        //TODO:保存注册信息
        //
        //我这里把数据加进去给前端用
        //modelAndView.addObject();

        return modelAndView;
    }

    @RequestMapping("/transfer")
    public String transfer(){
        return "/cbank/transfer";
    }

    @RequestMapping("/transfer/log")
    public String transferLog(){
        return "/cbank/transfer-log-coin";
    }
}
