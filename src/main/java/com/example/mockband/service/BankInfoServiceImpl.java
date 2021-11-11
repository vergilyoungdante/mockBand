package com.example.mockband.service;

import com.example.mockband.entity.BankInfo;
import com.example.mockband.mapper.BankInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class BankInfoServiceImpl implements BankInfoService{
    @Autowired
    BankInfoMapper bankInfoMapper;

    @Override
    public boolean createBank(BankInfo bankInfo) {
        if (bankInfoMapper.selectByLoginName(bankInfo.getLoginName()) != null)
        {
            return false;
        }
        bankInfoMapper.insert(bankInfo);
        return true;
    }
}
