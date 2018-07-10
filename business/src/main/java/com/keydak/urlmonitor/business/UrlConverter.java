package com.keydak.urlmonitor.business;

import com.keydak.urlmonitor.db.UrlConverterDao;

import java.util.Date;

/**
 * Created by admin on 2018/7/10.
 */
public class UrlConverter {
    private String longUrl;
    private String shortUrl;
    private Date createTime=new Date();

    public boolean save(boolean isNew){
        com.keydak.urlmonitor.db.UrlConverter dbUrlConverter=toDbUrlConverter();
        UrlConverterDao urlConverterDao=new UrlConverterDao();
        if(isNew){
            return urlConverterDao.insertUrlConverter(dbUrlConverter);
        }else{
            return urlConverterDao.updateUrlConverter(dbUrlConverter);
        }
    }

    public boolean delete(){
        com.keydak.urlmonitor.db.UrlConverter dbUrlConverter=toDbUrlConverter();
        UrlConverterDao urlConverterDao=new UrlConverterDao();
        return urlConverterDao.deleteUrlConverter(dbUrlConverter);
    }

    public com.keydak.urlmonitor.db.UrlConverter toDbUrlConverter(){
        com.keydak.urlmonitor.db.UrlConverter dbUrlConverter=new com.keydak.urlmonitor.db.UrlConverter();
        dbUrlConverter.setCreateTime(this.createTime);
        dbUrlConverter.setLongUrl(this.longUrl);
        dbUrlConverter.setShortUrl(this.shortUrl);
        return dbUrlConverter;
    }

    public UrlConverter fromDbUrlConverter(com.keydak.urlmonitor.db.UrlConverter dbUrlConverter ){
         UrlConverter busUrlConverter=new UrlConverter();
         busUrlConverter.setLongUrl(dbUrlConverter.getLongUrl());
         busUrlConverter.setShortUrl(dbUrlConverter.getShortUrl());
         busUrlConverter.setCreateTime(dbUrlConverter.getCreateTime());
         return busUrlConverter;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
