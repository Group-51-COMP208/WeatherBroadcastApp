package com.example.lib;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import org.w3c.dom.*;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/*
* Note: There are many HTTP clients that we could use but
* it appears that this is the recommended Http client for Android,
* if you are looking for a specific example:
* https://developer.android.com/reference/java/net/HttpURLConnection.html
 */

public class MetOfficeWeatherForecastService implements WeatherForecastService {
    public MetOfficeWeatherForecastService() {
        String api_key = "474b382b-4970-4685-a1dd-8bffd071216b";
        Document Day;
        Document Hour;
        ArrayList<String> ids = new ArrayList<String>();
    }

    final private static String[] compassCodes = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE",
                                            "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};

    public static float metOfficeCompassCodeToDegrees(String code) {
        for(int i = 0; i < compassCodes.length; ++i) {
            if(code.equals(compassCodes[i])) {
                float segmentSize = 360.f / compassCodes.length;
                return i * segmentSize;
            }
        }
        return 0;
    }


    /**
     * @see WeatherForecastService
     */
    @Override
    public ArrayList<DetailedWeatherForecastSample> getDetailedForecast(Location location) {
        // We are expecting 3 hourly samples so will be 8 in a full day
        final int expectedDailySamples = 8;
        final int sampleHourlyResolution = 3;

        // TODO: Please implement
        // Forecast API
        // URL detailData = new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/352409?res=3hourly&key=474b382b-4970-4685-a1dd-8bffd071216b");
        // Location API
        // URL detailloc =  new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/sitelist?key=6cb4001b-cb25-4682-baf3-61a64918d89b");
        ArrayList<DetailedWeatherForecastSample> samples = new ArrayList<DetailedWeatherForecastSample>();
        try {
            URL url = new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/" + location.getApiId() + "?res=3hourly&key=" + apiKey);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            try {
                Reader reader = new InputStreamReader(connection.getInputStream());
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(reader);
                JSONObject siteRep = (JSONObject) obj.get("SiteRep");
                JSONObject dv = (JSONObject) siteRep.get("DV");
                JSONObject locationForecast = (JSONObject) dv.get("Location");
                JSONArray data = (JSONArray) locationForecast.get("Period");
                for(Object obji: data) {
                    JSONObject day = (JSONObject) obji;
                    // It would appear that each object in period contains the samples for a given
                    // day. At 3 hour resolution, this is usually 8, but samples in the past are omitted,
                    // hence the first day probably has fewer samples.
                    // The samples are in 3 hourly intervals starting from midnight, from which we can
                    // deduce the time of the first sample.
                    // This is the date of the day
                    String dayDate = (String) day.get("value");
                    String[] dateSplit = dayDate.split("-");

                    // Get rid of the trailing 'Z' (incidentally, I think this just indicates UTC+0
                    dateSplit[2] = dateSplit[2].substring(0, dateSplit[2].length() - 1);

                    // TODO: Error catching?
                    int year = Integer.parseInt(dateSplit[0]);
                    int month = Integer.parseInt(dateSplit[1]);
                    int dayOfMonth = Integer.parseInt(dateSplit[2]);

                    // Don't know what 'Rep' is supposed to stand for but this appears to be the weather
                    // samples for this day
                    JSONArray dailySamples = (JSONArray) day.get("Rep");
                    // It is normal for there to be a few missing samples at the start of the forecast
                    // for today as samples from the past are omitted
                    final int missingSamples = expectedDailySamples - dailySamples.size();
                    for(int i = 0; i < dailySamples.size(); ++i) {
                        JSONObject jsonSample = (JSONObject) dailySamples.get(i);
                        String weatherTypeString   = (String) jsonSample.get("W");
                        String windSpeedString     = (String) jsonSample.get("S");
                        String windDirectionString = (String) jsonSample.get("D");
                        String uvIndexString = (String) jsonSample.get("U");
                        String precipitationProbabilityString = (String) jsonSample.get("Pp");
                        String temperatureString = (String) jsonSample.get("T");

                        DetailedWeatherForecastSample thisSample = new DetailedWeatherForecastSample();
                        thisSample.timeStamp = Calendar.getInstance();
                        thisSample.timeStamp.set(Calendar.YEAR, year);
                        thisSample.timeStamp.set(Calendar.MONTH, month - 1);
                        thisSample.timeStamp.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        thisSample.timeStamp.set(Calendar.HOUR_OF_DAY,
                                sampleHourlyResolution * i + missingSamples * sampleHourlyResolution);
                        thisSample.timeStamp.set(Calendar.MINUTE, 0);
                        thisSample.timeStamp.set(Calendar.SECOND, 0);

                        thisSample.weatherType = WeatherType.fromMetOfficeApiCode(weatherTypeString);
                        thisSample.windSpeed_mph = Float.parseFloat(windSpeedString);
                        thisSample.windDirection_degrees = metOfficeCompassCodeToDegrees(windDirectionString);
                        thisSample.uvIndex = Integer.parseInt(uvIndexString);
                        thisSample.temperature_celsius = Float.parseFloat(temperatureString);
                        thisSample.precipitationProbability = Float.parseFloat(precipitationProbabilityString);
                        thisSample.location = location;
                        samples.add(thisSample);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return samples;
    }


    @Override
    public ArrayList<DetailedWeatherForecastSample> getDailyForecast(Location location) {
        // TODO: Please implement
        // URL detailData = new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/352409?res=3hourly&key=474b382b-4970-4685-a1dd-8bffd071216b");
        ArrayList<DetailedWeatherForecastSample> samples = new ArrayList<DetailedWeatherForecastSample>();
        try {
            URL url = new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/" + location.getApiId() + "?res=daily&key=" + apiKey);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            try {
                Reader reader = new InputStreamReader(connection.getInputStream());
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(reader);
                JSONObject siteRep = (JSONObject) obj.get("SiteRep");
                JSONObject dv = (JSONObject) siteRep.get("DV");
                JSONObject locationForecast = (JSONObject) dv.get("Location");
                JSONArray data = (JSONArray) locationForecast.get("Period");
                for(Object obji: data) {
                    JSONObject day = (JSONObject) obji;
                    // It would appear that each object in period contains the samples for a given
                    // day. At 3 hour resolution, this is usually 8, but samples in the past are omitted,
                    // hence the first day probably has fewer samples.
                    // The samples are in 3 hourly intervals starting from midnight, from which we can
                    // deduce the time of the first sample.
                    // This is the date of the day
                    String dayDate = (String) day.get("value");
                    String[] dateSplit = dayDate.split("-");

                    // Get rid of the trailing 'Z' (incidentally, I think this just indicates UTC+0)
                    dateSplit[2] = dateSplit[2].substring(0, dateSplit[2].length() - 1);

                    // TODO: Error catching?
                    int year = Integer.parseInt(dateSplit[0]);
                    int month = Integer.parseInt(dateSplit[1]);
                    System.out.println(dateSplit[1] + " parsed to " + String.valueOf(month));
                    int dayOfMonth = Integer.parseInt(dateSplit[2]);

                    // Don't know what 'Rep' is supposed to stand for but this appears to be the weather
                    // samples for this day
                    JSONArray dailySamples = (JSONArray) day.get("Rep");
                    JSONObject jsonSample = (JSONObject) dailySamples.get(0);
                    String weatherTypeString   = (String) jsonSample.get("W");
                    String windSpeedString     = (String) jsonSample.get("S");
                    String windDirectionString = (String) jsonSample.get("D");
                    String uvIndexString = (String) jsonSample.get("U");
                    String precipitationProbabilityString = (String) jsonSample.get("PPd");
                    // This is the daily maximum temperature
                    String temperatureString = (String) jsonSample.get("Dm");

                    DetailedWeatherForecastSample thisSample = new DetailedWeatherForecastSample();
                    thisSample.timeStamp = Calendar.getInstance();
                    thisSample.timeStamp.set(Calendar.YEAR, year);
                    thisSample.timeStamp.set(Calendar.MONTH, month - 1);
                    thisSample.timeStamp.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    // This is a sample for a whole day so the h:m:s are meaningless here
                    thisSample.timeStamp.set(Calendar.HOUR_OF_DAY, 0);
                    thisSample.timeStamp.set(Calendar.MINUTE, 0);
                    thisSample.timeStamp.set(Calendar.SECOND, 0);

                    thisSample.weatherType = WeatherType.fromMetOfficeApiCode(weatherTypeString);
                    thisSample.windSpeed_mph = Float.parseFloat(windSpeedString);
                    thisSample.windDirection_degrees = metOfficeCompassCodeToDegrees(windDirectionString);
                    thisSample.uvIndex = Integer.parseInt(uvIndexString);
                    thisSample.temperature_celsius = Float.parseFloat(temperatureString);
                    thisSample.precipitationProbability = Float.parseFloat(precipitationProbabilityString);
                    thisSample.location = location;
                    samples.add(thisSample);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return samples;
    }



    /**
     * @see WeatherForecastService
     * @return
     */
    @Override
    public TextualForecast getLongTermForecast() {
        // TODO: Please implement
        // API link
        // URL longterm = new URL("http://datapoint.metoffice.gov.uk/public/data/txt/wxfcs/regionalforecast/json/500?key=6cb4001b-cb25-4682-baf3-61a64918d89b");
        TextualForecast textualForecast = new TextualForecast();
            try {
                URL texturalUrl = new URL("http://datapoint.metoffice.gov.uk/public/data/txt/wxfcs/regionalforecast/json/500?key=" + apiKey);
                HttpURLConnection connection = (HttpURLConnection) texturalUrl.openConnection();
                try {
                    Reader reader = new InputStreamReader(connection.getInputStream());
                    JSONParser parser = new JSONParser();
                    JSONObject obj = (JSONObject) parser.parse(reader);

                    JSONObject RegionalFcst = (JSONObject) obj.get("RegionalFcst");
                    JSONObject FcstPeriods = (JSONObject) RegionalFcst.get("FcstPeriods");
                    JSONArray textperiod = (JSONArray) FcstPeriods.get("Period");

                  /*  for(Object o:textperiod){
                        JSONObject jobj = (JSONObject) o;
                        textualForecast.period = jobj.get("id").toString();
                        textualForecast.text =  jobj.get("Paragraph").toString();
                        return textualForecast;
                    }  */
                    for (int i = 0; i < textperiod.size(); i++) {
                        JSONArray paragraph = (JSONArray) ((JSONObject) textperiod.get(i)).get("Paragraph");
                        JSONObject jobj = (JSONObject) paragraph.get(i);
                        JSONObject id = (JSONObject) textperiod.get(i);
                        textualForecast.period = "LongTerm weather forecast for "+id.get("id").toString();
                        textualForecast.text = jobj.get("$").toString();
                       // System.out.println(textualForecast);
                        return textualForecast;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    connection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        return  textualForecast;

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
                    System.err.println("ERROR. Some kind of network problem in MetOfficeWeatherForecastService::getAvailableLocations");
                    e.printStackTrace();
                } finally {
                    connection.disconnect();
                }
            } catch (Exception e) {
                System.err.println("ERROR. Outer. Some kind of network problem in MetOfficeWeatherForecastService::getAvailableLocations");
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
     /*   for (Location location : ws.getAvailableLocations()) {
            System.out.println(location);
        }*/
      /*  for(SimpleWeatherForecastSample forecast: ws.getSimpleForecast()) {
            System.out.println(forecast);
        }*/

        Location location = ws.getLocationByName("London");
        System.out.println("Location by name: " + location);

//      ArrayList<DetailedWeatherForecastSample> samples = ws.getDailyForecast(ws.getLocationByName("Liverpool"));
//        for(DetailedWeatherForecastSample sample: samples) {
//            System.out.println(sample);
//        }
//
//        TextualForecast textualForecast = ws.getLongTermForecast();
//           System.out.println(textualForecast);
    }


    private ArrayList<Location> locationCache = null;
}
