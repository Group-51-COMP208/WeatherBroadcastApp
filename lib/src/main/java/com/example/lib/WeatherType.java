package com.example.lib;

// These categories were taken from the Met Office docs:
// https://www.metoffice.gov.uk/binaries/content/assets/metofficegovuk/pdf/data/datapoint_api_reference.pdf
// (see "significant weather as a code").
// These are are probably broadly representative of the
// categories one would expect from any forecast service.
// Where the Met Office makes a distinction between night and
// day for each weather type, I generally omitted this as it
// seemed redundant if we have a time stamp for each prediction,
// but we may need to change this if it becomes apparent that there
// is a good reason the Met Office makes this distinction.
public enum WeatherType {
  /*  ClearNight,
    SunnyDay,
    PartlyCloudyNight,
    PartlyCloudyDay,
    Mist,
    Fog,
    Cloudy,
    Overcast,
    // Rain shower v.s Rain:
    // https://www.metoffice.gov.uk/weather/learn-about/weather/types-of-weather/rain/rain-and-showers
    LightRainShowerNight,
    LightRainShowerDay,
    Drizzle,
    LightRain,
    HeavyRainShowerNight,
    HeavyRainShowerDay,
    HeavyRain,
    Sleet,
    SleetShowerNight,
    SleetShowerDay,
    HailShower,
    Hail,
    LightSnowShowerNight,
    LightSnowShowerDay,
    LightSnow,
    HeavySnowShowerNight,
    HeavySnowShowerDay,
    HeavySnow,
    ThunderShowerNight,
    ThunderShowerDay,
    Thunder,
*/
    NOT_AVAILABLE("NA"),
    CLEAR_NIGHT("0"),
    SUNNY_DAY("1"),
    PARTLY_CLOUDY_NIGHT ("2"),
    PARTLY_CLOUDY_DAY ("3"),
    NOT_USED ("4"),
    MIST ("5"),
    FOG ("6"),
    CLOUDY ("7"),
    OVERCAST ("8"),
    LIGHT_RAIN_SHOWER_NIGHT ("9"),
    LIGHT_RAIN_SHOWER_DAY ("10"),
    DRIZZLE ("11"),
    LIGHT_RAIN ("12"),
    HEAVY_RAIN_SHOWER_NIGHT ("13"),
    HEAVY_RAIN_SHOWER_DAY ("14"),
    HEAVY_RAIN ("15"),
    SLEET_SHOWER_NIGHT ("16"),
    SLEET_SHOWER_DAY ("17"),
    SLEET ("18"),
    HAIL_SHOWER_NIGHT ("19"),
    HAIL_SHOWER_DAY ("20"),
    HAIL ("21"),
    LIGHT_SNOW_SHOWER_NIGHT ("22"),
    LIGHT_SNOW_SHOWER_DAY ("23"),
    LIGHT_SNOW ("24"),
    HEAVY_SNOW_SHOWER_NIGHT ("25"),
    HEAVY_SNOW_SHOWER_DAY ("26"),
    HEAVY_SNOW ("27"),
    THUNDER_SHOWER_NIGHT ("28"),
    THUNDER_SHOWER_DAY ("29"),
    THUNDER ("30"),;

    private String weatherCode;
    private WeatherType(final String code){
        this.weatherCode = code;
    }

    public String getCode() {
        return weatherCode;
    }
}
