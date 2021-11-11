package com.example.mockband.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.mockband.entity.AccountInfo;
import com.example.mockband.entity.BankInfo;
import com.example.mockband.entity.CbankInfo;
import com.example.mockband.entity.User;
import com.example.mockband.service.AccountInfoService;
import com.example.mockband.service.BankInfoService;
import com.example.mockband.service.CbankInfoService;
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

    @RequestMapping("/total")
    public ModelAndView totalCount(){
        ModelAndView modelAndView = new ModelAndView("/cbank/total-count");
        //TODO:获得所有中央银行数据
        //需要所有3个数据
        //我这里把数据加进去给前端用
        //modelAndView.addObject();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CbankInfo cbankInfo = cbankInfoService.queryInfo(user.getName());
        modelAndView.addObject("totalCoin", cbankInfo.getTotalGrowingCoin());
        modelAndView.addObject("publicCoin", cbankInfo.getPublicGrowingCoin());
        modelAndView.addObject("cbankCoin",cbankInfo.getCbankGrowingCoin());
        modelAndView.addObject("totalBond", cbankInfo.getTotalBond());
        modelAndView.addObject("publicBond", cbankInfo.getPublicBond());
        modelAndView.addObject("cbankBond",cbankInfo.getCbankBond());
        return modelAndView;
    }

    @RequestMapping("/change/bond")
    public boolean changeBond(@RequestBody Map<String,Object> param,HttpServletRequest request, HttpServletResponse response){
        String change = param.get("change").toString();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //todo:如果销毁金额大于现有金额，该如何返回
        boolean isSuccess = cbankInfoService.changeBond(user.getName(), Double.parseDouble(change));
        return isSuccess;
    }
    @PostMapping("/change/coin")
    public boolean changeCoin(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response){
        String change = param.get("change").toString();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //todo:如果销毁金额大于现有金额，该如何返回
        boolean isSuccess = cbankInfoService.changeCoin(user.getName(), Double.parseDouble(change));
        return isSuccess;
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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",200);

        if(file.isEmpty()){
            jsonObject.put("message","需要上传执照");
            response.getWriter().write(jsonObject.toString());
            return;
        }
        if(!newPassword.equals(againPassword)){
            jsonObject.put("message","密码设置不一致");
            response.getWriter().write(jsonObject.toString());
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
        bankInfoService.createBank(bankInfo);
        accountInfoService.createAccount(accountInfo);
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
        String toBank = request.getParameter("toBank");//对方银行账号

        cbankInfoService.transfer(user.getName(), Double.parseDouble(change), type, toBank);
    }



    @RequestMapping("/transfer/log")
    public String transferLog(){
        return "/cbank/transfer-log-coin";
    }

    @RequestMapping("/query/transfer/log")
    public void queryTransferLog(HttpServletRequest request, HttpServletResponse response){
        String query = request.getParameter("query");


    }

}
