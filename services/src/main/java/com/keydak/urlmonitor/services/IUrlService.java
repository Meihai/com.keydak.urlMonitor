package com.keydak.urlmonitor.services;

/**
 * Created by admin on 2018/7/10.
 */
public interface IUrlService {
    Result longToShortUrl(UrlEncoder urlEncoder);

    String getLongUrl(String shortUrl);
}
