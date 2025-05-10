package data.weather.repository

import com.google.common.truth.Truth.assertThat
import data.model.ApiResult
import data.model.CurrentWeather
import data.model.CurrentWeatherUnits
import data.model.WeatherData
import data.weather.mapper.toTemperature
import data.weather.repository.dataSource.WeatherDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.repository.WeatherRepository
import logic.result.LogicResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WeatherRepositoryImplTest {
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var weatherDataSource: WeatherDataSource

    @BeforeEach
    fun setUp() {
        weatherDataSource = mockk(relaxed = true)
        weatherRepository = WeatherRepositoryImpl(weatherDataSource)
    }

    @Test
    fun `when call fetchTodayWeatherByLocation then return temperature`() = runTest {
        val expectedResult = ApiResult.Success(
            WeatherData(
                currentWeather = CurrentWeather(temperature = 20.0),
                currentWeatherUnits = CurrentWeatherUnits("o")
            )
        )
        coEvery { weatherDataSource.fetchTodayWeatherByLocation(any(), any()) } returns expectedResult

        val actualResult = weatherRepository.fetchTodayWeatherByLocation(20.0, 20.0) as LogicResponse.Success

        assertThat(actualResult.data).isEqualTo(expectedResult.data.toTemperature())
    }

    @Test
    fun `when call fetchTodayWeatherByLocation with invalid location then return temperature`() = runTest {
        val expectedResult = ApiResult.Error(true, "Test")
        coEvery { weatherDataSource.fetchTodayWeatherByLocation(any(), any()) } returns expectedResult

        val actualResult = weatherRepository.fetchTodayWeatherByLocation(2000.0, 2000.0) as LogicResponse.Error

        assertThat(actualResult.errorMessage).isEqualTo(expectedResult.reason)
    }
}