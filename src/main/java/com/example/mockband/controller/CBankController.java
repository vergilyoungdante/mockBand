package com.example.mockband.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.example.mockband.entity.*;
import com.example.mockband.model.EnumMsgCode;
import com.example.mockband.model.ResultMsgBuilder;
import com.example.mockband.service.*;
import com.example.mockband.util.CommonUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
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
        //????????????????????????????????????
        boolean isSuccess = cbankInfoService.changeBond(user.getName(), Double.parseDouble(change));
        if(isSuccess){
            ResultMsgBuilder.success(new HashMap<>(),response);
        }else {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"????????????",response);
        }
    }
    @PostMapping("/change/coin")
    public void changeCoin(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response){
        String change = param.get("change").toString();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //????????????????????????????????????
        boolean isSuccess = cbankInfoService.changeCoin(user.getName(), Double.parseDouble(change));
        if(isSuccess){
            ResultMsgBuilder.success(new HashMap<>(),response);
        }else {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"????????????",response);
        }
    }

    @RequestMapping("/register")
    public ModelAndView toRegister(){
        return new ModelAndView("/cbank/register");
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
            ResultMsgBuilder.commonError(EnumMsgCode.NO_PHOTO_ERROR,"????????????????????????",response);
            return;
        }
        if(!newPassword.equals(againPassword)){
            ResultMsgBuilder.commonError(EnumMsgCode.PASSWORD_INCONSISTENT_ERROR,"???????????????",response);
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
            ResultMsgBuilder.commonError(EnumMsgCode.REPEAT_ACCOUNT_ERROR,"???????????????",response);
        }
        else
        {
            //????????????
            File outputFile =  CommonUtil.transferToFile(file,userName);
            bankInfoService.createBank(bankInfo);
            ResultMsgBuilder.success("????????????",response);
        }
    }

    @RequestMapping("/transfer")
    public String transfer(){
        return "/cbank/transfer";
    }

    @RequestMapping("/check/count")
    public void checkCount(HttpServletRequest request, HttpServletResponse response){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String change = request.getParameter("change");//????????????
        String type = request.getParameter("type");//???????????????1?????????,2??????
        String content = request.getParameter("content");//????????????
        String toAccount = request.getParameter("count");//????????????

        //????????????????????????
        boolean isAccount = accountInfoService.queryInfo(toAccount);
        if (!isAccount)
        {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"???????????????",response);
            return;
        }

        //??????????????????????????????0
        if (Double.parseDouble(change) < 0)
        {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"????????????????????????0",response);
            return;
        }

        //?????????????????????????????????
        BankInfo bankInfo = bankInfoService.queryInfo(toAccount);
        if (bankInfo == null)
        {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"???????????????????????????",response);
            return;
        }

        //??????????????????????????????
        boolean isSuccess = cbankInfoService.checkAmount(user.getName(), Double.parseDouble(change), type);
        if(isSuccess){
            ResultMsgBuilder.success(new HashMap<>(),response);
        }else {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"????????????",response);
        }
    }

    @RequestMapping("/commit/change")
    public void commitChange(HttpServletRequest request, HttpServletResponse response){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String change = request.getParameter("change");//????????????
        String type = request.getParameter("type");//???????????????1?????????,2??????
        String content = request.getParameter("content");//????????????
        String toAccount = request.getParameter("count");//????????????

        //??????????????????????????????
        boolean isSuccess = cbankInfoService.checkAmount(user.getName(), Double.parseDouble(change), type);
        if(isSuccess){
            cbankInfoService.transfer(user.getName(), Double.parseDouble(change), type, toAccount, content);
            ResultMsgBuilder.success(new HashMap<>(),response);
        }else {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"????????????",response);
        }
    }

    @RequestMapping("/transfer/log")
    public String transferLog(){
        return "/cbank/transfer-log-coin";
    }

    @RequestMapping("/query/transfer/log")
    public void queryTransferLog(HttpServletRequest request, HttpServletResponse response){
        List<TranInfo> list = tranInfoService.query(request);
        int totalCount = tranInfoService.count(request);//????????????
        AllPage  allPage = new AllPage();
        allPage.setPageCount(totalCount);
        allPage.setTotal(list);
        ResultMsgBuilder.success(allPage, response);
    }

    @RequestMapping("/export/transfer/log")
    public void exportTransferLog(HttpServletRequest request, HttpServletResponse response) throws IOException{
        List<TranInfo> list = tranInfoService.export(request);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("????????????","??????"),TranInfo.class,list);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

        String fileName = simpleDateFormat.format(new Date());
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition","attachment;filename=" + new String(("????????????-" + fileName + ".xls").getBytes(),"iso-8859-1"));

        ServletOutputStream servletOutputStream = response.getOutputStream();
        workbook.write(servletOutputStream);
        servletOutputStream.flush();
        servletOutputStream.close();
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
            ResultMsgBuilder.commonError(EnumMsgCode.SUCCESS,"????????????",response);
        }else {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"????????????",response);
        }
    }
    @RequestMapping("/credit/all/user")
    public void allPeopleCredit(HttpServletRequest request, HttpServletResponse response){
        String account = request.getParameter("account");//??????
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));

        List<UserInfo> list = userInfoService.queryInfoList(account, page, limit);
        int totalCount = userInfoService.countInfoList(account, page, limit);//????????????
        AllPage allPage = new AllPage();
        allPage.setPageCount(totalCount);
        allPage.setTotalPeople(list);
        ResultMsgBuilder.success(allPage, response);
    }

    @RequestMapping("/credit/all/bank")
    public void allBankCredit(HttpServletRequest request, HttpServletResponse response){
        String account = request.getParameter("account");//??????
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));

        List<BankInfo> list = bankInfoService.queryInfoList(account, page, limit);
        int totalCount = bankInfoService.countInfoList(account, page, limit);//????????????
        AllPage allPage = new AllPage();
        allPage.setPageCount(totalCount);
        allPage.setTotalBank(list);
        ResultMsgBuilder.success(allPage, response);
    }

    @RequestMapping("/credit/edit/user")
    public void editPeopleCredit(HttpServletRequest request, HttpServletResponse response){
        String credit = request.getParameter("credit");//???????????????
        String account = request.getParameter("account");//?????????
        boolean isModified = userInfoService.modifyCredit(account, credit);
        if (!isModified)
        {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR, "????????????????????????????????????", response);
            return;
        }
        ResultMsgBuilder.success("????????????", response);
    }

    @RequestMapping("/credit/edit/bank")
    public void editBankCredit(HttpServletRequest request, HttpServletResponse response){
        String credit = request.getParameter("credit");//???????????????
        String account = request.getParameter("account");//?????????
        boolean isModified = bankInfoService.modifyCredit(account, credit);
        if (!isModified)
        {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR, "????????????????????????????????????", response);
            return;
        }
        ResultMsgBuilder.success("????????????", response);
    }
}
