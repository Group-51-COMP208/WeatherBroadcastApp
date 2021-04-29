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
        // URL detailData = new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/xml/352409?res=3hourly&key=474b382b-4970-4685-a1dd-8bffd071216b");
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
        return null;
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
        if(locationCache == null) {
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
                    for(Object o: locations) {
                        JSONObject jobj        = (JSONObject) o;
                        String name            = (String) jobj.get("name");
                        String id              = (String) jobj.get("id");
                        String latitudeString  = (String) jobj.get("latitude");
                        String longitudeString = (String) jobj.get("longitude");
                        float latitude         = Float.parseFloat(latitudeString);
                        float longitude        = Float.parseFloat(longitudeString);

                        locationCache.add(new Location(
                            name,
                            id,
                            latitude,
                            longitude
                        ));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    connection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return locationCache;
    }

    /**
     * @see WeatherForecastService
     * @return
     */
    @Override
    public Location getLocationByName(String displayName) {
        // TODO: Implement after getAvailableLocations
        // I suggest some sort of caching of the available locations
        // to reduce calls to the API

        String url = "http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/sitelist";
        String charset = "UTF-8";
        String param1 = apiKey;
        ArrayList<String> ids = new ArrayList<String>();
        try {
            String query = String.format("key=%s",
                    URLEncoder.encode(param1, charset));
            //URLEncoder.encode(param2, charset));

            URLConnection connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();

            HttpURLConnection conn = (HttpURLConnection)connection;
            int status = conn.getResponseCode();
            for (Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {

            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset));
            String json = reader.readLine();

            JSONParser parser = new JSONParser();
            Object resultObject = parser.parse(json);

            JSONObject obj =(JSONObject)resultObject;
            JSONObject locs =(JSONObject)obj.get("Locations");
            JSONArray loc = (JSONArray)locs.get("Location");


            for (int i=0;i<loc.size();i++) {
                JSONObject location = (JSONObject)loc.get(i);
                String id = (String)location.get("id");
                ids.add(displayName);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
         //return displayName;

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
        for(Location location: ws.getAvailableLocations()) {
            System.out.println(location);
        }
    }

    private ArrayList<Location> locationCache;
}
