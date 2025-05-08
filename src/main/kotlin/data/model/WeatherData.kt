package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherData(
    val latitude: Double? = null,
    val longitude: Double? = null,
    @SerialName("current_weather_units")
    val currentWeatherUnits: CurrentWeatherUnits? = null,
    @SerialName("current_weather")
    val currentWeather: CurrentWeather? = null,
    override val error: Boolean? = null,
    override val reason: String? = null
): DataModel

@Serializable
data class CurrentWeatherUnits(
    @SerialName("temperature")
    val temperatureUnit: String,
)

@Serializable
data class CurrentWeather(
    val temperature: Double,
)