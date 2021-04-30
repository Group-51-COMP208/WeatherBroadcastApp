package com.example.lib;

import java.util.ArrayList;

public interface LocationService {
    Location getSelectedLocation();
    void setSelectedLocation(Location location);
    // Will be saved in a list of recently used locations
    public void registerLocationUsage(String displayName);
    /*
    * @return List of recently used Locations, by their
    * display names.
    */
    public Iterable<String> getFavouriteLocations();
}
