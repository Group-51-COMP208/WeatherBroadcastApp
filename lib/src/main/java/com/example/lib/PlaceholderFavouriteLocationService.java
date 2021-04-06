package com.example.lib;

public class PlaceholderFavouriteLocationService implements FavouriteLocationService {
    @Override
    public Location getFavouriteLocation() {
        return new Location("Liverpool", "?", 53.4f, -2.99f);
    }

    @Override
    public void saveFavouriteLocation(Location location) {
        System.out.println("Sorry, PlaceholderFavouriteLocationService is not implemented. " +
                "Switch Services to use a proper FavouriteLocationService when available.");
    }
}
