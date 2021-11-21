package com.example.mockband.mapper;

import com.example.mockband.entity.AccountInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountInfoMapper {
    int deleteByPrimaryKey(Integer accountId);

    int insert(AccountInfo record);

    int insertSelective(AccountInfo record);

    AccountInfo selectByPrimaryKey(Integer accountId);

    int updateByPrimaryKeySelective(AccountInfo record);

    int updateByPrimaryKey(AccountInfo record);

    AccountInfo selectByLoginName(String loginName);

    int updateByLoginName(AccountInfo record);

    int deleteByLoginName(String loginName);
}