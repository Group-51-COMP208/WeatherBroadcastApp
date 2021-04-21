package com.example.lib;

import java.time.Duration;
import java.util.Calendar;

/*
 * A function library of useful utilities
 */
public class Utilities {
    // Returns a new Calender specifying the time that is period * number after startTime
    public static Calendar addTime(Calendar startTime, Duration period, int number) {
        Calendar newTime = (Calendar) startTime.clone();
        newTime.add(Calendar.MINUTE, (int) period.toMinutes() * number);
        return newTime;
    }
}
