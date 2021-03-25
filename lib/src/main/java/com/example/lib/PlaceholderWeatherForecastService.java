package com.example.lib;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

/*
* Returns artificially constructed data so that this can be used as a placeholder for
* a real WeatherForecastService implementation
 */
public class PlaceholderWeatherForecastService implements WeatherForecastService {
    @Override
    public ArrayList<DetailedWeatherForecastSample> getDetailedForecast(Date start, Duration resolution, Location location) {
        return null;
    }

    @Override
    public ArrayList<SimpleWeatherForecastSample> getSimpleForecast(Date start, Duration resolution, Location location) {
        return null;
    }

    @Override
    public TextualForecast getLongTermForecast() {
        return null;
    }

    @Override
    public ArrayList<Location> getAvailableLocations() {
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location("Liverpool", "?", 53.4f, -2.99f));
        locations.add(new Location("Manchester", "?", 53.4f, -2.24f));
        return locations;
    }


    public static void main(String[] args) {
        // I suggest we use this to try out API calls
        PlaceholderWeatherForecastService ws = new PlaceholderWeatherForecastService();
        for(Location location: ws.getAvailableLocations()) {
            System.out.println(location.getDisplayName());
        }
    }
}
