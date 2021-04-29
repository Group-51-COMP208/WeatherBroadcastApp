package com.example.lib;

import java.util.ArrayList;

public interface LocationService {
    Location getFavouriteLocation();
    void saveFavouriteLocation(Location location);
    Location getSelectedLocation();
    void setSelectedLocation(Location location);
    // Will be saved in a list of recently used locations
    public void registerLocationUsage(Location location);
    // Returns the list of recently used locations
    public ArrayList<Location> getFavouriteLocations();
}
