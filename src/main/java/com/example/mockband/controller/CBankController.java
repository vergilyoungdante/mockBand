package com.example.mockband.controller;

import com.example.mockband.entity.User;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/cbank")
public class CBankController {

    @RequestMapping("/total")
    public ModelAndView totalCount(){
        ModelAndView modelAndView = new ModelAndView("/cbank/total-count");
        //TODO:获得所有中央银行数据
        //需要所有3个数据
        //我这里把数据加进去给前端用
        //modelAndView.addObject();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return modelAndView;
    }

    @RequestMapping("/change/bond")
    public void changeBond(HttpServletRequest request, HttpServletResponse response){
        String change = request.getParameter("change");
    }
    @RequestMapping("/change/coin")
    public void changeCoin(HttpServletRequest request, HttpServletResponse response){
        String change = request.getParameter("change");
    }

    @RequestMapping("/register")
    public ModelAndView toRegister(){
        ModelAndView modelAndView = new ModelAndView("/cbank/register");

        return modelAndView;
    }

    @RequestMapping("/create/bank")
    public void createBank(HttpServletRequest request, HttpServletResponse response){

    }

    @RequestMapping("/transfer")
    public String transfer(){
        return "/cbank/transfer";
    }

    @RequestMapping("/check/count")
    public void checkCount(HttpServletRequest request, HttpServletResponse response){
        //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String change = request.getParameter("count");//转出金额
        String type = request.getParameter("type");//交易类型，成长币还是债券
        String content = request.getParameter("content");//交易备注

    }

    @RequestMapping("/commit/change")
    public void commitChange(HttpServletRequest request, HttpServletResponse response){
        //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String change = request.getParameter("count");//转出金额
        String type = request.getParameter("type");//交易类型，成长币还是债券
        String content = request.getParameter("content");//交易备注
        String toBank = request.getParameter("toBank");//对方银行账号

    }



    @RequestMapping("/transfer/log")
    public String transferLog(){
        return "/cbank/transfer-log-coin";
    }

    @RequestMapping("/query/transfer/log")
    public void queryTransferLog(HttpServletRequest request, HttpServletResponse response){
        String fromBank = request.getParameter("fromBank");
        String toBank = request.getParameter("toBank");
        String type = request.getParameter("type");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");







    }

}
