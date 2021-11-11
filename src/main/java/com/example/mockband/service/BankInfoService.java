package com.example.mockband.service;

import com.example.mockband.entity.BankInfo;
import org.springframework.stereotype.Service;

@Service
public interface BankInfoService {
    boolean createBank(BankInfo bankInfo);
}
