package com.keydak.urlmonitor.services;

/**
 *
 * Created by Sammy Leung on 2017/7/26.
 */
public interface IResult {

    int getCode();

    String getMessage();

    Object getData();

    String toJson();
}
