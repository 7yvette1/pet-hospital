package com.scnu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author xuzhijun
 * @date 2024年11月20日 18:34
 */
public class DateUtils {


    /**
     * 获取一个时间+分钟数，之后的时间
     * @author xuzhijun
     * @date 2024/11/20 19:33
     * @param date 时间
     * @param minutes 分钟数
     * @param style 时间格式化
     * @return java.lang.String
     */
    public static String getAddMinutes(String date, String minutes,String style){
        String format = "";
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        try {
            Date date1 = formatter.parse(date);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);

            calendar.add(Calendar.MINUTE, Integer.valueOf(minutes));

            Date time = calendar.getTime();
            format = formatter.format(time);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format;
    }

}
