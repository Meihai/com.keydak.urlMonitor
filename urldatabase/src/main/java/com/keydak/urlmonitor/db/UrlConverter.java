package com.keydak.urlmonitor.db;

import java.util.Date;

/**
 * Created by admin on 2018/7/10.
 */
public class UrlConverter {
    private Date createTime;
    private String longUrl; //具有唯一性
    private String shortUrl;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
