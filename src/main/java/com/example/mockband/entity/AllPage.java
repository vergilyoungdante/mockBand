package com.example.mockband.entity;

import java.util.List;

public class AllPage {
    List<TranInfo> total;
    int pageCount;

    List<BankInfo> totalBank;
    List<UserInfo> totalPeople;

    public List<BankInfo> getTotalBank() {
        return totalBank;
    }

    public void setTotalBank(List<BankInfo> totalBank) {
        this.totalBank = totalBank;
    }

    public List<UserInfo> getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(List<UserInfo> totalPeople) {
        this.totalPeople = totalPeople;
    }

    public List<TranInfo> getTotal() {
        return total;
    }

    public void setTotal(List<TranInfo> total) {
        this.total = total;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
