package com.example.mockband.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

public class TranInfo implements Serializable {
    private Integer tranId;

    @Excel(name ="转出账户")
    private String fromAccount;
    @Excel(name ="转入账户")
    private String toAccount;
    @Excel(name ="交易种类",replace = {"成长币_1","债券_2"})
    private Integer currencyType;
    @Excel(name ="交易金额")
    private Double tranAmount;
    @Excel(name ="交易时间")
    private Date tranTime;

    private Integer tranType;

    public Integer getTranType() {
        return tranType;
    }

    public void setTranType(Integer tranType) {
        this.tranType = tranType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Excel(name ="备注")
    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getTranId() {
        return tranId;
    }

    public void setTranId(Integer tranId) {
        this.tranId = tranId;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount == null ? null : fromAccount.trim();
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount == null ? null : toAccount.trim();
    }

    public Integer getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(Integer currencyType) {
        this.currencyType = currencyType;
    }

    public Double getTranAmount() {
        return tranAmount;
    }

    public void setTranAmount(Double tranAmount) {
        this.tranAmount = tranAmount;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }
}