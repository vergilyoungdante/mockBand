package com.example.mockband.service;

import com.example.mockband.entity.TranInfo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public interface TranInfoService {

    List<TranInfo> query(HttpServletRequest request);

    List<TranInfo> export(HttpServletRequest request);

    int count(HttpServletRequest request);

    List<TranInfo> queryPeople(HttpServletRequest request, String loginName);

    int countPeople(HttpServletRequest request, String loginName);

    List<TranInfo> queryBank(HttpServletRequest request, String loginName);

    int countBank(HttpServletRequest request, String loginName);
}
