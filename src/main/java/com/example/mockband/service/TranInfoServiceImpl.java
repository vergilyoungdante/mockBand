package com.example.mockband.service;

import com.example.mockband.entity.TranInfo;
import com.example.mockband.mapper.TranInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));

        String fromDate = "";
        String toDate = "";
        if(date != null && !date.equals(""))
        {
            String[] dateArr = date.split(" - ");
            fromDate = dateArr[0];
            toDate = dateArr[1];
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date1 = dateFormat.parse(toDate);
                Calendar c = Calendar.getInstance();
                c.setTime(date1);
                c.add(Calendar.DAY_OF_MONTH, 1);
                toDate = dateFormat.format(c.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fromAccount", fromAccount);
        hashMap.put("toAccount", toAccount);
        hashMap.put("curType", curType);
        hashMap.put("fromDate", fromDate);
        hashMap.put("toDate", toDate);

        int startIndex = (page - 1) * limit;
        hashMap.put("startIndex", startIndex);
        hashMap.put("pageSize", limit);

        return tranInfoMapper.selectSelective(hashMap);
    }

    @Override
    public List<TranInfo> export(HttpServletRequest request) {

        String fromAccount = request.getParameter("fromAccount");
        String toAccount = request.getParameter("toAccount");
        String curType = request.getParameter("type");
        String date = request.getParameter("date");//date:2021-11-13 - 2021-12-13  split(" - ")

        String fromDate = "";
        String toDate = "";
        if(date != null && !date.equals(""))
        {
            String[] dateArr = date.split(" - ");
            fromDate = dateArr[0];
            toDate = dateArr[1];
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date1 = dateFormat.parse(toDate);
                Calendar c = Calendar.getInstance();
                c.setTime(date1);
                c.add(Calendar.DAY_OF_MONTH, 1);
                toDate = dateFormat.format(c.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fromAccount", fromAccount);
        hashMap.put("toAccount", toAccount);
        hashMap.put("curType", curType);
        hashMap.put("fromDate", fromDate);
        hashMap.put("toDate", toDate);

        return tranInfoMapper.exportSelective(hashMap);
    }

    @Override
    public int count(HttpServletRequest request) {
        String fromAccount = request.getParameter("fromAccount");
        String toAccount = request.getParameter("toAccount");
        String curType = request.getParameter("type");
        String date = request.getParameter("date");//date:2021-11-13 - 2021-12-13  split(" - ")

        String fromDate = "";
        String toDate = "";
        if(date != null && !date.equals(""))
        {
            String[] dateArr = date.split(" - ");
            fromDate = dateArr[0];
            toDate = dateArr[1];
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date1 = dateFormat.parse(toDate);
                Calendar c = Calendar.getInstance();
                c.setTime(date1);
                c.add(Calendar.DAY_OF_MONTH, 1);
                toDate = dateFormat.format(c.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fromAccount", fromAccount);
        hashMap.put("toAccount", toAccount);
        hashMap.put("curType", curType);
        hashMap.put("fromDate", fromDate);
        hashMap.put("toDate", toDate);

        return tranInfoMapper.selectSelectiveCount(hashMap);
    }

    @Override
    public List<TranInfo> queryPeople(HttpServletRequest request, String loginName) {
        String fromAccount = request.getParameter("fromAccount");
        String toAccount = request.getParameter("toAccount");
        String curType = request.getParameter("type");
        String date = request.getParameter("date");//date:2021-11-13 - 2021-12-13  split(" - ")
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));

        String fromDate = "";
        String toDate = "";
        if(date != null && !date.equals(""))
        {
            String[] dateArr = date.split(" - ");
            fromDate = dateArr[0];
            toDate = dateArr[1];
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date1 = dateFormat.parse(toDate);
                Calendar c = Calendar.getInstance();
                c.setTime(date1);
                c.add(Calendar.DAY_OF_MONTH, 1);
                toDate = dateFormat.format(c.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fromAccount", fromAccount);
        hashMap.put("toAccount", toAccount);
        hashMap.put("curType", curType);
        hashMap.put("fromDate", fromDate);
        hashMap.put("toDate", toDate);
        hashMap.put("loginName", loginName);

        int startIndex = (page - 1) * limit;
        hashMap.put("startIndex", startIndex);
        hashMap.put("pageSize", limit);

        return tranInfoMapper.selectPeople(hashMap);
    }

    @Override
    public int countPeople(HttpServletRequest request, String loginName) {
        String fromAccount = request.getParameter("fromAccount");
        String toAccount = request.getParameter("toAccount");
        String curType = request.getParameter("type");
        String date = request.getParameter("date");//date:2021-11-13 - 2021-12-13  split(" - ")

        String fromDate = "";
        String toDate = "";
        if(date != null && !date.equals(""))
        {
            String[] dateArr = date.split(" - ");
            fromDate = dateArr[0];
            toDate = dateArr[1];
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date1 = dateFormat.parse(toDate);
                Calendar c = Calendar.getInstance();
                c.setTime(date1);
                c.add(Calendar.DAY_OF_MONTH, 1);
                toDate = dateFormat.format(c.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fromAccount", fromAccount);
        hashMap.put("toAccount", toAccount);
        hashMap.put("curType", curType);
        hashMap.put("fromDate", fromDate);
        hashMap.put("toDate", toDate);
        hashMap.put("loginName", loginName);

        return tranInfoMapper.selectPeopleCount(hashMap);
    }

    @Override
    public List<TranInfo> queryBank(HttpServletRequest request, String loginName) {
        String fromAccount = request.getParameter("fromAccount");
        String toAccount = request.getParameter("toAccount");
        String curType = request.getParameter("type");
        String date = request.getParameter("date");//date:2021-11-13 - 2021-12-13  split(" - ")
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));

        String fromDate = "";
        String toDate = "";
        if(date != null && !date.equals(""))
        {
            String[] dateArr = date.split(" - ");
            fromDate = dateArr[0];
            toDate = dateArr[1];
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date1 = dateFormat.parse(toDate);
                Calendar c = Calendar.getInstance();
                c.setTime(date1);
                c.add(Calendar.DAY_OF_MONTH, 1);
                toDate = dateFormat.format(c.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fromAccount", fromAccount);
        hashMap.put("toAccount", toAccount);
        hashMap.put("curType", curType);
        hashMap.put("fromDate", fromDate);
        hashMap.put("toDate", toDate);
        hashMap.put("loginName", loginName);

        int startIndex = (page - 1) * limit;
        hashMap.put("startIndex", startIndex);
        hashMap.put("pageSize", limit);

        return tranInfoMapper.selectBank(hashMap);
    }

    @Override
    public int countBank(HttpServletRequest request, String loginName) {
        String fromAccount = request.getParameter("fromAccount");
        String toAccount = request.getParameter("toAccount");
        String curType = request.getParameter("type");
        String date = request.getParameter("date");//date:2021-11-13 - 2021-12-13  split(" - ")

        String fromDate = "";
        String toDate = "";
        if(date != null && !date.equals(""))
        {
            String[] dateArr = date.split(" - ");
            fromDate = dateArr[0];
            toDate = dateArr[1];
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date1 = dateFormat.parse(toDate);
                Calendar c = Calendar.getInstance();
                c.setTime(date1);
                c.add(Calendar.DAY_OF_MONTH, 1);
                toDate = dateFormat.format(c.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fromAccount", fromAccount);
        hashMap.put("toAccount", toAccount);
        hashMap.put("curType", curType);
        hashMap.put("fromDate", fromDate);
        hashMap.put("toDate", toDate);
        hashMap.put("loginName", loginName);

        return tranInfoMapper.selectBankCount(hashMap);
    }
}
