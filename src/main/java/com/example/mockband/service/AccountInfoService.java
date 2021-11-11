package com.example.mockband.service;

import com.example.mockband.entity.AccountInfo;
import org.springframework.stereotype.Service;

@Service
public interface AccountInfoService {
    boolean createAccount(AccountInfo accountInfo);
}
