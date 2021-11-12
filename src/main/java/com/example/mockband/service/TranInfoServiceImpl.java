package com.example.mockband.service;

import com.example.mockband.entity.TranInfo;
import com.example.mockband.mapper.TranInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TranInfoServiceImpl implements TranInfoService{
    @Autowired
    TranInfoMapper tranInfoMapper;

    @Override
    public TranInfo query(String fromAccount, String toAccount, int curType, Date fromDate, Date toDate) {
        TranInfo tranInfo = new TranInfo();
        tranInfo.setFromAccount(fromAccount);
        tranInfo.setToAccount(toAccount);
        tranInfo.setCurrencyType(curType);

        //todo：传入多个参数，起始时间和截止时间
        return tranInfoMapper.select(tranInfo);
    }
}
