package com.example.lib;

import java.util.ArrayList;
import java.util.Date;
import java.time.Duration;


/*
* Abstract interface for a weather forecasting service
 */
public interface WeatherForecastService {
    ArrayList<DetailedWeatherForecastSample> getDetailedForecast(Date start, Duration resolution, Location location);
    ArrayList<SimpleWeatherForecastSample> getSimpleForecast(Date start, Duration resolution, Location location);
    TextualForecast getLongTermForecast();
    ArrayList<Location> getAvailableLocations();
}
