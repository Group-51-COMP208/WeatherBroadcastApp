package com.example.weatherbroadcastapp;

import com.example.lib.WeatherType;

public class WeatherIcons {
    public static int getIconId(WeatherType weatherType) {
        switch(weatherType) {
            case CLEAR_NIGHT:
                return R.drawable.icon_moon;
            case SUNNY_DAY:
                return R.drawable.icon_sun;
            case PARTLY_CLOUDY_NIGHT:
                return R.drawable.icon_cloud_moon;
            case PARTLY_CLOUDY_DAY:
                return R.drawable.icon_cloud_sun;
            case MIST:
            case FOG:
                return R.drawable.icon_fog;
            case CLOUDY:
                return R.drawable.icon_cloud;
            case OVERCAST:
                return R.drawable.icon_cloud;
            case LIGHT_RAIN_SHOWER_NIGHT:
                return R.drawable.icon_rain_moon;
            case LIGHT_RAIN_SHOWER_DAY:
                return R.drawable.icon_rain_sun;
            case DRIZZLE:
            case LIGHT_RAIN:
            case HEAVY_RAIN_SHOWER_NIGHT:
            case HEAVY_RAIN_SHOWER_DAY:
            case HEAVY_RAIN:
                return R.drawable.icon_rain;
            case SLEET:
            case SLEET_SHOWER_NIGHT:
            case SLEET_SHOWER_DAY:
                return R.drawable.icon_sleet;
            case HAIL:
                return R.drawable.icon_hail;
            case LIGHT_SNOW_SHOWER_NIGHT:
            case LIGHT_SNOW_SHOWER_DAY:
            case LIGHT_SNOW:
            case HEAVY_SNOW_SHOWER_NIGHT:
            case HEAVY_SNOW_SHOWER_DAY:
            case HEAVY_SNOW:
                return R.drawable.icon_snow;
            case THUNDER_SHOWER_NIGHT:
            case THUNDER_SHOWER_DAY:
            case THUNDER:
                return R.drawable.icon_thunder;
        }
        return R.drawable.icon_cloud;
    }
}
