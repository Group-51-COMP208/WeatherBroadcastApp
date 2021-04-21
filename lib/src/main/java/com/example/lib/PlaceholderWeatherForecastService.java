package com.example.lib;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;

/*
* Returns artificially constructed data so that this can be used as a placeholder for
* a real WeatherForecastService implementation
 */
public class PlaceholderWeatherForecastService implements WeatherForecastService {
    @Override
    public ArrayList<DetailedWeatherForecastSample> getDetailedForecast(Calendar start, Duration resolution, int numSamples, Location location) {
        // Ascending values for testing
        ArrayList<DetailedWeatherForecastSample> samples = new ArrayList<DetailedWeatherForecastSample>();
        for(int i = 0; i < numSamples; ++i) {
            DetailedWeatherForecastSample sample = new DetailedWeatherForecastSample();
            sample.timeStamp = (Calendar) start.clone();
            sample.timeStamp.add(Calendar.MINUTE, (int) resolution.toMinutes() * i);
            sample.location = location;
            sample.precipitationProbability = i * 0.05f;
            sample.temperature_celsius = i;
            sample.uvIndex = i;
            sample.windDirection_degrees = i * 15;
            sample.windSpeed_mph = i;
            // Just cycling through the weather types for testing purposes
            sample.weatherType = WeatherType.values()[i % WeatherType.values().length];
            samples.add(sample);
        }
        return samples;
    }

    @Override
    public ArrayList<SimpleWeatherForecastSample> getSimpleForecast(Calendar start, Duration resolution, int numSamples, Location location) {
        ArrayList<SimpleWeatherForecastSample> samples = new ArrayList<SimpleWeatherForecastSample>();
        for(int i = 0; i < numSamples; ++i) {
            SimpleWeatherForecastSample sample = new SimpleWeatherForecastSample();
            sample.timeStamp = (Calendar) start.clone();
            sample.timeStamp.add(Calendar.MINUTE, (int) resolution.toMinutes() * i);
            sample.location = location;
            // Just cycling through the weather types for testing purposes
            sample.weatherType = WeatherType.values()[i % WeatherType.values().length];
            samples.add(sample);
        }
        return samples;
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
        locations.add(new Location("London", "?", 51.5f, -0.13f));
        locations.add(new Location("Glasgow", "?", 55.9f, -4.25f));
        locations.add(new Location("Cardiff", "?", 51.48f, -3.18f));

        return locations;
    }


    @Override
    public Location getLocationByName(String displayName) {
        for(Location l: getAvailableLocations()) {
            if(l.getDisplayName().equals(displayName)) {
                return l;
            }
        }
        return null;
    }


    public static void main(String[] args) {
        PlaceholderWeatherForecastService ws = new PlaceholderWeatherForecastService();
        for(Location location: ws.getAvailableLocations()) {
            System.out.println(location.getDisplayName());
        }

        Location liv = ws.getAvailableLocations().get(0);

        for(DetailedWeatherForecastSample sample: ws.getDetailedForecast(Calendar.getInstance(),
                Duration.ofHours(1), 24, liv)) {
            System.out.println(sample);
        }
    }
}
