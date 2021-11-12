package com.example.mockband.service;

import com.example.mockband.entity.UserInfo;
import com.example.mockband.mapper.AccountInfoMapper;
import com.example.mockband.mapper.TranInfoMapper;
import com.example.mockband.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    AccountInfoMapper accountInfoMapper;

    @Override
    public UserInfo queryInfo(String loginName) {
        return userInfoMapper.selectByLoginName(loginName);
    }

    @Override
    public void modifyInfo(String loginName, String mobile, String department) {
        UserInfo userInfo = userInfoMapper.selectByLoginName(loginName);
        userInfo.setUserMobile(mobile);
        userInfo.setUserDepartment(department);
        userInfoMapper.updateByLoginName(userInfo);
    }

    @Override
    public boolean checkAmount(String loginName, double tranAmount, String curType) {
        return false;
    }
}
