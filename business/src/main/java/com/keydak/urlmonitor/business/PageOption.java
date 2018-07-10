package com.keydak.urlmonitor.business;

/**
 * Created by admin on 2018/7/10.
 */
public class PageOption {
    private Integer page;

    private Integer pageSize;

    public PageOption() {
        super();
    }

    public PageOption(Integer page, Integer pageSize) {
        super();
        this.page = page;
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
