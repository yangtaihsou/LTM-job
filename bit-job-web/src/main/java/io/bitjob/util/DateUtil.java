package io.bitjob.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**

 * Date: 14-2-27
 * Time: 上午10:02
 */
public class DateUtil {

    private static Map<String, ThreadLocal<SimpleDateFormat>> dataFormatMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    /**
     * @param dateStr
     * @param fromFormat 原始的时间格式
     * @param toFormat   转换后的时间格式
     * @return
     */
    public static String formatDate(String dateStr, String fromFormat, String toFormat) {

        DateFormat fromDateFormat = new SimpleDateFormat(fromFormat);
        Date date = null;
        try {
            date = fromDateFormat.parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("时间转换异常:dateStr=" + dateStr + ", fromFormat=" + fromFormat);
        }

        DateFormat toDateFormat = new SimpleDateFormat(toFormat);

        return toDateFormat.format(date);
    }

    /**
     * 使用ThreadLocal以空间换时间解决SimpleDateFormat线程安全问题
     * 第一次调用get将返回null
     * 故需要初始化一个SimpleDateFormat，并set到threadLocal中
     *
     * @return
     */
    public static SimpleDateFormat getDateFormat(final String pattern) {
        ThreadLocal<SimpleDateFormat> threadLocal = dataFormatMap.get(pattern);
        if (threadLocal == null) {
            threadLocal = new ThreadLocal<SimpleDateFormat>() {
                @Override
                protected SimpleDateFormat initialValue() {
                    return new SimpleDateFormat(pattern);
                }
            };
            dataFormatMap.put(pattern, threadLocal);
        }
        return threadLocal.get();
    }
}
