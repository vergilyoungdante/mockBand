package com.example.mockband.service;

import com.example.mockband.entity.TranInfo;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface TranInfoService {
    TranInfo query(String fromAccount, String toAccount, int curType, Date fromDate, Date toDate);
}
