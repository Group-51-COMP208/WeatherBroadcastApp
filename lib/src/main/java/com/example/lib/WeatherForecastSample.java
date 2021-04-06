package com.example.lib;
import java.util.Calendar;

/*
* Base class representing all the information that is common to a Weather Forecast Sample
* of any detail level.
 */
public class WeatherForecastSample {
    public Calendar timeStamp;
    public WeatherType weatherType;
    public Location location;

    @Override
    public String toString() {
        return "WeatherForecastSample{" +
                "timeStamp=" + timeStamp +
                ", weatherType=" + weatherType +
                ", location=" + location +
                '}';
    }
}
