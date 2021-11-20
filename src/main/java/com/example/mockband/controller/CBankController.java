package com.example.mockband.controller;

import com.example.mockband.entity.*;
import com.example.mockband.model.EnumMsgCode;
import com.example.mockband.model.ResultMsgBuilder;
import com.example.mockband.service.*;
import com.example.mockband.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.File;
import java.io.IOException;
import java.util.*;

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

    @Autowired
    UserInfoService userInfoService;

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
        //如果销毁金额大于现有金额
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
        //如果销毁金额大于现有金额
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
                           @RequestParam("bankType") String bankType,
                           @RequestParam("file") MultipartFile file) throws IOException {

        if(file.isEmpty()){
            ResultMsgBuilder.commonError(EnumMsgCode.NO_PHOTO_ERROR,"需要上传营业执照",response);
            return;
        }
        if(!newPassword.equals(againPassword)){
            ResultMsgBuilder.commonError(EnumMsgCode.PASSWORD_INCONSISTENT_ERROR,"密码不一致",response);
            return;
        }

        BankInfo bankInfo = new BankInfo();
        bankInfo.setLoginName(userName);
        bankInfo.setBankName(bankName);
        bankInfo.setBankHead(bankHead);
        bankInfo.setBankCredits(Double.parseDouble(credit));
        bankInfo.setBankType(bankType);

        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setLoginName(userName);
        accountInfo.setLoginPassword(newPassword);
        accountInfo.setAccountType(2);

        boolean res = accountInfoService.createAccount(accountInfo);
        if(!res)
        {
            ResultMsgBuilder.commonError(EnumMsgCode.REPEAT_ACCOUNT_ERROR,"账户已存在",response);
            return;
        }
        else
        {
            //保存图片
            File outputFile =  CommonUtil.transferToFile(file,userName);
            bankInfoService.createBank(bankInfo);
            ResultMsgBuilder.commonError(EnumMsgCode.SUCCESS,"开户成功",response);
            return;
        }
    }

    @RequestMapping("/transfer")
    public String transfer(){
        return "/cbank/transfer";
    }

    @RequestMapping("/check/count")
    public void checkCount(HttpServletRequest request, HttpServletResponse response){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String change = request.getParameter("change");//转出金额
        String type = request.getParameter("type");//交易类型，1成长币,2债券
        String content = request.getParameter("content");//交易备注
        String toAccount = request.getParameter("count");//对方账号

        //检查账户是否存在
        boolean isAccount = accountInfoService.queryInfo(toAccount);
        if (!isAccount)
        {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"账户不存在",response);
            return;
        }

        //检查账户是否为商业银行
        BankInfo bankInfo = bankInfoService.queryInfo(toAccount);
        if (bankInfo == null)
        {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"该账户不是商业银行",response);
            return;
        }

        //如果转出金额大于余额
        boolean isSuccess = cbankInfoService.checkAmount(user.getName(), Double.parseDouble(change), type);
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
        String content = request.getParameter("content");//交易备注
        String toAccount = request.getParameter("count");//对方账号

        //如果转出金额大于余额
        boolean isSuccess = cbankInfoService.checkAmount(user.getName(), Double.parseDouble(change), type);
        if(isSuccess){
            cbankInfoService.transfer(user.getName(), Double.parseDouble(change), type, toAccount, content);
            ResultMsgBuilder.success(new HashMap<>(),response);
        }else {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"余额不足",response);
        }
    }

    @RequestMapping("/transfer/log")
    public String transferLog(){
        return "/cbank/transfer-log-coin";
    }

    @RequestMapping("/query/transfer/log")
    public void queryTransferLog(HttpServletRequest request, HttpServletResponse response){
        List<TranInfo> list = tranInfoService.query(request);
        int totalCount = tranInfoService.count(request);//数据总数
        AllPage  allPage = new AllPage();
        allPage.setPageCount(totalCount);
        allPage.setTotal(list);
        ResultMsgBuilder.success(allPage, response);
    }

    @RequestMapping("/credit/bank")
    public ModelAndView creditBank(){
        ModelAndView modelAndView = new ModelAndView("/cbank/credit-bank");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CbankInfo cbankInfo = cbankInfoService.queryInfo(user.getName());
        modelAndView.addObject("credit", String.valueOf(cbankInfo.getInitCredits()));
        return modelAndView;
    }

    @RequestMapping("/credit/user")
    public ModelAndView creditUser(){

        ModelAndView modelAndView = new ModelAndView("/cbank/credit-people");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CbankInfo cbankInfo = cbankInfoService.queryInfo(user.getName());
        modelAndView.addObject("credit", String.valueOf(cbankInfo.getInitCredits()));

        return modelAndView;
    }

    @RequestMapping("/credit/modify")
    public void credit(HttpServletRequest request, HttpServletResponse response){
        String initCredit = request.getParameter("credit");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isSet = cbankInfoService.setCredit(user.getName(), initCredit);
        if(isSet){
            ResultMsgBuilder.commonError(EnumMsgCode.SUCCESS,"设置成功",response);
        }else {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"设置失败",response);
        }
    }
    @RequestMapping("/credit/all/user")
    public void allPeopleCredit(HttpServletRequest request, HttpServletResponse response){
        String account = request.getParameter("account");//账户
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));

        List<UserInfo> list = userInfoService.queryInfoList(account, page, limit);
        int totalCount = userInfoService.countInfoList(account, page, limit);//数据总数
        AllPage allPage = new AllPage();
        allPage.setPageCount(totalCount);
        allPage.setTotalPeople(list);
        ResultMsgBuilder.success(allPage, response);
    }

    @RequestMapping("/credit/all/bank")
    public void allBankCredit(HttpServletRequest request, HttpServletResponse response){
        String account = request.getParameter("account");//账户
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));

        List<BankInfo> list = bankInfoService.queryInfoList(account, page, limit);
        int totalCount = bankInfoService.countInfoList(account, page, limit);//数据总数
        AllPage allPage = new AllPage();
        allPage.setPageCount(totalCount);
        allPage.setTotalBank(list);
        ResultMsgBuilder.success(allPage, response);
    }

    @RequestMapping("/credit/edit/user")
    public void editPeopleCredit(HttpServletRequest request, HttpServletResponse response){
        String credit = request.getParameter("credit");//新的信用分
        String account = request.getParameter("account");//账户号
        userInfoService.modifyCredit(account, credit);
    }

    @RequestMapping("/credit/edit/bank")
    public void editBankCredit(HttpServletRequest request, HttpServletResponse response){
        String credit = request.getParameter("credit");//新的信用分
        String account = request.getParameter("account");//账户号
        bankInfoService.modifyCredit(account, credit);
    }
}
