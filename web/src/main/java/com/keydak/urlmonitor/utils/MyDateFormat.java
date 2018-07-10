package com.keydak.urlmonitor.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.AttributedCharacterIterator;
import java.text.DateFormatSymbols;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * User: caisz
 * Date: 2017/8/3
 * Time: 11:43
 * Description:
 */
public class MyDateFormat extends SimpleDateFormat {

    private static final long serialVersionUID = 1L;
    private final static String pattern1 = "yyyy-MM-dd";
    private final static String pattern2 = "yyyy-MM-dd HH:mm:ss";
    // 年月日的格式化
    private static SimpleDateFormat ymd = new SimpleDateFormat(pattern1);
    // 年月日时分秒的格式化
    // 默认的格式化
    private static SimpleDateFormat ymdhms = new SimpleDateFormat(pattern2);

   /* public static void main(String[] args) throws Exception {
        String d1 = "2017-01-01";
        String d2 = "2017-01-01 12:12:12";
        ObjectMapper om = new ObjectMapper();
        String json0 = "{\"my09\":\"2017-01-01\",\"my091\":\"2017-01-01\"}";
        String json1 = "{\"my09\":\"2017-01-01\",\"my091\":\"2017-01-01 08:00:00\"}";
        String json2 = "{\"my09\":\"2017-01-01 08:00:00\",\"my091\":\"2017-01-01\"}";
        String json3 = "{\"my09\":\"2017-01-01 08:00:00\",\"my091\":\"2017-01-01 08:00:00\"}";
        om.setDateFormat(new MyDateFormat());
        Demo d = null;
        d = om.readValue(json0, Demo.class);
        System.out.println(om.writeValueAsString(d));
        d = om.readValue(json1, Demo.class);
        System.out.println(om.writeValueAsString(d));
        d = om.readValue(json2, Demo.class);
        System.out.println(om.writeValueAsString(d));
        d = om.readValue(json3, Demo.class);
        System.out.println(om.writeValueAsString(d));
    }*/

    // 默认的格式化
    public MyDateFormat() {
        this(pattern2);
    }

    public MyDateFormat(String pattern) {
        super(pattern);
    }

    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition pos) {
        if (date instanceof java.sql.Date) {
            return ymd.format(date, toAppendTo, pos);
        } else if (date instanceof java.sql.Timestamp) {
            return ymdhms.format(date, toAppendTo, pos);
        }
        // 可以自定义自己的数据格式
        return ymdhms.format(date, toAppendTo, pos);
    }

    @Override
    public void set2DigitYearStart(Date startDate) {
        System.out.println("set2DigitYearStart");
        super.set2DigitYearStart(startDate);
    }

    @Override
    public Date get2DigitYearStart() {
        System.out.println("get2DigitYearStart");
        return super.get2DigitYearStart();
    }

    @Override
    public AttributedCharacterIterator formatToCharacterIterator(Object obj) {
        System.out.println("formatToCharacterIterator");
        return super.formatToCharacterIterator(obj);
    }

    @Override
    public Date parse(String text, ParsePosition pos) {
        if (StringUtils.isNotEmpty(text)) {
            // 2017-01-01 12:13:14
            if (text.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                return ymdhms.parse(text, pos);
            } else if (text.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
                // 2017-01-01
                return ymd.parse(text, pos);
            } else if( Pattern.matches("\\d+", text) ) {
                return new Date(Long.valueOf(text));
            } else if( Pattern.matches("\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d+Z", text) ){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
                text = text.replace("Z", " UTC");
                return simpleDateFormat.parse(text, pos);
            }
            // 可以自定义自己的数据格式
        }

        return super.parse(text, pos);
    }

    @Override
    public String toPattern() {
        System.out.println("toPattern");
        return super.toPattern();
    }

    @Override
    public String toLocalizedPattern() {
        System.out.println("toLocalizedPattern");
        return super.toLocalizedPattern();
    }

    @Override
    public void applyPattern(String pattern) {
        System.out.println("applyPattern");
        super.applyPattern(pattern);
    }

    @Override
    public void applyLocalizedPattern(String pattern) {
        System.out.println("applyLocalizedPattern");
        super.applyLocalizedPattern(pattern);
    }

    @Override
    public DateFormatSymbols getDateFormatSymbols() {
        System.out.println("getDateFormatSymbols");
        return super.getDateFormatSymbols();
    }

    @Override
    public void setDateFormatSymbols(DateFormatSymbols newFormatSymbols) {
        System.out.println("setDateFormatSymbols");
        super.setDateFormatSymbols(newFormatSymbols);
    }

    @Override
    public Object clone() {
        return super.clone();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}

/*
class Demo implements Serializable {

    private static final long serialVersionUID = 1L;

    // @JsonFormat(pattern = "yyyy-MM-dd")
    private Date my09;
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp my091;

    public Date getMy09() {
        return my09;
    }

    public Timestamp getMy091() {
        return my091;
    }

    public void setMy091(Timestamp my091) {
        this.my091 = my091;
    }

    public void setMy09(Date my09) {
        this.my09 = my09;
    }
}*/
