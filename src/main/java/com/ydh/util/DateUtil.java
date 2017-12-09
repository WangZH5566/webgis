package com.ydh.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yqb on 2016/8/5 0005.
 */
public class DateUtil {
    private final static long ONE_MINUTE = 60 * 1000;       // 1分钟
    private final static long ONE_HOUR = 60 * ONE_MINUTE;   // 1小时
    private final static long ONE_DAY = 24 * ONE_HOUR;      // 1天
    private final static long ONE_MONTH = 31 * ONE_DAY;     // 月
    private final static long ONE_YEAR = 12 * ONE_MONTH;    // 年


    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "个月前";
    private static final String ONE_YEAR_AGO = "年前";


    private final static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat sdf_type_2=new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat sdf_type_YMDHM=new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static Date parseYYYYMMDDHHMMSS(String date) throws Exception{
        return sdf.parse(date);
    }

    public static String formatYYYYMMDDHHMMSS(Date date){
        return sdf.format(date);
    }

    public static String formatYYYYMMDDHHMM(Date date){
        return sdf_type_YMDHM.format(date);
    }

    public static Date parseYYYYMMDDHHMM(String date) throws Exception{
        return sdf_type_YMDHM.parse(date);
    }

    public static String getDateDefaultFormateText(Date date){
        if(date==null){
            return "";
        }
        return sdf.format(date);
    }

    public static String getDateDefaultFormateText(Long timemills){
        if(timemills==null){
            return "";
        }
        return sdf.format(new Date(timemills));
    }

    public static String getTimeFormatText(Long time){
        return getTimeFormatText(time==null?null:new Date(time));
    }

    /**
     * 返回文字描述的日期
     *
     * @param date
     * @return
     */
    public static String getTimeFormatText(Date date) {
        if (date == null) {
            return "";
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > ONE_YEAR) {
            r = (diff / ONE_YEAR);
            return r + ONE_YEAR_AGO;
        }
        if (diff > ONE_MONTH) {
            r = (diff / ONE_MONTH);
            return r + ONE_MONTH_AGO;
        }
        if (diff > ONE_DAY) {
            r = (diff / ONE_DAY);
            return r + ONE_DAY_AGO;
        }
        if (diff > ONE_HOUR) {
            r = (diff / ONE_HOUR);
            return r + ONE_HOUR_AGO;
        }
        if (diff > ONE_MINUTE) {
            r = (diff / ONE_MINUTE);
            return r + ONE_MINUTE_AGO;
        }
        return "刚刚";
    }

    /**
     * 获取与当前时间的相对时间的友好描述
     * @param millisecond 毫秒数
     * @return
     */
    public static String getFriendlyDate(long millisecond) {
        //时间差
        long delta = new Date().getTime() - millisecond;
        if (delta < 1L * ONE_MINUTE) {
            long seconds = delta / 1000L;
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 60L * ONE_MINUTE) {
            long minutes = delta / ONE_MINUTE;
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = delta / ONE_HOUR;
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 7L * ONE_DAY) {
            long days = delta / ONE_DAY;
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        //一周后的时间用[月-日]来表示
        String date = sdf_type_2.format(new Date(millisecond));
        return date.substring(5);
    }

    /**
     * 将毫秒数转换成HH:mm:ss格式的时间
     * @param millisecond
     * @return
     */
    public static String getHmsDate(long millisecond) {
        long hour = millisecond/1000L/3600L;
        long min = millisecond/1000L%3600L/60L;
        long second = millisecond/1000L%3600L%60L;
        return new StringBuffer(String.format("%02d", hour)).append(":").append(String.format("%02d", min)).append(":").append(String.format("%02d", second)).toString();
    }
}
