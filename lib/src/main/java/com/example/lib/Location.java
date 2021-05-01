package com.example.lib;

import java.util.ArrayList;

public class Location {
    Location(String names, String apiId,
             float latitude, float longitude)
    {
        this.name = names;
        this.apiId = apiId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "{" + name + ", " + apiId + ", "
                + String.valueOf(latitude) + ", " + String.valueOf(longitude)
                + "}";
    }

    public String getDisplayName() {
        return name;
    }

    public String getApiId() {
        return apiId;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }


    // I don't see any reason that any of these should change
    // for a given location, hence final.
    final private String name;
    final private String apiId;
    final private float latitude;
    final private float longitude;
}
