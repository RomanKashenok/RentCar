package com.kashenok.rentcar.util;

import com.kashenok.rentcar.exception.ServiceException;
import java.sql.Date;

/**
 * The DateCreatorSQL class. Used to create java.sql.Date from String values
 */
public class DateCreatorSQL {

    /**
     * The method createDate. Create date from String value
     *
     * @param year is the String value of Year
     * @param month is the String value of Month
     * @param day is the String value of Day
     * @return filled Date object
     *
     */
    public static Date createDate(String year, String month, String day) {
        if (day == null || day.equals("")) {
            return null;
        }
        String dateString = year + "-" + month + "-" + day;
        Date date = Date.valueOf(dateString);
        return date;
    }

}
