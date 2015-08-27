package com.dualfual.common;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.TimeZone;


public class DateUtil {


    public static final DateTimeFormatter df = DateTimeFormat.forPattern("yyyyMMdd");
    public static final DateTimeFormatter df_MMddyyyy = DateTimeFormat.forPattern("MM/dd/yyyy");
    public static final DateTimeFormatter df_Mdyyyy = DateTimeFormat.forPattern("M/d/yyyy");
    public static final DateTimeFormatter df2 = DateTimeFormat.forPattern("yyyyMMdd HH:mm:ss");


    public static DateTime getDateTime(long time, DateTimeZone dateTimeZone) {
        DateTime dateTime = new DateTime(time, dateTimeZone);
        return dateTime;
    }

    public static int getHourEnding(DateTime dateTime) {
        return (getMinute(dateTime) > 0) ? (dateTime.getHourOfDay() + 1) : dateTime.getHourOfDay();
    }

    public static int getMinute(DateTime dateTime) {
        return dateTime.getMinuteOfHour();
    }

    public static int getIntDate(Date date) {

        DateTime dateTime = new DateTime(date);
        int day = dateTime.getDayOfMonth();
        int month = dateTime.getMonthOfYear();
        int year = dateTime.getYear();
        int dateInt = (year * 10000) + (month * 100) + day;
        return dateInt;
    }

    public static int firstOfMonth(int date) {
        DateTime dateTime = new DateTime(javaDate(date));
        int day = 1;
        int month = dateTime.getMonthOfYear();
        int year = dateTime.getYear();
        int dateInt = (year * 10000) + (month * 100) + day;
        return dateInt;
    }

    public static int getIntDate(DateTime dateTime) {
        int day = dateTime.getDayOfMonth();
        int month = dateTime.getMonthOfYear();
        int year = dateTime.getYear();
        int date = (year * 10000) + (month * 100) + day;
        return date;
    }

    public static DateTime toDateTime(int date, int hour, DateTimeZone dateTimeZone) {
        return df2.withZone(dateTimeZone).parseDateTime(date + " " + hour + ":00:00");
    }
    public static DateTime toDateTime(int date, int hour) {
        return df2.parseDateTime(date + " " + hour + ":00:00");
    }

    public static Date javaDate(int date) {
        return df.parseDateTime(date + "").toDate();
    }

    public static String print_M_D_YYYY(int date){
        return df_Mdyyyy.print(javaDate(date).getTime());
    }

    public static int addDays(int dateInt, int days) {
        DateTime dt = toDateTime(dateInt, 0);
        return addDays(dt, days);
    }
    public static int addDays(DateTime dt, int days) {
        DateTime dateTime = new DateTime(dt);
        dateTime = dateTime.plusDays(days);
        int day = dateTime.getDayOfMonth();
        int month = dateTime.getMonthOfYear();
        int year = dateTime.getYear();
        int date = (year * 10000) + (month * 100) + day;
        return date;
    }

    public static int addMonth(DateTime dt, int days) {
        DateTime dateTime = new DateTime(dt);
        dateTime = dateTime.plusMonths(days);
        int day = dateTime.getDayOfMonth();
        int month = dateTime.getMonthOfYear();
        int year = dateTime.getYear();
        int date = (year * 10000) + (month * 100) + day;
        return date;
    }

    public static void main(String[] args) {
//        DateTime dt = DateTime.now().minusDays(5);
//
//        System.out.println(dt.getMonthOfYear());
//        System.out.println(dt.getDayOfMonth());
//        System.out.println(dt.getHourOfDay());
//        System.out.println(dt.getMinuteOfHour());
//        System.out.println(dt.getYear());
//        System.out.println(getIntDate(dt));

        DateTime dt = toDateTime(20150406, 10);

        System.out.println(getHourEnding(dt));
    }
}
