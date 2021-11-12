package com.example.mockband.service;

import com.example.mockband.entity.UserInfo;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public interface UserInfoService {
    UserInfo queryInfo(String loginName);
    void modifyInfo(String mobile, String department);
}
