package com.example.mockband.service;

import com.example.mockband.entity.AccountInfo;
import com.example.mockband.mapper.AccountInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountInfoServiceImpl implements AccountInfoService{
    @Autowired
    AccountInfoMapper accountInfoMapper;

    @Override
    public boolean createAccount(AccountInfo accountInfo) {

        if (accountInfoMapper.selectByLoginName(accountInfo.getLoginName()) != null)
        {
            return false;
        }
        accountInfoMapper.insert(accountInfo);
        return true;
    }
}
