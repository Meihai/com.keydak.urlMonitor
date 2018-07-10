package com.keydak.urlmonitor.business;

/**
 * Created by Sammy Leung on 2018/5/4.
 */
public class BaseResult{

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    protected int code;
    protected String message;
}
