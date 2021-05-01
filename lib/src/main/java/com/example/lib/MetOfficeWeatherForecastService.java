package com.example.lib;
import java.io.InputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import org.w3c.dom.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map.Entry;
import java.io.BufferedReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
* Note: There are many HTTP clients that we could use but
* it appears that this is the recommended Http client for Android,
* if you are looking for a specific example:
* https://developer.android.com/reference/java/net/HttpURLConnection.html
 */

public class MetOfficeWeatherForecastService implements WeatherForecastService {
    MetOfficeWeatherForecastService() {
        String api_key = "474b382b-4970-4685-a1dd-8bffd071216b";
        Document Day;
        Document Hour;
        ArrayList<String> ids = new ArrayList<String>();
    }

    /**
     * @see WeatherForecastService
     */
    @Override
    public ArrayList<DetailedWeatherForecastSample> getDetailedForecast(Calendar start, Duration resolution, int numSamples, Location location) {
        // TODO: Please implement
        // Forecast API
        // URL detailData = new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/352409?res=3hourly&key=474b382b-4970-4685-a1dd-8bffd071216b");
        // Location API
        // URL detailloc =  new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/sitelist?key=6cb4001b-cb25-4682-baf3-61a64918d89b");
        return null;
    }

    /**
     * @see WeatherForecastService
     */
    @Override
    public ArrayList<SimpleWeatherForecastSample> getSimpleForecast(Calendar start, Duration resolution, int numSamples, Location location) {
        // TODO: Please implement
        // Forecast API
        // URL simpleData = new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/352409?res=daily&key=474b382b-4970-4685-a1dd-8bffd071216b");
        // Location API
        // URL simpleloc =  new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxobs/all/json/sitelist?key=6cb4001b-cb25-4682-baf3-61a64918d89b");
        if (simpleforecast == null) {
            try {
                simpleforecast = new ArrayList<SimpleWeatherForecastSample>();
                URL locUrl = new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/352409?res=daily&key=" + apiKey);
                HttpURLConnection connection = (HttpURLConnection) locUrl.openConnection();
                try {
                    Reader reader = new InputStreamReader(connection.getInputStream());
                    JSONParser parser = new JSONParser();
                    JSONObject obj = (JSONObject) parser.parse(reader);

                    JSONObject forecast = (JSONObject) obj.get("Forecasts");
                    JSONArray forecasts = (JSONArray) forecast.get("Forecast");
                    for (Object o : forecasts) {
                        JSONObject jobj = (JSONObject) o;
                        Calendar timeStamp = (Calendar) jobj.get("timeStamp");
                        //String weatherType  = (String) jobj.get("weatherType");

                        for (int i = 0; i < numSamples; ++i) {
                            //   ArrayList<String> data = new ArrayList<>();
                            JSONObject simpleforecast = (JSONObject) forecast.get(i);
                            String loc = location.getDisplayName();
                            // data.add(loc);
                        }
                        Location loc = (Location) jobj.get("location");
                        //   simpleforecast.add(data);
                        simpleforecast.add(new SimpleWeatherForecastSample(
                                timeStamp,
                                numSamples,
                                loc
                        ));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    connection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return simpleforecast;

        // return null;
    }

    /**
     * @see WeatherForecastService
     */
    @Override
    public TextualForecast getLongTermForecast() {
        // TODO: Please implement
        // API link
        // URL longterm = new URL("http://datapoint.metoffice.gov.uk/public/data/txt/wxfcs/regionalforecast/json/500?key=6cb4001b-cb25-4682-baf3-61a64918d89b");
        return null;
    }

    /**
     * @see WeatherForecastService
     */
    @Override
    public ArrayList<Location> getAvailableLocations() {
        if (locationCache == null) {
            try {
                locationCache = new ArrayList<Location>();
                URL locUrl = new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/sitelist?key=" + apiKey);
                HttpURLConnection connection = (HttpURLConnection) locUrl.openConnection();
                try {
                    Reader reader = new InputStreamReader(connection.getInputStream());
                    JSONParser parser = new JSONParser();
                    JSONObject obj = (JSONObject) parser.parse(reader);

                    JSONObject location = (JSONObject) obj.get("Locations");
                    JSONArray locations = (JSONArray) location.get("Location");
                    for (Object o : locations) {
                        JSONObject jobj = (JSONObject) o;
                        String name = (String) jobj.get("name");
                        String id = (String) jobj.get("id");
                        String latitudeString = (String) jobj.get("latitude");
                        String longitudeString = (String) jobj.get("longitude");
                        float latitude = Float.parseFloat(latitudeString);
                        float longitude = Float.parseFloat(longitudeString);

                        locationCache.add(new Location(
                                name,
                                id,
                                latitude,
                                longitude
                        ));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    connection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return locationCache;
    }

    /**
     * @return
     * @see WeatherForecastService
     */
    @Override
    public Location getLocationByName(String displayName) {
        for(Location l: getAvailableLocations()) {
            if(l.getDisplayName().equals(displayName)) {
                return l;
            }
        }
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
//        for (Location location : ws.getAvailableLocations()) {
//            System.out.println(location);
//        }
      /*  for(SimpleWeatherForecastSample forecast: ws.getSimpleForecast()) {
            System.out.println(forecast);
        }*/

        Location location = ws.getLocationByName("Manchester");
        System.out.println("Location by name: " + location);
    }

    private ArrayList<Location> locationCache;
    private ArrayList<SimpleWeatherForecastSample> simpleforecast;
}
