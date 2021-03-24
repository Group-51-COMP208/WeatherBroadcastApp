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
    ClearNight,
    SunnyDay,
    PartlyCloudy,
    Mist,
    Fog,
    Cloudy,
    Overcast,
    // Rain shower v.s Rain:
    // https://www.metoffice.gov.uk/weather/learn-about/weather/types-of-weather/rain/rain-and-showers
    LightRainShower,
    Drizzle,
    LightRain,
    HeavyRainShower,
    HeavyRain,
    SleetShower,
    Sleet,
    HailShower,
    Hail,
    LightSnowShower,
    LightSnow,
    HeavySnowShower,
    HeavySnow,
    ThunderShower,
    Thunder
}
