package data.weather.mapper

import data.model.WeatherData
import logic.entity.Temperature

fun WeatherData.toTemperature(): Temperature {
    return Temperature(currentWeather!!.temperature, currentWeatherUnits!!.temperatureUnit)
}