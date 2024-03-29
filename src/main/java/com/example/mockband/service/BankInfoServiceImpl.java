package com.example.mockband.service;

import com.example.mockband.entity.*;
import com.example.mockband.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class BankInfoServiceImpl implements BankInfoService{
    @Autowired
    BankInfoMapper bankInfoMapper;

    @Autowired
    CbankInfoMapper cbankInfoMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    TranInfoMapper tranInfoMapper;

    @Autowired
    AccountInfoMapper accountInfoMapper;

    @Override
    public boolean createBank(BankInfo bankInfo) {
        if (bankInfoMapper.selectByLoginName(bankInfo.getBankName()) != null)
        {
            return false;
        }
        bankInfoMapper.insert(bankInfo);
        return true;
    }

    @Override
    public BankInfo queryInfo(String loginName) {
        return bankInfoMapper.selectByLoginName(loginName);
    }

    @Override
    public void modifyInfo(String loginName, String bankName, String bankHead, String bankType) {
        BankInfo bankInfo = bankInfoMapper.selectByLoginName(loginName);
        bankInfo.setBankName(bankName);
        bankInfo.setBankHead(bankHead);
        bankInfo.setBankType(bankType);
        bankInfoMapper.updateByLoginName(bankInfo);
    }

    @Override
    public boolean modifyCredit(String loginName, String credit) {
        BankInfo bankInfo = bankInfoMapper.selectByLoginName(loginName);
        if (bankInfo == null)
        {
            return false;
        }
        bankInfo.setBankCredits(Double.parseDouble(credit));
        bankInfoMapper.updateByLoginName(bankInfo);
        return true;
    }

    @Override
    public List<BankInfo> queryInfoList(String loginName, int page, int limit) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("loginName", loginName);

        int startIndex = (page - 1) * limit;
        hashMap.put("startIndex", startIndex);
        hashMap.put("pageSize", limit);

        return bankInfoMapper.selectBankCredit(hashMap);
    }

    @Override
    public int countInfoList(String loginName, int page, int limit) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("loginName", loginName);
        return bankInfoMapper.selectBankCreditCount(hashMap);
    }

    @Override
    public boolean deleteCustomer(String loginName) {
        UserInfo userInfo = userInfoMapper.selectByLoginName(loginName);
        AccountInfo accountInfo = accountInfoMapper.selectByLoginName(loginName);
        if (userInfo == null || accountInfo == null)
        {
            return false;
        }
        userInfoMapper.deleteByLoginName(loginName);
        accountInfoMapper.deleteByLoginName(loginName);
        return true;
    }

    @Override
    public boolean checkAmount(String loginName, double tranAmount, String curType) {
        BankInfo bankInfo = bankInfoMapper.selectByLoginName(loginName);

        //1成长币
        if (curType.equals("1"))
        {
            if (tranAmount > bankInfo.getBankGrowingCoin())
            {
                return false;
            }
        }

        //2债券
        if (curType.equals("2"))
        {
            if (tranAmount > bankInfo.getBankBond())
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public void transfer(String loginName, double tranAmount, String curType, String target, String toAccount, String remark) {

        BankInfo fromBankInfo = bankInfoMapper.selectByLoginName(loginName);
        TranInfo tranInfo = new TranInfo();
        //商业银行 -> 央行
        if (target.equals("1"))
        {
            CbankInfo cbankInfo = cbankInfoMapper.selectByLoginName(toAccount);
            //2. 商业银行 -> 央行
            tranInfo.setTranType(2);
            //1成长币
            if (curType.equals("1"))
            {
                fromBankInfo.setBankGrowingCoin(fromBankInfo.getBankGrowingCoin() - tranAmount);
                cbankInfo.setCbankGrowingCoin(cbankInfo.getCbankGrowingCoin() + tranAmount);
            }

            //2债券
            if (curType.equals("2"))
            {
                fromBankInfo.setBankBond(fromBankInfo.getBankBond() - tranAmount);
                cbankInfo.setCbankBond(cbankInfo.getCbankBond() + tranAmount);
            }
            bankInfoMapper.updateByLoginName(fromBankInfo);
            cbankInfoMapper.updateByLoginName(cbankInfo);
        }

        //商业银行 -> 个人
        if (target.equals("3"))
        {
            UserInfo userInfo = userInfoMapper.selectByLoginName(toAccount);
            //3. 商业银行 -> 个人
            tranInfo.setTranType(3);
            //1成长币
            if (curType.equals("1"))
            {
                fromBankInfo.setBankGrowingCoin(fromBankInfo.getBankGrowingCoin() - tranAmount);
                userInfo.setUserGrowingCoin(userInfo.getUserGrowingCoin() + tranAmount);
            }

            //2债券
            if (curType.equals("2"))
            {
                fromBankInfo.setBankBond(fromBankInfo.getBankBond() - tranAmount);
                userInfo.setUserBond(userInfo.getUserBond() + tranAmount);
            }
            bankInfoMapper.updateByLoginName(fromBankInfo);
            userInfoMapper.updateByLoginName(userInfo);
        }

        tranInfo.setFromAccount(fromBankInfo.getLoginName());
        tranInfo.setToAccount(toAccount);
        tranInfo.setCurrencyType(Integer.parseInt(curType));
        tranInfo.setTranAmount(tranAmount);
        tranInfo.setTranTime(new Date());
        tranInfo.setRemark(remark);
        tranInfoMapper.insert(tranInfo);
    }
}
