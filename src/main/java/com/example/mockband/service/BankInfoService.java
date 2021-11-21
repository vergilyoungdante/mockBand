package com.example.mockband.service;

import com.example.mockband.entity.BankInfo;
import com.example.mockband.entity.CbankInfo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface BankInfoService {

    boolean createBank(BankInfo bankInfo);

    BankInfo queryInfo(String loginName);

    void modifyInfo(String loginName, String bankName, String bankHead, String bankType);

    boolean checkAmount(String loginName, double tranAmount, String curType);

    void transfer(String loginName, double tranAmount, String curType, String target, String toAccount, String remark);

    boolean modifyCredit(String loginName, String credit);

    List<BankInfo> queryInfoList(String loginName, int page, int limit);

    int countInfoList(String loginName, int page, int limit);

    boolean deleteCustomer(String loginName);
}
