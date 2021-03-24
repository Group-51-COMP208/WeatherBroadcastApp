package com.example.lib;

public class Location {
    Location(String displayName, String apiId,
             float latitude, float longitude)
    {
        this.displayName = displayName;
        this.apiId = apiId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getDisplayName() {
        return displayName;
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
    final private String displayName;
    final private String apiId;
    final private float latitude;
    final private float longitude;
}
