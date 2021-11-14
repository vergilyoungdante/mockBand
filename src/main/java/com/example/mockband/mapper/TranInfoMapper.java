package com.example.mockband.mapper;

import com.example.mockband.entity.TranInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface TranInfoMapper {
    int deleteByPrimaryKey(Integer tranId);

    int insert(TranInfo record);

    int insertSelective(TranInfo record);

    TranInfo selectByPrimaryKey(Integer tranId);

    int updateByPrimaryKeySelective(TranInfo record);

    int updateByPrimaryKey(TranInfo record);

    List<TranInfo> selectSelective(HashMap<String, Object> hashMap);

    int selectSelectiveCount(HashMap<String, Object> hashMap);

    List<TranInfo> selectPeople(HashMap<String, Object> hashMap);

    int selectPeopleCount(HashMap<String, Object> hashMap);
}