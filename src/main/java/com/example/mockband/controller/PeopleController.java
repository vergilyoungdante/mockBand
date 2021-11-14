package com.example.mockband.controller;


import com.example.mockband.entity.CbankInfo;
import com.example.mockband.entity.User;
import com.example.mockband.entity.UserInfo;
import com.example.mockband.model.EnumMsgCode;
import com.example.mockband.model.ResultMsgBuilder;
import com.example.mockband.service.AccountInfoService;
import com.example.mockband.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

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
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String department = request.getParameter("department");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userInfoService.modifyInfo(user.getName(), phone, department);
        accountInfoService.modifyInfo(user.getName(), password);

        ResultMsgBuilder.success(new HashMap<>(),response);
    }

    @RequestMapping("/query/credit")
    public ModelAndView queryCredit(){
        ModelAndView modelAndView = new ModelAndView("/people/form-people-credit");
        //这里查一下信用分
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInfo userInfo = userInfoService.queryInfo(user.getName());
        modelAndView.addObject("userCredit", userInfo.getUserCredits());
        return modelAndView;
    }

    @RequestMapping("/query/balance")
    public ModelAndView queryBalance(){
        ModelAndView modelAndView = new ModelAndView("/people/form-people-balance");
        //这里查一下两种余额
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInfo userInfo = userInfoService.queryInfo(user.getName());
        modelAndView.addObject("userCoin", userInfo.getUserGrowingCoin());
        modelAndView.addObject("userBond", userInfo.getUserBond());
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String change = request.getParameter("change");//转出金额
        String type = request.getParameter("type");//交易类型，1成长币，2债券
        String content = request.getParameter("content");//交易备注
        String toAccount = request.getParameter("account");//对方账号

        //检查账户是否存在
        boolean isAccount = accountInfoService.queryInfo(toAccount);
        if (!isAccount)
        {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"账户不存在",response);
            return;
        }

        //如果转出金额大于余额
        boolean isSuccess = userInfoService.checkAmount(user.getName(), Double.parseDouble(change), type);
        if(isSuccess){
            ResultMsgBuilder.success(new HashMap<>(),response);
        }else {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"余额不足",response);
        }
    }

    @RequestMapping("/commit/change")
    public void commitChange(HttpServletRequest request, HttpServletResponse response){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String change = request.getParameter("change");//转出金额
        String type = request.getParameter("type");//交易类型，1成长币，2债券
        String target = request.getParameter("target");//交易类型，1央行，2商业银行，3个人
        String content = request.getParameter("content");//交易备注
        String toAccount = request.getParameter("account");//对方账号

        //如果转出金额大于余额
        boolean isSuccess = userInfoService.checkAmount(user.getName(), Double.parseDouble(change), type);
        if(isSuccess){
            userInfoService.transfer(user.getName(), Double.parseDouble(change), type, target, toAccount);
            ResultMsgBuilder.success(new HashMap<>(),response);
        }else {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"余额不足",response);
        }
    }
}
