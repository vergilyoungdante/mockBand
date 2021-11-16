package com.example.mockband.service;

import com.example.mockband.entity.CbankInfo;
import org.springframework.stereotype.Service;

@Service
public interface CbankInfoService {

    CbankInfo queryInfo(String loginName);

    boolean changeBond(String loginName, double changeAmount);

    boolean changeCoin(String loginName, double changeAmount);

    boolean checkAmount(String loginName, double tranAmount, String curType);

    void transfer(String loginName, double tranAmount, String curType, String toAccount, String remark);

    boolean setCredit(String loginName, String initCredit);

}
