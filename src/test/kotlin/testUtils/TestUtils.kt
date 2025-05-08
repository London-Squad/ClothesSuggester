package testUtils

import data.model.CurrentWeather
import data.model.CurrentWeatherUnits
import data.model.WeatherData

fun createWeatherData(
    latitude: Double? = null,
    longitude: Double? = null,
    currentWeatherUnits: CurrentWeatherUnits? = null,
    currentWeather: CurrentWeather? = null,
    error: Boolean? = null,
    reason: String? = null
) = WeatherData(latitude, longitude, currentWeatherUnits, currentWeather, error, reason)