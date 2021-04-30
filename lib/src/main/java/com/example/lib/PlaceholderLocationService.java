package com.example.lib;

import java.util.ArrayList;

public class PlaceholderLocationService implements LocationService {
    PlaceholderLocationService() {
        setSelectedLocation(new Location("Liverpool", "?", 53.4f, -2.99f));
        favLocs.add(new String("Liverpool"));
        favLocs.add(new String("Manchester"));
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
    public void registerLocationUsage(String location) {
        System.out.println("TODO: implement registerLocationUsage for LocationService");
    }

    @Override
    public Iterable<String> getFavouriteLocations() {
        return favLocs;
    }

    private Location selectedLocation;
    private ArrayList<String> favLocs = new ArrayList<String>();
}
