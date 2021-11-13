package com.example.mockband.service;

import com.example.mockband.entity.TranInfo;
import com.example.mockband.mapper.TranInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class TranInfoServiceImpl implements TranInfoService{
    @Autowired
    TranInfoMapper tranInfoMapper;

    @Override
    public List<TranInfo> query(HttpServletRequest request) {

        String fromAccount = request.getParameter("fromAccount");
        String toAccount = request.getParameter("toAccount");
        String curType = request.getParameter("type");
        String date = request.getParameter("date");//date:2021-11-13 - 2021-12-13  split(" - ")
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");

        String fromDate = "";
        String toDate = "";
        if(date != null)
        {
            String[] dateArr = date.split(" - ");
            fromDate = dateArr[0];
            toDate = dateArr[1];
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fromAccount", fromAccount);
        hashMap.put("toAccount", toAccount);
        hashMap.put("curType", curType);
        hashMap.put("fromDate", fromDate);
        hashMap.put("toDate", toDate);
        hashMap.put("page", page);
        hashMap.put("limit", limit);

        return tranInfoMapper.selectSelective(hashMap);
    }
}
