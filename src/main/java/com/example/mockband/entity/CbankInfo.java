package com.example.mockband.entity;

import java.io.Serializable;
import java.util.Date;

public class CbankInfo implements Serializable {
    private Integer cbankId;

    private String loginName;

    private Double totalGrowingCoin;

    private Double cbankGrowingCoin;

    private Double publicGrowingCoin;

    private Double totalBond;

    private Double cbankBond;

    private Double publicBond;

    private Double initCredits;

    private Date createTime;

    private Date modifyTime;

    private static final long serialVersionUID = 1L;

    public Integer getCbankId() {
        return cbankId;
    }

    public void setCbankId(Integer cbankId) {
        this.cbankId = cbankId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public Double getTotalGrowingCoin() {
        return totalGrowingCoin;
    }

    public void setTotalGrowingCoin(Double totalGrowingCoin) {
        this.totalGrowingCoin = totalGrowingCoin;
    }

    public Double getCbankGrowingCoin() {
        return cbankGrowingCoin;
    }

    public void setCbankGrowingCoin(Double cbankGrowingCoin) {
        this.cbankGrowingCoin = cbankGrowingCoin;
    }

    public Double getPublicGrowingCoin() {
        return publicGrowingCoin;
    }

    public void setPublicGrowingCoin(Double publicGrowingCoin) {
        this.publicGrowingCoin = publicGrowingCoin;
    }

    public Double getTotalBond() {
        return totalBond;
    }

    public void setTotalBond(Double totalBond) {
        this.totalBond = totalBond;
    }

    public Double getCbankBond() {
        return cbankBond;
    }

    public void setCbankBond(Double cbankBond) {
        this.cbankBond = cbankBond;
    }

    public Double getPublicBond() {
        return publicBond;
    }

    public void setPublicBond(Double publicBond) {
        this.publicBond = publicBond;
    }

    public Double getInitCredits() {
        return initCredits;
    }

    public void setInitCredits(Double initCredits) {
        this.initCredits = initCredits;
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