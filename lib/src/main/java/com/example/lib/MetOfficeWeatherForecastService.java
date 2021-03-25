package com.example.lib;
import java.util.ArrayList;

/*
* Note: There are many HTTP clients that we could use but
* it appears that this is the recommended Http client for Android,
* if you are looking for a specific example:
* https://developer.android.com/reference/java/net/HttpURLConnection.html
 */

public class MetOfficeWeatherForecastService implements WeatherForecastService {
    MetOfficeWeatherForecastService() {
        // Test comment
    }


    @Override
    public ArrayList<DetailedWeatherForecastSample> getDetailedForecast() {
        // TODO: Please implement
        return null;
    }

    @Override
    public ArrayList<SimpleWeatherForecastSample> getSimpleForecast() {
        // TODO: Please implement
        return null;
    }

    @Override
    public TextualForecast getLongTermForecast() {
        // TODO: Please implement
        return null;
    }

    @Override
    public ArrayList<Location> getAvailableLocations() {
        // TODO: Please implement
        return null;
    }

    // This API key is from an account created by Jonathan Wood
    // (J.M.Wood2@student.liverpool.ac.uk)
    private final String apiKey = "474b382b-4970-4685-a1dd-8bffd071216b";


    // You can run this method using the green 'run' arrow in the bar on the left
    // This will be useful for testing throughout development
    public static void main(String[] args) {
        // I suggest we use this to try out API calls
        MetOfficeWeatherForecastService ws = new MetOfficeWeatherForecastService();
        System.out.println("You can see this output in the 'run' tab at the bottom");
    }
}
