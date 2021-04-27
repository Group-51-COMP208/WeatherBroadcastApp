package com.example.lib;

public class PlaceholderLocationService implements LocationService {
    PlaceholderLocationService() {
        setSelectedLocation(getFavouriteLocation());
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

    private Location selectedLocation;
}
