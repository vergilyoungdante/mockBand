package com.example.mockband.service;

import com.example.mockband.entity.BankInfo;
import com.example.mockband.entity.UserInfo;
import com.example.mockband.mapper.BankInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankInfoServiceImpl implements BankInfoService{
    @Autowired
    BankInfoMapper bankInfoMapper;

    @Override
    public boolean createBank(BankInfo bankInfo) {
        if (bankInfoMapper.selectByLoginName(bankInfo.getBankName()) != null)
        {
            return false;
        }
        bankInfoMapper.insert(bankInfo);
        return true;
    }

    @Override
    public BankInfo queryInfo(String loginName) {
        return bankInfoMapper.selectByLoginName(loginName);
    }

    @Override
    public void modifyInfo(String loginName, String bankName, String bankHead, String bankType) {
        BankInfo bankInfo = bankInfoMapper.selectByLoginName(loginName);
        bankInfo.setBankName(bankName);
        bankInfo.setBankHead(bankHead);
        bankInfo.setBankType(bankType);
        bankInfoMapper.updateByLoginName(bankInfo);
    }
}
