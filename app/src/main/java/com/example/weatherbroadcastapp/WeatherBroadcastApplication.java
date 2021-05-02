package com.example.weatherbroadcastapp;

import android.app.Application;
import android.content.Context;

import com.example.lib.ApiException;

/*
 * The only purpose of this class is to provide global access
 * to context (for the benefit of utility and service classes)
 */
public class WeatherBroadcastApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static void handleApiException(ApiException e) {
        // TODO
        System.err.println("Drat: " + e);
        System.exit(-1);
    }

    private static Context appContext;
}
