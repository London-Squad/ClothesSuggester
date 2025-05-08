package logic.dataSource

import logic.entity.Temperature
import logic.result.LogicResponse

interface WeatherDataSource {
    suspend fun fetchTodayWeatherByLocation(lat: Double, long: Double): LogicResponse<Temperature>
}