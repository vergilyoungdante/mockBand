package com.example.mockband.service;

import com.example.mockband.entity.BankInfo;
import com.example.mockband.entity.UserInfo;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public interface UserInfoService {

    UserInfo queryInfo(String loginName);

    void modifyInfo(String loginName, String mobile, String department);

    boolean checkAmount(String loginName, double tranAmount, String curType);

    void transfer(String loginName, double tranAmount, String curType, String target, String toAccount, String remark);

    boolean createUser(UserInfo userInfo);
}
