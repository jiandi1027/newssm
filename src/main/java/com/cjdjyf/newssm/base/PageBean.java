package com.cjdjyf.newssm.base;

import java.util.List;

/**
 * @author : cjd
 * @description : easyui分页bean
 * @date : 2018/4/24 11:18
 */
public class PageBean<T> {
    /*总页数*/
    private Long total;
    /* 页数据*/
    private List<T> rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
