package com.liukai.seanews.util;

import org.junit.Test;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    private static SimpleDateFormat sf = null;

    /**
     * 获取最新时间字符串
     * @return
     */
    public static String getCurrentDate() {
        Date date = new Date();
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(date);
    }

    public static String getTimeStampToDateTime(long time) {
        Date date = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(date);
    }

    public static long getTimeStamp() {
        return System.currentTimeMillis();
    }

    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    @Test
    public void testGetCurrentTime() throws ParseException {
        System.out.println(getCurrentDate());
        long res = getTimeStamp();
        System.out.println(res);
        String re = getTimeStampToDateTime(res);
        System.out.println(re + "--" + res);
        System.out.println(strToDate("2019-01-01"));
    }
}
