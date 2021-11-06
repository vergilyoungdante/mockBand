package com.example.mockband.mapper;

import com.example.mockband.entity.CbankInfo;

public interface CbankInfoMapper {
    int deleteByPrimaryKey(Integer cbankId);

    int insert(CbankInfo record);

    int insertSelective(CbankInfo record);

    CbankInfo selectByPrimaryKey(Integer cbankId);

    int updateByPrimaryKeySelective(CbankInfo record);

    int updateByPrimaryKey(CbankInfo record);
}