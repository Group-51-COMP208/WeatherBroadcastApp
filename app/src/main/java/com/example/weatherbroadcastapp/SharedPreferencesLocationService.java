package com.example.weatherbroadcastapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.lib.Location;
import com.example.lib.LocationService;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class SharedPreferencesLocationService implements LocationService {
    SharedPreferencesLocationService() {
        favouriteLocations = WeatherBroadcastApplication.getAppContext().getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        recentLocations = parseFavouriteLocations(favouriteLocations.getString(RECENT_LOCATIONS_KEY, ""));
    }

    // Stored as a '|' separated list of strings
    private static Queue<String> parseFavouriteLocations(String s) {
        return new LinkedList<String>(Arrays.asList(s.split("\\|")));
    }

    private static String serializeFavouriteLocations(Iterable<String> locations) {
        StringBuilder s = new StringBuilder();
        for(String l: locations) {
            s.append(l).append("|");
        }
        if(s.length() > 0) {
            // Remove the trailing '|'
            s.deleteCharAt(s.length() - 1);
        }
        return s.toString();
    }


    @Override
    public Location getSelectedLocation() {
        return selectedLocation;
    }

    @Override
    public void setSelectedLocation(Location location) {
        selectedLocation = location;
    }

    @Override
    public void registerLocationUsage(String displayName) {
        if(recentLocations.contains(displayName)) {
            return;
        }

        if(recentLocations.size() >= MAX_FAVOURITES) {
            recentLocations.remove();
        }
        recentLocations.add(displayName);
        SharedPreferences.Editor e = favouriteLocations.edit();
        e.putString(RECENT_LOCATIONS_KEY, serializeFavouriteLocations(recentLocations));
        e.apply();
    }

    @Override
    public Iterable<String> getFavouriteLocations() {
        return recentLocations;
    }

    private static final int MAX_FAVOURITES = 4;
    private static final String SHARED_PREFERENCES_FILE = "favourite_locations";
    private static final String RECENT_LOCATIONS_KEY = "recent_locations";
    private Queue<String> recentLocations;
    private Location selectedLocation = null;
    SharedPreferences favouriteLocations;
}
