package com.keydak.urlmonitor.business;

import com.keydak.utils.expression.Condition;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/7/10.
 */
public interface ISearchSource {
    /**
     * 获取资源名
     * @return
     */
    String getName();

    /**
     * 获取总数
     * @return
     */
    Integer getTotal();

    /**
     * 获取总数
     * @return
     */
    Integer getTotal(Map<String, Condition> conditions);


    /**
     * 搜索返回列表
     * @param expression 条件表达式
     * @return
     */
    <T> List<T> Search(String expression);

    /**
     * 搜索返回列表
     * @param conditions 条件列表
     * @return
     */
    List Search(Map<String, Condition> conditions);

    /**
     * 分组
     * @param groupBy
     */
    void setGroupBy(String groupBy);

    /**
     * 排序
     * @param orderBy
     */
    void setOrderBy(String orderBy);

    /**
     * 分页
     * @param pageOption
     */
    void setPageOption(PageOption pageOption);
}
