package com.example.mockband.service;

import com.example.mockband.entity.BankInfo;
import com.example.mockband.entity.UserInfo;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserInfoService {

    UserInfo queryInfo(String loginName);

    void modifyInfo(String loginName, String mobile, String department);

    boolean checkAmount(String loginName, double tranAmount, String curType);

    void transfer(String loginName, double tranAmount, String curType, String target, String toAccount, String remark);

    boolean createUser(UserInfo userInfo);

    boolean modifyCredit(String loginName, String credit, String field);

    List<UserInfo> queryInfoList(String loginName, int page, int limit);

    int countInfoList(String loginName, int page, int limit);

    List<UserInfo> queryCustomer(String bankName, int page, int limit);

    int countCustomer(String bankName);
}
