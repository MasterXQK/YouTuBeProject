package com.Morgan.bilibili.domain;

import java.util.List;

/**
 * @author Morgan
 * @create 2022-12-04-21:03
 */
public class PageResult<T> {
    private Integer total;

    private List<T> list;

    public PageResult(Integer total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    // get set


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
