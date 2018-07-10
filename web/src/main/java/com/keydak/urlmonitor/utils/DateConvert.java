package com.keydak.urlmonitor.utils;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * User: caisz
 * Date: 2017/7/28
 * Time: 14:43
 * Description:
 *
 *      全局日期处理类
 *      Convert<T,S>
 *         泛型T:代表客户端提交的参数 String
 *         泛型S:通过convert转换的类型

 */
public class DateConvert implements Converter<String, Date> {

    @Override
    public Date convert(String stringDate) {

        try {
            if( Pattern.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", stringDate) ) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                return simpleDateFormat.parse(stringDate);
            } else if( Pattern.matches("\\d+", stringDate) ) {
                return new Date(Long.valueOf(stringDate));
            } else if( Pattern.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+Z", stringDate) ){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
                stringDate = stringDate.replace("Z", " UTC");
                return simpleDateFormat.parse(stringDate);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

/*    public static void main(String[] args) {
        String stringDate = "1501730332";
        try {
            if( Pattern.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", stringDate) ) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                System.out.println(simpleDateFormat.parse(stringDate));
            } else if( Pattern.matches("\\d+", stringDate) ) {
                System.out.println(new Date(Long.valueOf(stringDate)));
            } else if( Pattern.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+Z", stringDate) ){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
                stringDate = stringDate.replace("Z", " UTC");
                System.out.println(simpleDateFormat.parse(stringDate));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }*/
}