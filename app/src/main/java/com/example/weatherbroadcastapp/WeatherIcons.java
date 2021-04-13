package com.example.weatherbroadcastapp;

import com.example.lib.WeatherType;

public class WeatherIcons {
    public static int getIconId(WeatherType weatherType) {
        switch(weatherType) {
            case ClearNight:
                return R.drawable.icon_moon;
            case SunnyDay:
                return R.drawable.icon_sun;
            case PartlyCloudyNight:
                return R.drawable.icon_cloud_moon;
            case PartlyCloudyDay:
                return R.drawable.icon_cloud_sun;
            case Mist:
            case Fog:
                return R.drawable.icon_fog;
            case Cloudy:
                return R.drawable.icon_cloud;
            case Overcast:
                return R.drawable.icon_cloud;
            case LightRainShowerNight:
                return R.drawable.icon_rain_moon;
            case LightRainShowerDay:
                return R.drawable.icon_rain_sun;
            case Drizzle:
            case LightRain:
            case HeavyRainShowerNight:
            case HeavyRainShowerDay:
            case HeavyRain:
                return R.drawable.icon_rain;
            case Sleet:
            case SleetShowerNight:
            case SleetShowerDay:
                return R.drawable.icon_sleet;
            case HailShower:
            case Hail:
                return R.drawable.icon_hail;
            case LightSnowShowerNight:
            case LightSnowShowerDay:
            case LightSnow:
            case HeavySnowShowerNight:
            case HeavySnowShowerDay:
            case HeavySnow:
                return R.drawable.icon_snow;
            case ThunderShowerNight:
            case ThunderShowerDay:
            case Thunder:
                return R.drawable.icon_thunder;
        }
        return R.drawable.icon_cloud;
    }
}
