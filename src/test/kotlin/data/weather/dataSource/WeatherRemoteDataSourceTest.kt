package data.weather.dataSource

import com.google.common.truth.Truth.assertThat
import data.model.ApiResult
import data.model.CurrentWeather
import data.model.CurrentWeatherUnits
import data.weather.service.WeatherService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.dataSource.WeatherDataSource
import logic.entity.Temperature
import logic.result.LogicResponse
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import testUtils.createWeatherData

class WeatherRemoteDataSourceTest {
    private lateinit var weatherDataSource: WeatherDataSource
    private lateinit var weatherService: WeatherService

    @BeforeEach
    fun setUp() {
        weatherService = mockk(relaxed = true)
        weatherDataSource = WeatherRemoteDataSource(weatherService)
    }

    @Test
    fun `when call fetchWeatherByLocation should return temperature`() = runTest {
        val response = ApiResult.Success(
            createWeatherData(
                currentWeather = CurrentWeather(20.0),
                currentWeatherUnits = CurrentWeatherUnits("o")
            )
        )
        val expectedResult = LogicResponse.Success(Temperature(
            response.data.currentWeather!!.temperature,
            response.data.currentWeatherUnits!!.temperatureUnit
        ))
        coEvery { weatherService.fetchTodayWeatherByLocation(any(), any()) } returns response

        val actualResult = weatherDataSource.fetchTodayWeatherByLocation(20.0, 20.0)

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `when call fetchWeatherByLocation with invalid location should return temperature`() = runTest {
        val response = ApiResult.Error(true,"Test")
        val expectedResult = LogicResponse.Error(response.reason)
        coEvery { weatherService.fetchTodayWeatherByLocation(any(), any()) } returns response

        val actualResult = weatherDataSource.fetchTodayWeatherByLocation(2000.0, 2000.0)

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}