package io.bitjob.util;

import org.apache.commons.beanutils.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;


/**

 * Date: 15-1-19
 * Time: 下午3:48
 */
public class DateTimeConverter implements Converter {

    private static final String DATE = "yyyy-MM-dd";
    private static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
    private static final String TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";

    public static Object toStr(Class type, Object value) {
        if (value == null)
            return null;
        if (value instanceof Date) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(DATETIME);
                return sdf.format((Date) value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    @Override
    public Object convert(Class type, Object value) {
        return toStr(type, value);
    }


}