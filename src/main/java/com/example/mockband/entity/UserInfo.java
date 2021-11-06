package com.example.mockband.entity;

import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable {
    private Integer userId;

    private String bankName;

    private String loginName;

    private String userName;

    private String userDepartment;

    private String userMobile;

    private Double userCredits;

    private Double userGrowingCoin;

    private Double userBond;

    private Date createTime;

    private Date modifyTime;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserDepartment() {
        return userDepartment;
    }

    public void setUserDepartment(String userDepartment) {
        this.userDepartment = userDepartment == null ? null : userDepartment.trim();
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile == null ? null : userMobile.trim();
    }

    public Double getUserCredits() {
        return userCredits;
    }

    public void setUserCredits(Double userCredits) {
        this.userCredits = userCredits;
    }

    public Double getUserGrowingCoin() {
        return userGrowingCoin;
    }

    public void setUserGrowingCoin(Double userGrowingCoin) {
        this.userGrowingCoin = userGrowingCoin;
    }

    public Double getUserBond() {
        return userBond;
    }

    public void setUserBond(Double userBond) {
        this.userBond = userBond;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}