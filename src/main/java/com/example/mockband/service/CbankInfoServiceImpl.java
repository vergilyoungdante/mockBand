package com.example.mockband.service;

import com.example.mockband.entity.CbankInfo;
import com.example.mockband.mapper.CbankInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CbankInfoServiceImpl implements CbankInfoService{

    @Autowired
    CbankInfoMapper cbankInfoMapper;

    @Override
    public CbankInfo queryInfo(String loginName) {
        CbankInfo cbankInfo = cbankInfoMapper.selectByLoginName(loginName);
        return cbankInfo;
    }
}
