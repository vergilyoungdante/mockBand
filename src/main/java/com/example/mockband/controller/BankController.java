package com.example.mockband.controller;

import com.example.mockband.entity.*;
import com.example.mockband.model.EnumMsgCode;
import com.example.mockband.model.ResultMsgBuilder;
import com.example.mockband.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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

    @Autowired
    TranInfoService tranInfoService;

    @RequestMapping("/info")
    public ModelAndView identity(){
        ModelAndView modelAndView = new ModelAndView("/bank/form-bank-info");
        //查询
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BankInfo bankInfo = bankInfoService.queryInfo(user.getName());
        modelAndView.addObject("bankName", bankInfo.getBankName());
        modelAndView.addObject("bankHead", bankInfo.getBankHead());
        modelAndView.addObject("bankType",bankInfo.getBankType());

        //设置并返回图片路径
        String imgPath = user.getName() + ".jpg";
        modelAndView.addObject("imgPath",imgPath);
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TranInfo> list = tranInfoService.queryBank(request, user.getName());
        int totalCount = tranInfoService.countBank(request, user.getName());//数据总数
        AllPage  allPage = new AllPage();
        allPage.setPageCount(totalCount);
        allPage.setTotal(list);
        ResultMsgBuilder.success(allPage, response);
    }

    @RequestMapping("/people")
    public ModelAndView toRegister(){
        return new ModelAndView("/bank/form-bank-create-people");
    }

    @RequestMapping("/create/people")
    public void createPeople(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam("userName") String userName,
                                  @RequestParam("newPassword") String newPassword,
                                  @RequestParam("againPassword") String againPassword,
                                  @RequestParam("phoneNumber") String phoneNumber,
                                  @RequestParam("loginName") String loginName) throws IOException {

//        if(file.isEmpty()){
//            jsonObject.put("message","需要上传执照");
//            response.getWriter().write(jsonObject.toString());
//            return;
//        }
        if(!newPassword.equals(againPassword)){
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"密码不一样",response);
            return;
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setLoginName(loginName);
        userInfo.setUserMobile(phoneNumber);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userInfo.setBankName(user.getName());

        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setLoginName(loginName);
        accountInfo.setLoginPassword(newPassword);
        accountInfo.setAccountType(3);

        boolean isExist = accountInfoService.createAccount(accountInfo);
        if (!isExist)
        {
            ResultMsgBuilder.commonError(EnumMsgCode.REPEAT_ACCOUNT_ERROR,"账户已存在",response);
            return;
        }
        userInfoService.createUser(userInfo);
        ResultMsgBuilder.success("开户成功",response);
    }

    @RequestMapping("/check/count")
    public void checkCount(HttpServletRequest request, HttpServletResponse response){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String change = request.getParameter("change");//转出金额
        String type = request.getParameter("type");//交易类型，1成长币,2债券
        String content = request.getParameter("content");//交易备注
        String toAccount = request.getParameter("account");//对方账号
        String target = request.getParameter("target");//交易类型，1央行，2商业银行，3个人

        //检查账户是否存在
        boolean isAccount = accountInfoService.queryInfo(toAccount);
        if (!isAccount)
        {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"账户不存在",response);
            return;
        }

        //检查转出金额是否小于0
        if (Double.parseDouble(change) < 0)
        {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"转出金额不能小于0",response);
            return;
        }

        //检查账户是否为央行或个人
        CbankInfo cbankInfo = cbankInfoService.queryInfo(toAccount);
        UserInfo userInfo = userInfoService.queryInfo(toAccount);
        if (cbankInfo == null && userInfo == null)
        {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"输入的账户不是央行或个人",response);
            return;
        }

        //检查账号和账户类型是否匹配
        if (target.equals("1") && cbankInfo == null)
        {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"输入的账户不是央行",response);
            return;
        }
        if (target.equals("3") && userInfo == null)
        {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"输入的账户不是个人",response);
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
            //手续费 5%，15元封顶
            if(target.equals("3")){
                double trans = Double.parseDouble(change);
                if(trans * 0.05>15){
                    change = String.valueOf(trans-15);
                }else {
                    change = String.valueOf(trans*0.95);
                }
            }

            bankInfoService.transfer(user.getName(), Double.parseDouble(change), type, target, toAccount, content);
            ResultMsgBuilder.success(new HashMap<>(),response);
        }else {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"余额不足",response);
        }
    }
    @RequestMapping("/manage")
    public ModelAndView manage(){
        return new ModelAndView("/bank/manage-user");
    }

    @RequestMapping("/query/customer")
    public void queryCustomer(HttpServletRequest request, HttpServletResponse response){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        List<UserInfo> list = userInfoService.queryCustomer(user.getName(), page, limit);
        int totalCount = userInfoService.countCustomer(user.getName());//数据总数
        AllPage allPage = new AllPage();
        allPage.setPageCount(totalCount);
        allPage.setTotalPeople(list);
        ResultMsgBuilder.success(allPage, response);
    }

    @RequestMapping("/delete/customer")
    public void deleteCustomer(HttpServletRequest request, HttpServletResponse response){
        String account = request.getParameter("account");//账户号
        boolean isDelete = bankInfoService.deleteCustomer(account);
        if (!isDelete)
        {
            ResultMsgBuilder.commonError(EnumMsgCode.UNKONWN_ERROR,"账户已被删除",response);
            return;
        }
        ResultMsgBuilder.success("删除成功", response);
    }
}
