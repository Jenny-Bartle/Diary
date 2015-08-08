package com.jenny.diary;

import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jenny on 8/9/2015.
 */
public class DateFormatUtil {

    public static SimpleDateFormat getSimpleDateFormatter(Date date) {

        Time startTime = new Time();
        startTime.set(date.getTime());
        int startDay = Time.getJulianDay(date.getTime(), startTime.gmtoff);

        Date now = new Date(System.currentTimeMillis());
        Time currentTime = new Time();
        currentTime.set(now.getTime());
        int currentDay = Time.getJulianDay(now.getTime(), currentTime.gmtoff);

        int days = Math.abs(currentDay - startDay);

        if (days == 0) {
            return new SimpleDateFormat("h:mm a");
        } else if (days <7) {
            return new SimpleDateFormat("EEE");
        } else {
            return new SimpleDateFormat("d MMM yy");
        }
    }

}
