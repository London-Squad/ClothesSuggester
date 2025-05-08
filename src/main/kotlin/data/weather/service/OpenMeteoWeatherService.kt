package data.weather.service

import data.api.ApiResponseHandler
import data.api.BaseUrl
import data.model.ApiResult
import data.model.WeatherData
import io.ktor.client.*
import io.ktor.client.request.*

class OpenMeteoWeatherService(
    private val httpClient: HttpClient,
    private val apiResponseHandler: ApiResponseHandler
) : WeatherService {
    override suspend fun fetchTodayWeatherByLocation(lat: Double, long: Double): ApiResult<WeatherData> {
        val url = BaseUrl.WEATHER_URL + TODAY_WEATHER_ENDPOINT
        return apiResponseHandler.safeApiCall<WeatherData> {
            httpClient.get(url) {
                parameter(LATITUDE_PARAM, lat)
                parameter(LONGITUDE_PARAM, long)
                parameter(CURRENT_WEATHER_PARAM, true)
            }
        }
    }

    private companion object {
        const val TODAY_WEATHER_ENDPOINT = "forecast"
        const val LATITUDE_PARAM = "latitude"
        const val LONGITUDE_PARAM = "longitude"
        const val CURRENT_WEATHER_PARAM = "current_weather"
    }
}