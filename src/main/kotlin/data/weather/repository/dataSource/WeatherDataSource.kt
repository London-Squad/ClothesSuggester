package data.weather.repository.dataSource

import data.model.ApiResult
import data.model.WeatherData

interface WeatherDataSource {
    suspend fun fetchTodayWeatherByLocation(lat: Double, long: Double): ApiResult<WeatherData>
}