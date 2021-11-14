package com.example.mockband.service;

import com.example.mockband.entity.BankInfo;
import com.example.mockband.entity.CbankInfo;
import com.example.mockband.entity.TranInfo;
import com.example.mockband.entity.UserInfo;
import com.example.mockband.mapper.AccountInfoMapper;
import com.example.mockband.mapper.BankInfoMapper;
import com.example.mockband.mapper.TranInfoMapper;
import com.example.mockband.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    AccountInfoMapper accountInfoMapper;

    @Autowired
    BankInfoMapper bankInfoMapper;

    @Autowired
    TranInfoMapper tranInfoMapper;

    @Override
    public UserInfo queryInfo(String loginName) {
        return userInfoMapper.selectByLoginName(loginName);
    }

    @Override
    public void modifyInfo(String loginName, String mobile, String department) {
        UserInfo userInfo = userInfoMapper.selectByLoginName(loginName);
        userInfo.setUserMobile(mobile);
        userInfo.setUserDepartment(department);
        userInfoMapper.updateByLoginName(userInfo);
    }

    @Override
    public boolean checkAmount(String loginName, double tranAmount, String curType) {
        UserInfo userInfo = userInfoMapper.selectByLoginName(loginName);

        //1成长币
        if (curType.equals("1"))
        {
            if (tranAmount > userInfo.getUserGrowingCoin())
            {
                return false;
            }
        }

        //2债券
        if (curType.equals("2"))
        {
            if (tranAmount > userInfo.getUserBond())
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public void transfer(String loginName, double tranAmount, String curType, String target, String toAccount, String remark) {

        UserInfo fromUserInfo = userInfoMapper.selectByLoginName(loginName);
        TranInfo tranInfo = new TranInfo();
        //个人 -> 商业银行
        if (target.equals("2"))
        {
            BankInfo bankInfo = bankInfoMapper.selectByLoginName(toAccount);
            //4. 个人 -> 商业银行
            tranInfo.setTranType(4);
            //1成长币
            if (curType.equals("1"))
            {
                fromUserInfo.setUserGrowingCoin(fromUserInfo.getUserGrowingCoin() - tranAmount);
                bankInfo.setBankGrowingCoin(bankInfo.getBankGrowingCoin() + tranAmount);
            }

            //2债券
            if (curType.equals("2"))
            {
                fromUserInfo.setUserBond(fromUserInfo.getUserBond() - tranAmount);
                bankInfo.setBankBond(bankInfo.getBankBond() + tranAmount);
            }
            userInfoMapper.updateByLoginName(fromUserInfo);
            bankInfoMapper.updateByLoginName(bankInfo);
        }

        //个人 -> 个人
        if (target.equals("3"))
        {
            UserInfo toUserInfo = userInfoMapper.selectByLoginName(toAccount);
            //5. 个人 -> 个人
            tranInfo.setTranType(5);
            //1成长币
            if (curType.equals("1"))
            {
                fromUserInfo.setUserGrowingCoin(fromUserInfo.getUserGrowingCoin() - tranAmount);
                toUserInfo.setUserGrowingCoin(toUserInfo.getUserGrowingCoin() + tranAmount);
            }

            //2债券
            if (curType.equals("2"))
            {
                fromUserInfo.setUserBond(fromUserInfo.getUserBond() - tranAmount);
                toUserInfo.setUserBond(toUserInfo.getUserBond() + tranAmount);
            }
            userInfoMapper.updateByLoginName(fromUserInfo);
            userInfoMapper.updateByLoginName(toUserInfo);
        }


        tranInfo.setFromAccount(fromUserInfo.getLoginName());
        tranInfo.setToAccount(toAccount);
        tranInfo.setCurrencyType(Integer.parseInt(curType));
        tranInfo.setTranAmount(tranAmount);
        tranInfo.setTranTime(new Date());
        tranInfo.setRemark(remark);
        tranInfoMapper.insert(tranInfo);
    }

    @Override
    public boolean createUser(UserInfo userInfo) {
        if (userInfoMapper.selectByLoginName(userInfo.getLoginName()) != null)
        {
            return false;
        }
        userInfoMapper.insert(userInfo);
        return true;
    }
}
