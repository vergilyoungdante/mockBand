package com.example.mockband.service;

import com.example.mockband.entity.BankInfo;
import com.example.mockband.entity.CbankInfo;
import com.example.mockband.entity.TranInfo;
import com.example.mockband.mapper.AccountInfoMapper;
import com.example.mockband.mapper.BankInfoMapper;
import com.example.mockband.mapper.CbankInfoMapper;
import com.example.mockband.mapper.TranInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CbankInfoServiceImpl implements CbankInfoService{

    @Autowired
    CbankInfoMapper cbankInfoMapper;

    @Autowired
    BankInfoMapper bankInfoMapper;

    @Autowired
    AccountInfoMapper accountInfoMapper;

    @Autowired
    TranInfoMapper tranInfoMapper;

    @Override
    public CbankInfo queryInfo(String loginName) {
        CbankInfo cbankInfo = cbankInfoMapper.selectByLoginName(loginName);
        return cbankInfo;
    }

    @Override
    public boolean changeBond(String loginName, double changeAmount) {
        CbankInfo cbankInfo = queryInfo(loginName);
        double sumAmount = cbankInfo.getCbankBond() + changeAmount;
        if (sumAmount < 0)
        {
            return false;
        }
        cbankInfo.setCbankBond(sumAmount);
        cbankInfoMapper.updateByLoginName(cbankInfo);
        return true;
    }

    @Override
    public boolean changeCoin(String loginName, double changeAmount) {
        CbankInfo cbankInfo = queryInfo(loginName);
        double sumAmount = cbankInfo.getCbankGrowingCoin() + changeAmount;
        if (sumAmount < 0)
        {
            return false;
        }
        cbankInfo.setCbankGrowingCoin(sumAmount);
        cbankInfoMapper.updateByLoginName(cbankInfo);
        return true;
    }

    @Override
    public boolean checkAmount(String loginName, double tranAmount, String curType) {
        CbankInfo cbankInfo = cbankInfoMapper.selectByLoginName(loginName);

        //1成长币
        if (curType.equals("1"))
        {
            if (tranAmount > cbankInfo.getCbankGrowingCoin())
            {
                return false;
            }
        }

        //2债券
        if (curType.equals("2"))
        {
            if (tranAmount > cbankInfo.getCbankBond())
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public void transfer(String loginName, double tranAmount, String curType, String toAccount) {
        CbankInfo cbankInfo = cbankInfoMapper.selectByLoginName(loginName);
        BankInfo bankInfo = bankInfoMapper.selectByLoginName(toAccount);

        //1成长币
        if (curType.equals("1"))
        {
            cbankInfo.setCbankGrowingCoin(cbankInfo.getCbankGrowingCoin() - tranAmount);
            bankInfo.setBankGrowingCoin(bankInfo.getBankGrowingCoin() + tranAmount);
        }

        //2债券
        if (curType.equals("2"))
        {
            cbankInfo.setCbankBond(cbankInfo.getCbankBond() - tranAmount);
            bankInfo.setBankBond(bankInfo.getBankBond() + tranAmount);
        }
        cbankInfoMapper.updateByLoginName(cbankInfo);
        bankInfoMapper.updateByLoginName(bankInfo);

        TranInfo tranInfo = new TranInfo();
        tranInfo.setFromAccount(cbankInfo.getLoginName());
        tranInfo.setToAccount(toAccount);
        tranInfo.setCurrencyType(Integer.parseInt(curType));
        tranInfo.setTranAmount(tranAmount);
        tranInfo.setTranTime(new Date());
        tranInfoMapper.insert(tranInfo);
    }
}
