package data.weather.service

import data.model.ApiResult
import data.model.WeatherData

interface WeatherService {
    suspend fun fetchTodayWeatherByLocation(lat: Double, long: Double): ApiResult<WeatherData>
}