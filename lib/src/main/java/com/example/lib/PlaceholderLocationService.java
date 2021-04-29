package com.example.lib;

import java.util.ArrayList;

public class PlaceholderLocationService implements LocationService {
    PlaceholderLocationService() {
        setSelectedLocation(getFavouriteLocation());
        favLocs.add(new Location("Liverpool", "?", 53.4f, -2.99f));
        favLocs.add(new Location("Manchester", "?", 53.4f, -1.99f));
    }

    @Override
    public Location getFavouriteLocation() {
        return new Location("Liverpool", "?", 53.4f, -2.99f);
    }

    @Override
    public void saveFavouriteLocation(Location location) {
        System.out.println("Sorry, PlaceholderFavouriteLocationService is not implemented. " +
                "Switch Services to use a proper FavouriteLocationService when available.");
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
    public void registerLocationUsage(Location location) {
        System.out.println("TODO: implement registerLocationUsage for LocationService");
    }

    @Override
    public ArrayList<Location> getFavouriteLocations() {
        return favLocs;
    }

    private Location selectedLocation;
    private ArrayList<Location> favLocs = new ArrayList<Location>();
}
