package data.weather.dataSource

import data.model.ApiResult
import data.model.WeatherData
import data.weather.mapper.toTemperature
import data.weather.service.WeatherService
import logic.dataSource.WeatherDataSource
import logic.entity.Temperature
import logic.result.LogicResponse

class WeatherRemoteDataSource(
    private val weatherService: WeatherService
): WeatherDataSource {
    override suspend fun fetchTodayWeatherByLocation(lat: Double, long: Double): LogicResponse<Temperature> {
        return when(val response: ApiResult<WeatherData> = weatherService.fetchTodayWeatherByLocation(lat, long)) {
            is ApiResult.Success<WeatherData> -> LogicResponse.Success(response.data.toTemperature())
            is ApiResult.Error -> LogicResponse.Error(response.reason)
        }
    }
}