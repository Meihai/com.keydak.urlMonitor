package com.keydak.urlmonitor.services;

import java.util.List;

/**
 * Created by admin on 2018/7/10.
 */
public class UrlEncoder {
    private String  action;
    private List<String> longurls;

    public UrlEncoder() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<String> getLongurls() {
        return longurls;
    }

    public void setLongurls(List<String> longurls) {
        this.longurls = longurls;
    }
}
