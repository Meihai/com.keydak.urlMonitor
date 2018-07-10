package com.keydak.urlmonitor.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.keydak.utils.LogUtil;

import java.text.DateFormat;
import java.util.List;

/**
 * 接口返回对象
 * Created by Sammy Leung on 2017/7/26.
 */
public class Result implements IResult {

    public Result() {
        this.code = 0;
        this.message = "not init!";
        this.data = null;
    }

    public Result(int code, String message, Object data) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public String toJson() {
        Gson gson =  new GsonBuilder()
                .setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
        String jsonResult;

        try {
            jsonResult = gson.toJson(this);
        }catch (Exception e){
            jsonResult = "{\"code\":" + code + ",\"data\":{},\"message\":\"" + message + "\"}";
            e.printStackTrace();
            LogUtil.e("Result.toJson parse json error", e.getMessage());
        }

        return jsonResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result result = (Result) o;

        if (code != result.code) return false;
        if (data != null ? !data.equals(result.data) : result.data != null) return false;
        return message != null ? message.equals(result.message) : result.message == null;
    }

    @Override
    public int hashCode() {
        int result = data != null ? data.hashCode() : 0;
        result = 31 * result + code;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    private Object data;
    private int code;
    private String message;

    public static class ListResult<T>{

        public ListResult(List<T> list) {
            this.list = list;
        }

        public List<T> getList() {
            return list;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ListResult<?> that = (ListResult<?>) o;

            return list != null ? list.equals(that.list) : that.list == null;
        }

        @Override
        public int hashCode() {
            return list != null ? list.hashCode() : 0;
        }

        private List<T> list;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
