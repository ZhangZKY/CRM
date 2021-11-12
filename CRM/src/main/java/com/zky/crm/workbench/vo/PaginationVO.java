package com.zky.crm.workbench.vo;

import java.util.List;

public class PaginationVO<T> {
    private int total;
    private List<T> dataList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dateList) {
        this.dataList = dateList;
    }


    @Override
    public String toString() {
        return "Pagination{" +
                "total=" + total +
                ", dateList=" + dataList +
                '}';
    }
}
