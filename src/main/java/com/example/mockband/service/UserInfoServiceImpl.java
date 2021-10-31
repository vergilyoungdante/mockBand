package com.example.mockband.service;

import com.example.mockband.mapper.UserInfoMapper;
import com.example.mockband.repository.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public int countUser() {

        return userInfoMapper.count();
    }
}
