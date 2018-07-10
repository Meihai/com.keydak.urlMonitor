package com.keydak.urlmonitor.business;

import com.keydak.urlmonitor.constants.SpringCon;
import com.keydak.urlmonitor.db.*;
import com.keydak.utils.expression.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/7/10.
 */
public class UrlConverterSearchSource implements ISearchSource{
    @Autowired
    @Qualifier(SpringCon.URLCONVERTERDAO)
    private UrlConverterDao urlConverterDao;

    private String name;

    private String groupBy;

    private String orderBy;

    private PageOption pageOption=new PageOption(1,10);

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Integer getTotal() {
        List<com.keydak.urlmonitor.db.UrlConverter> list=urlConverterDao.getAll(null,null,null);
        return list.size();
    }

    @Override
    public Integer getTotal(Map<String, Condition> conditions) {
        List<com.keydak.urlmonitor.db.UrlConverter> list=urlConverterDao.getAll(conditions,null,null);
        return list.size();
    }

    @Override
    public <T> List<T> Search(String expression) {
        //todo
        return null;
    }

    @Override
    public List Search(Map<String, Condition> conditions) {
        Integer start = pageOption.getPage();
        Integer size = pageOption.getPageSize();
        List<com.keydak.urlmonitor.db.UrlConverter> list=urlConverterDao.getAll(conditions,start,size);
        return list;
    }

    @Override
    public void setGroupBy(String groupBy) {
        this.groupBy=groupBy;
    }

    @Override
    public void setOrderBy(String orderBy) {
        this.orderBy=orderBy;
    }

    @Override
    public void setPageOption(PageOption pageOption) {
        this.pageOption=pageOption;
    }

}
