package com.example.mockband.controller;

import com.example.mockband.entity.*;
import com.example.mockband.model.EnumMsgCode;
import com.example.mockband.model.ResultMsgBuilder;
import com.example.mockband.service.AccountInfoService;
import com.example.mockband.service.BankInfoService;
import com.example.mockband.service.CbankInfoService;
import com.example.mockband.service.TranInfoService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cbank")
public class CBankController {

    @Autowired
    CbankInfoService cbankInfoService;

    @Autowired
    BankInfoService bankInfoService;

    @Autowired
    AccountInfoService accountInfoService;

    @Autowired
    TranInfoService tranInfoService;

    @RequestMapping("/total")
    public ModelAndView totalCount(){
        ModelAndView modelAndView = new ModelAndView("/cbank/total-count");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CbankInfo cbankInfo = cbankInfoService.queryInfo(user.getName());
        modelAndView.addObject("totalCoin", String.valueOf(cbankInfo.getTotalGrowingCoin()));
        modelAndView.addObject("publicCoin", String.valueOf(cbankInfo.getPublicGrowingCoin()));
        modelAndView.addObject("cbankCoin",String.valueOf(cbankInfo.getCbankGrowingCoin()));
        modelAndView.addObject("totalBond", String.valueOf(cbankInfo.getTotalBond()));
        modelAndView.addObject("publicBond", String.valueOf(cbankInfo.getPublicBond()));
        modelAndView.addObject("cbankBond",String.valueOf(cbankInfo.getCbankBond()));
        return modelAndView;
    }

    @RequestMapping("/change/bond")
    public void changeBond(@RequestBody Map<String,Object> param,HttpServletRequest request, HttpServletResponse response){
        String change = param.get("change").toString();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //todo:如果销毁金额大于现有金额，该如何返回
        boolean isSuccess = cbankInfoService.changeBond(user.getName(), Double.parseDouble(change));
        if(isSuccess){
            ResultMsgBuilder.success(new HashMap<>(),response);
        }else {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"余额不足",response);
        }

    }
    @PostMapping("/change/coin")
    public void changeCoin(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response){
        String change = param.get("change").toString();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //todo:如果销毁金额大于现有金额，该如何返回
        boolean isSuccess = cbankInfoService.changeCoin(user.getName(), Double.parseDouble(change));
        if(isSuccess){
            ResultMsgBuilder.success(new HashMap<>(),response);
        }else {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"余额不足",response);
        }

    }

    @RequestMapping("/register")
    public ModelAndView toRegister(){
        ModelAndView modelAndView = new ModelAndView("/cbank/register");

        return modelAndView;
    }

    @RequestMapping("/create/bank")
    public void createBank(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam("userName") String userName,
                           @RequestParam("newPassword") String newPassword,
                           @RequestParam("againPassword") String againPassword,
                           @RequestParam("bankName") String bankName,
                           @RequestParam("bankHead") String bankHead,
                           @RequestParam("credit") String credit,
                           @RequestParam("file") MultipartFile file) throws IOException {

        if(file.isEmpty()){
            response.getWriter().write(new Gson().toJson(ResultMsgBuilder.commonError(EnumMsgCode.NO_PHOTO_ERROR,"需要上传营业执照",response)));
            return;
        }
        if(!newPassword.equals(againPassword)){
            response.getWriter().write(new Gson().toJson(ResultMsgBuilder.commonError(EnumMsgCode.PASSWORD_INCONSISTENT_ERROR,"两次输入密码不一致",response)));
            return;
        }

        BankInfo bankInfo = new BankInfo();
        bankInfo.setLoginName(userName);
        bankInfo.setBankName(bankName);
        bankInfo.setBankHead(bankHead);
        bankInfo.setBankCredits(Double.parseDouble(credit));

        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setLoginName(userName);
        accountInfo.setLoginPassword(newPassword);
        accountInfo.setAccountType(2);

        //todo:如果要创建的银行已经存在，该如何返回
        boolean res1 = bankInfoService.createBank(bankInfo);
        boolean res2 = accountInfoService.createAccount(accountInfo);
        if(res1 && res2)
        {
            response.getWriter().write(new Gson().toJson(ResultMsgBuilder.commonError(EnumMsgCode.SUCCESS,"开户成功",response)));
        }
        else
        {
            response.getWriter().write(new Gson().toJson(ResultMsgBuilder.commonError(EnumMsgCode.REPEAT_ACCOUNT_ERROR,"账户已存在",response)));
        }


    }

    @RequestMapping("/transfer")
    public String transfer(){
        return "/cbank/transfer";
    }

    @RequestMapping("/check/count")
    public void checkCount(HttpServletRequest request, HttpServletResponse response){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String change = request.getParameter("count");//转出金额
        String type = request.getParameter("type");//交易类型，1成长币,2债券
        String content = request.getParameter("content");//交易备注

        //todo:如果转出金额大于余额，该如何返回
        cbankInfoService.checkAmount(user.getName(), Double.parseDouble(change), type);
    }

    @RequestMapping("/commit/change")
    public void commitChange(HttpServletRequest request, HttpServletResponse response){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String change = request.getParameter("count");//转出金额
        String type = request.getParameter("type");//交易类型，1成长币,2债券
        String content = request.getParameter("content");//交易备注
        String toAccount = request.getParameter("toAccount");//对方账号

        cbankInfoService.transfer(user.getName(), Double.parseDouble(change), type, toAccount);
    }

    @RequestMapping("/transfer/log")
    public String transferLog(){
        return "/cbank/transfer-log-coin";
    }

    @RequestMapping("/query/transfer/log")
    public void queryTransferLog(HttpServletRequest request, HttpServletResponse response){
        String query = request.getParameter("query");

        //todo:如何返回查询结果
        String fromAccount = "";
        String toAccount = "";
        String curType = "";
        Date fromDate = new Date();
        Date toDate = new Date();

        tranInfoService.query(fromAccount, toAccount, Integer.parseInt(curType), fromDate, toDate);
    }

}
