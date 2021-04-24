package com.example.lib;

import java.text.SimpleDateFormat;

public class DetailedWeatherForecastSample extends WeatherForecastSample {
    // 0 degrees is North and clockwise is the positive direction
    public float windDirection_degrees;
    public float windSpeed_mph;
    public float temperature_celsius;
    public int uvIndex;
    public float precipitationProbability;

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return "DetailedWeatherForecastSample{" +
                "  timeStamp=" + format.format(timeStamp.getTime()) +
                ", windDirection_degrees=" + windDirection_degrees +
                ", windSpeed_mph=" + windSpeed_mph +
                ", temperature_celsius=" + temperature_celsius +
                ", uvIndex=" + uvIndex +
                ", precipitationProbability=" + precipitationProbability +
                ", weatherType=" + weatherType +
                ", location=" + location.getDisplayName() +
                '}';
    }
}
