package com.example.mockband.entity;

import java.io.Serializable;
import java.util.Date;

public class BankInfo implements Serializable {
    private Integer bankId;

    private String bankName;

    private String loginName;

    private String bankHead;

    private String bankType;

    private Double bankCredits;

    private Double bankGrowingCoin;

    private Double bankBond;

    private Date createTime;

    private Date modifyTime;

    private byte[] bankLicence;

    private static final long serialVersionUID = 1L;

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
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

    public String getBankHead() {
        return bankHead;
    }

    public void setBankHead(String bankHead) {
        this.bankHead = bankHead == null ? null : bankHead.trim();
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType == null ? null : bankType.trim();
    }

    public Double getBankCredits() {
        return bankCredits;
    }

    public void setBankCredits(Double bankCredits) {
        this.bankCredits = bankCredits;
    }

    public Double getBankGrowingCoin() {
        return bankGrowingCoin;
    }

    public void setBankGrowingCoin(Double bankGrowingCoin) {
        this.bankGrowingCoin = bankGrowingCoin;
    }

    public Double getBankBond() {
        return bankBond;
    }

    public void setBankBond(Double bankBond) {
        this.bankBond = bankBond;
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

    public byte[] getBankLicence() {
        return bankLicence;
    }

    public void setBankLicence(byte[] bankLicence) {
        this.bankLicence = bankLicence;
    }
}