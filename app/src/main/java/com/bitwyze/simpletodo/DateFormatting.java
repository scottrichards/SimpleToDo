package com.bitwyze.simpletodo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by scottrichards on 1/16/16.
 */
public class DateFormatting {
    private static DateFormatting dateFormatting;
    private SimpleDateFormat iso8601Format = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat yearMonthDayFormat = new SimpleDateFormat(
            "yyyy-MM-dd");
    private DateFormat dateFormat = DateFormat.getDateInstance();

    private DateFormatting()
    {

    }

    public static DateFormatting getInstance()
    {
        if (dateFormatting == null)
        {
            dateFormatting = new DateFormatting();
        }
        return dateFormatting;
    }

    public String iso8601Format(Date date) {
        return iso8601Format.format(date);
    }

    public String yearMonthDayFormat(Date date) {
        return yearMonthDayFormat.format(date);
    }

    // Parse yyyy-MM-dd string and return corresponding date
    public Date yearMonthDayFormat(String dateString) {
        try {
            if (dateString == null || dateString == "") {
                return null;
            }
            Date date = yearMonthDayFormat.parse(dateString);
            return date;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public Date iso8601Format(String dateTimeString) {
        try {
            if (dateTimeString == null || dateTimeString == "") {
                return null;
            }
            Date date = iso8601Format.parse(dateTimeString);
            return date;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public String localeFormat(Date date) {
        return dateFormat.format(date);
    }
}
