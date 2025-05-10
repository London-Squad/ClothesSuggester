package data.weather.repository

import data.model.ApiResult
import data.model.WeatherData
import data.weather.mapper.toTemperature
import data.weather.repository.dataSource.WeatherDataSource
import logic.entity.Temperature
import logic.repository.WeatherRepository
import logic.result.LogicResponse

class WeatherRepositoryImpl(
    private val weatherDataSource: WeatherDataSource
) : WeatherRepository {
    override suspend fun fetchTodayWeatherByLocation(lat: Double, long: Double): LogicResponse<Temperature> {
        return when(val response: ApiResult<WeatherData> = weatherDataSource.fetchTodayWeatherByLocation(lat, long)) {
            is ApiResult.Success<WeatherData> -> LogicResponse.Success(response.data.toTemperature())
            is ApiResult.Error -> LogicResponse.Error(response.reason)
        }
    }
}