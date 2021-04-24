package com.example.lib;

public interface FavouriteLocationService {
    Location getFavouriteLocation();
    void saveFavouriteLocation(Location location);
    Location getSelectedLocation();
    void setSelectedLocation(Location location);
}
