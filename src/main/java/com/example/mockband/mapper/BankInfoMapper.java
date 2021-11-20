package com.example.mockband.mapper;

import com.example.mockband.entity.BankInfo;
import com.example.mockband.entity.CbankInfo;
import com.example.mockband.entity.TranInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

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

    int updateByLoginName(BankInfo bankInfo);

    List<BankInfo> selectBankCredit(HashMap<String, Object> hashMap);

    int selectBankCreditCount(HashMap<String, Object> hashMap);

    int deleteByLoginName(String loginName);
}