package com.example.lib;

import java.util.ArrayList;


/*
* Abstract interface for a weather forecasting service
 */
public interface WeatherForecastService {
    /**
     * Gets an array of localized weather predictions for the specified period, with detailed
     * weather information.
     * @param location The location in the world that the forecast applies to
     * @return An array of weather forecast samples, ordered by increasing time in increments
     * specified by 'resolution', starting from 'start'.
     */
    ArrayList<DetailedWeatherForecastSample> getDetailedForecast(Location location) throws ApiException;

    /**
     * A forecast with general weather conditions for a day
     * @return The general weather conditions for the daytime of
     * as many consecutive days are available from the underlying API
     * from the underlying API.
     */
    ArrayList<DetailedWeatherForecastSample> getDailyForecast(Location location) throws ApiException;


    /**
     * A long term forecast from the weather service
     * @return A plain text natural language weather forecast
     * @see TextualForecast
     */
    TextualForecast getLongTermForecast() throws ApiException;

    /**
     * Gets all the locations supported by the weather service
     * The 'getApiId' method for the returned locations should
     * provide the correct way to refer to that location in the
     * API
     * @return All the locations supported by the weather service, with
     * all properties set correctly
     */
    ArrayList<Location> getAvailableLocations() throws ApiException;

    /**
     *
     * @param displayName name of the location, as it appears in the underlying API
     * @return Location object of the requested displayName, with all other fields set
     */
    Location getLocationByName(String displayName) throws ApiException;
}
