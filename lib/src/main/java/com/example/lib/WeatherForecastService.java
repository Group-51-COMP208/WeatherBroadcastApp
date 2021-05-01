package com.example.lib;

import java.util.ArrayList;
import java.util.Calendar;
import java.time.Duration;


/*
* Abstract interface for a weather forecasting service
 */
public interface WeatherForecastService {
    /**
     * Gets an array of localized weather predictions for the specified period, with detailed
     * weather information.
     * @param start The date/time of the first sample
     * @param location The location in the world that the forecast applies to
     * @return An array of weather forecast samples, ordered by increasing time in increments
     * specified by 'resolution', starting from 'start'.
     */
    ArrayList<DetailedWeatherForecastSample> getDetailedForecast(Calendar start, Location location);

    /**
     * Gets an array of localized weather predictions for the specified period, with just simple
     * weather information.
     * @param start The date/time of the first sample
     * @param location The location in the world that the forecast applies to
     * @return An array of weather forecast samples, ordered by increasing time in increments
     * specified by 'resolution', starting from 'start'.
     */
    ArrayList<SimpleWeatherForecastSample> getSimpleForecast(Calendar start, Location location);

    /**
     * A long term forecast from the weather service
     * @return A plain text natural language weather forecast
     * @see TextualForecast
     */
    TextualForecast getLongTermForecast();

    /**
     * Gets all the locations supported by the weather service
     * The 'getApiId' method for the returned locations should
     * provide the correct way to refer to that location in the
     * API
     * @return All the locations supported by the weather service, with
     * all properties set correctly
     */
    ArrayList<Location> getAvailableLocations();

    /**
     *
     * @param displayName name of the location, as it appears in the underlying API
     * @return Location object of the requested displayName, with all other fields set
     */
    Location getLocationByName(String displayName);
}
