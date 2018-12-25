package io.bitjob.util;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**

 * Date: 15-5-11
 * Time: 下午9:50
 */
public class DateEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String dateStr) throws IllegalArgumentException {
        Date d = this.parseToDate(dateStr);
        this.setValue(d);
    }

    private Date parseToDate(String date) {
        String format = "yyyy-MM-dd HH:mm:ss";
        DateFormat df = DateUtil.getDateFormat(format);
        Date d = null;
        try {
            d = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }
}
