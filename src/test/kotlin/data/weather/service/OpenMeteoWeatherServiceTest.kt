package data.weather.service

import com.google.common.truth.Truth.assertThat
import data.api.ApiResponseHandler
import data.api.HttpClientFactory
import data.model.ApiResult
import data.model.CurrentWeather
import data.model.CurrentWeatherUnits
import data.model.WeatherData
import data.weather.service.utils.DummyWeatherResponseData
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OpenMeteoWeatherServiceTest {
    private lateinit var weatherService: WeatherService
    private lateinit var apiResponseHandler: ApiResponseHandler
    private lateinit var httpClient: HttpClient
    private val dummyResponse = DummyWeatherResponseData.TODAY_WEATHER

    @BeforeEach
    fun setup() {
        apiResponseHandler = mockk(relaxed = true)
    }

    @Test
    fun `when we call fetchTodayWeatherByLocation with valid location it should return data`() = runTest {
        val lat = 52.52
        val long = 13.439999
        val weatherData = WeatherData(lat, long, CurrentWeatherUnits("°C"), CurrentWeather(7.2))
        val engine = MockEngine { request ->
            respond(
                content = dummyResponse["success"]!!,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        httpClient = HttpClientFactory().create(engine)
        weatherService = OpenMeteoWeatherService(httpClient, apiResponseHandler)

        val actualWeather = weatherService.fetchTodayWeatherByLocation(lat, long)

        assertThat(actualWeather).isEqualTo(ApiResult.Success(weatherData))
    }

    @Test
    fun `when we call fetchTodayWeatherByLocation with invalid location it should return data`() = runTest {
        val lat = 52.52
        val long = 13.439999
        val engine = MockEngine { request ->
            respond(
                content = dummyResponse["failed"]!!,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        httpClient = HttpClientFactory().create(engine)
        weatherService = OpenMeteoWeatherService(httpClient, apiResponseHandler)

        val actualWeather = weatherService.fetchTodayWeatherByLocation(lat, long)

        assertThat(actualWeather).isEqualTo(ApiResult.Error(true,"Test"))
    }
}