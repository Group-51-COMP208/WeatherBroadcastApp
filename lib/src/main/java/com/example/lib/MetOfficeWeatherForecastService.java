package com.example.lib;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;

/*
* Note: There are many HTTP clients that we could use but
* it appears that this is the recommended Http client for Android,
* if you are looking for a specific example:
* https://developer.android.com/reference/java/net/HttpURLConnection.html
 */

public class MetOfficeWeatherForecastService implements WeatherForecastService {
    MetOfficeWeatherForecastService() {

    }

    /**
     * @see WeatherForecastService
     */
    @Override
    public ArrayList<DetailedWeatherForecastSample> getDetailedForecast(Calendar start, Duration resolution, int numSamples, Location location) {
        // TODO: Please implement
        return null;
    }

    /**
     * @see WeatherForecastService
     */
    @Override
    public ArrayList<SimpleWeatherForecastSample> getSimpleForecast(Calendar start, Duration resolution, int numSamples, Location location) {
        // TODO: Please implement
        return null;
    }

    /**
     * @see WeatherForecastService
     */
    @Override
    public TextualForecast getLongTermForecast() {
        // TODO: Please implement
        /* API link
        "http://datapoint.metoffice.gov.uk/public/data/txt/wxfcs/regionalforecast/json/500?key=6cb4001b-cb25-4682-baf3-61a64918d89b"
       */
        return null;
    }

    /**
     * @see WeatherForecastService
     */
    @Override
    public ArrayList<Location> getAvailableLocations() {
        // TODO: Please implement
        return null;
    }

    /**
     * @see WeatherForecastService
     */
    @Override
    public Location getLocationByName(String displayName) {
        // TODO: Implement after getAvailableLocations
        // I suggest some sort of caching of the available locations
        // to reduce calls to the API
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


        try {
            URL url = new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/sitelist?key=474b382b-4970-4685-a1dd-8bffd071216b");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                byte[] bytes = new byte[1000000];
                in.read(bytes);
                String s = new String(bytes, StandardCharsets.UTF_8);
                System.out.println(s);
            } finally {
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}
