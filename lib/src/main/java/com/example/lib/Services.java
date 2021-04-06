package com.example.lib;

/*
* A Singleton class, the purpose of which is to make available
* services, including the WeatherForecastService to the rest of the
* application while hiding the specific implementation details.
* You access the Services with 'Services.get()'
 */
public class Services {
    public WeatherForecastService getWeatherForecastService() {
        return weatherForecastService;
    }

    public FavouriteLocationService getFavouriteLocationService() {
        return favouriteLocationService;
    }

    public static Services get() {
        if(instance == null) {
            // 'synchronized' makes this thread-safe, just in case
            synchronized (Services.class) {
                if(instance == null) {
                    instance = new Services();
                }
            }
        }
        return instance;
    }

    private Services() {
        // Placeholder. TODO: Switch to real implementation once it is sufficiently complete
        weatherForecastService = new PlaceholderWeatherForecastService();
        // Placeholder. TODO: Switch to real implementation once it is sufficiently complete
        favouriteLocationService = new PlaceholderFavouriteLocationService();
    }

    private static Services instance = null;

    WeatherForecastService weatherForecastService;
    FavouriteLocationService favouriteLocationService;
}
