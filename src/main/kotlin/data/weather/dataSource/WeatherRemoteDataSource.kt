package data.weather.dataSource

import data.model.ApiResult
import data.model.WeatherData
import data.weather.repository.dataSource.WeatherDataSource
import data.weather.service.WeatherService

class WeatherRemoteDataSource(
    private val weatherService: WeatherService
): WeatherDataSource {
    override suspend fun fetchTodayWeatherByLocation(lat: Double, long: Double): ApiResult<WeatherData> {
        return weatherService.fetchTodayWeatherByLocation(lat, long)
    }
}