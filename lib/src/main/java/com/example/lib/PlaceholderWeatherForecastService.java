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
    public ArrayList<DetailedWeatherForecastSample> getDetailedForecast(Location location) throws ApiException {
        // Ascending values for testing
        ArrayList<DetailedWeatherForecastSample> samples = new ArrayList<DetailedWeatherForecastSample>();
        Calendar start = Calendar.getInstance();
        Duration resolution = Duration.ofHours(3);
        final int numSamples = 40;
        for(int i = 0; i < numSamples; ++i) {
            DetailedWeatherForecastSample sample = new DetailedWeatherForecastSample();
            sample.timeStamp = (Calendar) start.clone();
            sample.timeStamp.add(Calendar.MINUTE, (int) resolution.toMinutes() * i);
            sample.location = location;
            sample.precipitationProbability = i * 0.05f;
            sample.temperature_celsius = i + 10;
            sample.uvIndex = i;
            sample.windDirection_degrees = i * 15 + 45;
            sample.windSpeed_mph = i + 10;
            // Just cycling through the weather types for testing purposes
            sample.weatherType = WeatherType.values()[i % WeatherType.values().length];
            samples.add(sample);
        }
        return samples;
    }

    @Override
    public ArrayList<DetailedWeatherForecastSample> getDailyForecast(Location location) throws ApiException {
        ArrayList<DetailedWeatherForecastSample> samples = new ArrayList<DetailedWeatherForecastSample>();
        Calendar start = Calendar.getInstance();
        Duration resolution = Duration.ofHours(24);
        int numSamples = 5;
        for(int i = 0; i < numSamples; ++i) {
            DetailedWeatherForecastSample daySample = new DetailedWeatherForecastSample();
            daySample.timeStamp = (Calendar) start.clone();
            daySample.timeStamp.add(Calendar.MINUTE, (int) resolution.toMinutes() * i);
            daySample.location = location;
            daySample.precipitationProbability = i * 0.05f;
            daySample.temperature_celsius = i + 10;
            daySample.uvIndex = i;
            daySample.windDirection_degrees = i * 15 + 45;
            daySample.windSpeed_mph = i + 10;
            // Just cycling through the weather types for testing purposes
            daySample.weatherType = WeatherType.values()[i % WeatherType.values().length];
            samples.add(daySample);
        }
        return samples;
    }

    @Override
    public TextualForecast getLongTermForecast() throws ApiException {
        TextualForecast textualForecast = new TextualForecast();
        textualForecast.period = "The next 30 days";
        textualForecast.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        return textualForecast;
      //  return null;
    }


    @Override
    public ArrayList<Location> getAvailableLocations() throws ApiException {
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location("Liverpool", "?", 53.4f, -2.99f));
        locations.add(new Location("Manchester", "?", 53.4f, -2.24f));
        locations.add(new Location("London", "?", 51.5f, -0.13f));
        locations.add(new Location("Glasgow", "?", 55.9f, -4.25f));
        locations.add(new Location("Cardiff", "?", 51.48f, -3.18f));
        locations.add(new Location("Brecon", "?", 51.95f, -3.39f));
        locations.add(new Location("Belfast", "?", 54.6f, -5.93f));

        return locations;
    }


    @Override
    public Location getLocationByName(String displayName) throws ApiException {
        for(Location l: getAvailableLocations()) {
            if(l.getDisplayName().equals(displayName)) {
                return l;
            }
        }
        return null;
    }


    public static void main(String[] args) {
        try {
            PlaceholderWeatherForecastService ws = new PlaceholderWeatherForecastService();
            for (Location location : ws.getAvailableLocations()) {
                System.out.println(location.getDisplayName());
            }

            Location liv = ws.getAvailableLocations().get(0);

            for (DetailedWeatherForecastSample sample : ws.getDetailedForecast(liv)) {
                System.out.println(sample);
            }
        } catch(ApiException e) {
            System.err.println("PlaceholderWeatherForecastService has thrown an ApiException, which it shouldn't have...");
        }
    }
}
