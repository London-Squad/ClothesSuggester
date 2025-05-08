package data.weather.repository

import logic.dataSource.WeatherDataSource
import logic.entity.Temperature
import logic.repository.WeatherRepository
import logic.result.LogicResponse

class WeatherRepositoryImpl(
    private val weatherDataSource: WeatherDataSource
) : WeatherRepository {
    override suspend fun fetchTodayWeatherByLocation(lat: Double, long: Double): LogicResponse<Temperature> {
        return weatherDataSource.fetchTodayWeatherByLocation(lat, long)
    }
}