package com.example.weatherbroadcastapp;

import com.example.lib.LocationService;
import com.example.lib.MetOfficeWeatherForecastService;
import com.example.lib.PlaceholderWeatherForecastService;
import com.example.lib.WeatherForecastService;

/*
* A Singleton class, the purpose of which is to make available
* services, including the WeatherForecastService to the rest of the
* application while hiding the specific implementation details.
* You access the Services with 'Services.get()'
 */
public class Services {
    public WeatherForecastService getWeatherForecastService() {
        return weatherForecastService;
    }

    public LocationService getLocationService() {
        return locationService;
    }

    public static Services get() {
        if(instance == null) {
            // 'synchronized' makes this thread-safe, just in case
            synchronized (Services.class) {
                if(instance == null) {
                    instance = new Services();
                }
            }
        }
        return instance;
    }

    private Services() {
        weatherForecastService = new MetOfficeWeatherForecastService();
        locationService = new SharedPreferencesLocationService();
    }

    private static Services instance = null;

    WeatherForecastService weatherForecastService;
    LocationService locationService;
}
