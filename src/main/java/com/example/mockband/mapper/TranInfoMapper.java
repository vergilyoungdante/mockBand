package com.example.mockband.mapper;

import com.example.mockband.entity.TranInfo;

public interface TranInfoMapper {
    int deleteByPrimaryKey(Integer tranId);

    int insert(TranInfo record);

    int insertSelective(TranInfo record);

    TranInfo selectByPrimaryKey(Integer tranId);

    int updateByPrimaryKeySelective(TranInfo record);

    int updateByPrimaryKey(TranInfo record);
}