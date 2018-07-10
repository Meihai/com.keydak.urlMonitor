package com.keydak.urlmonitor.services;

import java.util.Date;

/**
 * Created by admin on 2018/7/10.
 */
public class UrlConverter {
    private Date createTime;
    private String longUrl;
    private String shortUrl;

    public UrlConverter(){}

    public UrlConverter(String longUrl,String shortUrl,Date createTime){
        this.longUrl=longUrl;
        this.shortUrl=shortUrl;
        this.createTime=createTime;
    }

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

    public UrlConverter fromBusinessUrlConverter(com.keydak.urlmonitor.business.UrlConverter busUrlConverter){
        UrlConverter serviceUrlConverter=new UrlConverter();
        serviceUrlConverter.setCreateTime(busUrlConverter.getCreateTime());
        serviceUrlConverter.setLongUrl(busUrlConverter.getLongUrl());
        serviceUrlConverter.setShortUrl(busUrlConverter.getShortUrl());
        return serviceUrlConverter;
    }

    public com.keydak.urlmonitor.business.UrlConverter toBusinessUrlConverter(UrlConverter serviceConverter){
        com.keydak.urlmonitor.business.UrlConverter busUrlConverter=new com.keydak.urlmonitor.business.UrlConverter();
        if(serviceConverter==null){
            busUrlConverter.setCreateTime(this.getCreateTime());
            busUrlConverter.setLongUrl(this.getLongUrl());
            busUrlConverter.setShortUrl(this.getShortUrl());
        }else{
            busUrlConverter.setCreateTime(serviceConverter.getCreateTime());
            busUrlConverter.setLongUrl(serviceConverter.getLongUrl());
            busUrlConverter.setShortUrl(serviceConverter.getShortUrl());
        }
       return busUrlConverter;
    }
}
