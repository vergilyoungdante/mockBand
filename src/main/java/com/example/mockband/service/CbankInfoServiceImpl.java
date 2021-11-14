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
        double sumTotal = cbankInfo.getTotalBond() + changeAmount;
        if (sumAmount < 0)
        {
            return false;
        }
        cbankInfo.setCbankBond(sumAmount);
        cbankInfo.setTotalBond(sumTotal);
        cbankInfoMapper.updateByLoginName(cbankInfo);

        return true;
    }

    @Override
    public boolean changeCoin(String loginName, double changeAmount) {
        CbankInfo cbankInfo = queryInfo(loginName);
        double sumAmount = cbankInfo.getCbankGrowingCoin() + changeAmount;
        double sumTotal = cbankInfo.getTotalGrowingCoin() + changeAmount;
        if (sumAmount < 0)
        {
            return false;
        }
        cbankInfo.setCbankGrowingCoin(sumAmount);
        cbankInfo.setTotalGrowingCoin(sumTotal);
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
    public void transfer(String loginName, double tranAmount, String curType, String toAccount, String remark) {
        CbankInfo cbankInfo = cbankInfoMapper.selectByLoginName(loginName);
        BankInfo bankInfo = bankInfoMapper.selectByLoginName(toAccount);

        //1成长币
        if (curType.equals("1"))
        {
            cbankInfo.setCbankGrowingCoin(cbankInfo.getCbankGrowingCoin() - tranAmount);
            bankInfo.setBankGrowingCoin(bankInfo.getBankGrowingCoin() + tranAmount);
            cbankInfo.setPublicGrowingCoin(cbankInfo.getPublicGrowingCoin() + tranAmount);
        }

        //2债券
        if (curType.equals("2"))
        {
            cbankInfo.setCbankBond(cbankInfo.getCbankBond() - tranAmount);
            bankInfo.setBankBond(bankInfo.getBankBond() + tranAmount);
            cbankInfo.setPublicBond(cbankInfo.getPublicBond() + tranAmount);
        }
        cbankInfoMapper.updateByLoginName(cbankInfo);
        bankInfoMapper.updateByLoginName(bankInfo);

        TranInfo tranInfo = new TranInfo();
        tranInfo.setFromAccount(cbankInfo.getLoginName());
        tranInfo.setToAccount(toAccount);
        tranInfo.setCurrencyType(Integer.parseInt(curType));
        tranInfo.setTranAmount(tranAmount);
        tranInfo.setTranTime(new Date());

        //共5种，分别用1，2，3，4，5表示：
        //1. 央行 -> 商业银行
        //2. 商业银行 -> 央行
        //3. 商业银行 -> 个人
        //4. 个人 -> 商业银行
        //5. 个人 -> 个人
        tranInfo.setTranType(1);
        tranInfo.setRemark(remark);
        tranInfoMapper.insert(tranInfo);
    }

    @Override
    public boolean setCredit(String loginName, String initCredit) {
        CbankInfo cbankInfo = cbankInfoMapper.selectByLoginName(loginName);
        cbankInfo.setInitCredits(Double.parseDouble(initCredit));
        cbankInfoMapper.updateByLoginName(cbankInfo);
        return false;
    }
}
