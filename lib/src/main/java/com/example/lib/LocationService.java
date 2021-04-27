package com.example.lib;

public interface LocationService {
    Location getFavouriteLocation();
    void saveFavouriteLocation(Location location);
    Location getSelectedLocation();
    void setSelectedLocation(Location location);
}
