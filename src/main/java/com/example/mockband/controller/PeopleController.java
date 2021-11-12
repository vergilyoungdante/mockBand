package com.example.mockband.controller;


import com.example.mockband.entity.CbankInfo;
import com.example.mockband.entity.User;
import com.example.mockband.entity.UserInfo;
import com.example.mockband.service.AccountInfoService;
import com.example.mockband.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    AccountInfoService accountInfoService;

    @RequestMapping("/identity")
    public ModelAndView identity(){
        ModelAndView modelAndView = new ModelAndView("/people/form-people-identity");
        //查询
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInfo userInfo = userInfoService.queryInfo(user.getName());
        modelAndView.addObject("userName", userInfo.getUserName());
        modelAndView.addObject("userMobile", userInfo.getUserMobile());
        modelAndView.addObject("userDepartment",userInfo.getUserDepartment());
        return modelAndView;
    }

    @RequestMapping("/modify/identity")
    public void modifyIdentity(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        String sex  = request.getParameter("sex");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String department = request.getParameter("department");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userInfoService.modifyInfo(phone, department);
        accountInfoService.modifyInfo(user.getName(), password);
    }

    @RequestMapping("/query/credit")
    public ModelAndView queryCredit(){
        ModelAndView modelAndView = new ModelAndView("/people/form-people-credit");
        //这里查一下信用分

        return modelAndView;
    }

    @RequestMapping("/query/balance")
    public ModelAndView queryBalance(){
        ModelAndView modelAndView = new ModelAndView("/people/form-people-balance");
        //这里查一下两种余额

        return modelAndView;
    }

    @RequestMapping("/transfer/log")
    public String transferLog(){
        return "/people/table-people-search";
    }

    @RequestMapping("/query/transfer/log")
    public void queryTransferLog(HttpServletRequest request, HttpServletResponse response){
        String fromBank = request.getParameter("fromBank");
        String toBank = request.getParameter("toBank");
        String type = request.getParameter("type");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");


    }


    @RequestMapping("/transfer")
    public String transfer(){
        return "/people/form-step-people-transfer";
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
}
