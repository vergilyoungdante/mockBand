package com.example.mockband.service;

import com.example.mockband.entity.CbankInfo;
import org.springframework.stereotype.Service;

@Service
public interface CbankInfoService {
    public CbankInfo queryInfo(String loginName);
    boolean changeBond(String loginName, double changeAmount);
    boolean changeCoin(String loginName, double changeAmount);
}
