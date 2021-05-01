package com.example.lib;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/*
* This presently does not add anything to WeatherForecastSample but I think it
* is worth having as it is not unlikely that it will change.
 */
public class SimpleWeatherForecastSample extends WeatherForecastSample {
    public Location location;
    SimpleWeatherForecastSample(Calendar timeStamp, WeatherType weatherType, Location location) {
        super();
        this.timeStamp = timeStamp;
        this.weatherType = weatherType;
        this.location = location;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return "SimpleWeatherForecastSample{" +
                "  timeStamp=" + format.format(timeStamp.getTime()) +
                ", weatherType=" + weatherType +
                ", location=" + location.getDisplayName() +
                '}';
    }

}
