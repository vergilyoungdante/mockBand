package com.example.mockband.controller;

import com.example.mockband.entity.*;
import com.example.mockband.mapper.BankInfoMapper;
import com.example.mockband.model.EnumMsgCode;
import com.example.mockband.model.ResultMsg;
import com.example.mockband.model.ResultMsgBuilder;
import com.example.mockband.service.AccountInfoService;
import com.example.mockband.service.BankInfoService;
import com.example.mockband.service.CbankInfoService;
import com.example.mockband.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("/bank")
public class BankController {

    @Autowired
    BankInfoService bankInfoService;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    AccountInfoService accountInfoService;

    @Autowired
    CbankInfoService cbankInfoService;

    @RequestMapping("/info")
    public ModelAndView identity(){
        ModelAndView modelAndView = new ModelAndView("/bank/form-bank-info");
        //查询
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BankInfo bankInfo = bankInfoService.queryInfo(user.getName());
        modelAndView.addObject("bankName", bankInfo.getBankName());
        modelAndView.addObject("bankHead", bankInfo.getBankHead());
        //todo: 如何获取bankLicense
        modelAndView.addObject("bankType",bankInfo.getBankType());
        return modelAndView;
    }

    @RequestMapping("/modify/info")
    public void modifyIdentity(HttpServletRequest request, HttpServletResponse response){
        String bankName = request.getParameter("bankName");
        String bankHead = request.getParameter("bankHead");
        String bankType = request.getParameter("bankType");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bankInfoService.modifyInfo(user.getName(), bankName, bankHead, bankType);
    }

    @RequestMapping("/query/bank/credit")
    public ModelAndView queryCredit(){
        ModelAndView modelAndView = new ModelAndView("/bank/form-bank-credit");
        //这里查一下信用分
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BankInfo bankInfo = bankInfoService.queryInfo(user.getName());
        modelAndView.addObject("bankCredit", bankInfo.getBankCredits());
        return modelAndView;
    }

    @RequestMapping("/query/bank/balance")
    public ModelAndView queryBalance(){
        ModelAndView modelAndView = new ModelAndView("/bank/form-bank-balance");
        //这里查一下两种余额
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BankInfo bankInfo = bankInfoService.queryInfo(user.getName());
        modelAndView.addObject("bankCoin", bankInfo.getBankGrowingCoin());
        modelAndView.addObject("bankBond", bankInfo.getBankBond());
        return modelAndView;
    }

    @RequestMapping("/transfer")
    public String transfer(){
        return "/bank/form-step-bank-transfer";
    }

    @RequestMapping("/transfer/bank/log")
    public String transferLog(){
        return "/bank/table-bank-search";
    }

    @RequestMapping("/query/transfer/bank/log")
    public void queryTransferLog(HttpServletRequest request, HttpServletResponse response){
        String fromBank = request.getParameter("fromBank");
        String toBank = request.getParameter("toBank");
        String type = request.getParameter("type");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");


    }

    @RequestMapping("/people")
    public ModelAndView toRegister(){
        ModelAndView modelAndView = new ModelAndView("/bank/form-bank-create-people");

        return modelAndView;
    }

    @RequestMapping("/create/people")
    public ResultMsg<String> createPeople(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam("userName") String userName,
                                  @RequestParam("bankName") String bankName,
                                  @RequestParam("newPassword") String newPassword,
                                  @RequestParam("againPassword") String againPassword,
                                  @RequestParam("loginName") String loginName) throws IOException {


//        if(file.isEmpty()){
//            jsonObject.put("message","需要上传执照");
//            response.getWriter().write(jsonObject.toString());
//            return;
//        }
        if(!newPassword.equals(againPassword)){
            return ResultMsgBuilder.success("test",null);
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setLoginName(loginName);
        userInfo.setBankName(bankName);

        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setLoginName(loginName);
        accountInfo.setLoginPassword(newPassword);
        accountInfo.setAccountType(3);

        //todo:如果要创建的银行已经存在，该如何返回
        userInfoService.createUser(userInfo);
        accountInfoService.createAccount(accountInfo);
        //ResultMsgBuilder.success("test")
        return ResultMsgBuilder.success("test",null);
    }

    @RequestMapping("/check/count")
    public void checkCount(HttpServletRequest request, HttpServletResponse response){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String change = request.getParameter("change");//转出金额
        String type = request.getParameter("type");//交易类型，1成长币,2债券
        String content = request.getParameter("content");//交易备注
        String toAccount = request.getParameter("account");//对方账号

        //检查账户是否存在
        boolean isAccount = accountInfoService.queryInfo(toAccount);
        if (!isAccount)
        {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"账户不存在",response);
            return;
        }

        //检查账户是否为央行或个人
        BankInfo bankInfo = bankInfoService.queryInfo(toAccount);
        CbankInfo cbankInfo = cbankInfoService.queryInfo(toAccount);
        if (bankInfo == null && cbankInfo == null)
        {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"该账户不是央行或个人",response);
            return;
        }

        //如果转出金额大于余额
        boolean isSuccess = bankInfoService.checkAmount(user.getName(), Double.parseDouble(change), type);
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
        String type = request.getParameter("type");//交易类型，1成长币,2债券
        String target = request.getParameter("target");//交易类型，1央行，2商业银行，3个人
        String content = request.getParameter("content");//交易备注
        String toAccount = request.getParameter("account");//对方账号

        //如果转出金额大于余额
        boolean isSuccess = bankInfoService.checkAmount(user.getName(), Double.parseDouble(change), type);
        if(isSuccess){
            bankInfoService.transfer(user.getName(), Double.parseDouble(change), type, target, toAccount, content);
            ResultMsgBuilder.success(new HashMap<>(),response);
        }else {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"余额不足",response);
        }
    }
}
