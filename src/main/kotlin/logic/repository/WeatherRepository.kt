package logic.repository

import logic.entity.Temperature
import logic.result.LogicResponse

interface WeatherRepository {
    suspend fun fetchTodayWeatherByLocation(lat: Double, long: Double): LogicResponse<Temperature>
}