package com.example.mockband.service;

import com.example.mockband.entity.TranInfo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public interface TranInfoService {

    List<TranInfo> query(HttpServletRequest request);

    int count(HttpServletRequest request);

    List<TranInfo> queryPeople(HttpServletRequest request);

    int countPeople(HttpServletRequest request);
}
