package com.example.mockband.mapper;

import com.example.mockband.entity.BankInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BankInfoMapper {
    int deleteByPrimaryKey(Integer bankId);

    int insert(BankInfo record);

    int insertSelective(BankInfo record);

    BankInfo selectByPrimaryKey(Integer bankId);

    int updateByPrimaryKeySelective(BankInfo record);

    int updateByPrimaryKeyWithBLOBs(BankInfo record);

    int updateByPrimaryKey(BankInfo record);

    BankInfo selectByLoginName(String loginName);
}