package com.example.mockband.entity;

import java.util.List;

public class AllPage {
    List<TranInfo> total;
    int pageCount;

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
