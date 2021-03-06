package com.example.mockband.mapper;

import com.example.mockband.entity.BankInfo;
import com.example.mockband.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    int updateByLoginName(UserInfo record);

    UserInfo selectByLoginName(String loginName);

    List<UserInfo> selectPeopleCredit(HashMap<String, Object> hashMap);

    int selectPeopleCreditCount(HashMap<String, Object> hashMap);

    List<UserInfo> selectCustomer(HashMap<String, Object> hashMap);

    int CountCustomer(HashMap<String, Object> hashMap);

    int deleteByLoginName(String loginName);
}