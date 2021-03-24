package com.example.lib;

import java.util.ArrayList;

/*
* Abstract interface for a weather forecasting service
 */
public interface WeatherForecastService {
    ArrayList<DetailedWeatherForecastSample> getDetailedForecast();
    ArrayList<SimpleWeatherForecastSample> getSimpleForecast();
    TextualForecast getLongTermForecast();
    ArrayList<Location> getAvailableLocations();
}
