package data.weather.repository

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.dataSource.WeatherDataSource
import logic.entity.Temperature
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
        val expectedResult = LogicResponse.Success(Temperature(20.0, "o"))
        coEvery { weatherDataSource.fetchTodayWeatherByLocation(any(), any()) } returns expectedResult

        val actualResult = weatherRepository.fetchTodayWeatherByLocation(20.0, 20.0)

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `when call fetchTodayWeatherByLocation with invalid location then return temperature`() = runTest {
        val expectedResult = LogicResponse.Error("Test")
        coEvery { weatherDataSource.fetchTodayWeatherByLocation(any(), any()) } returns expectedResult

        val actualResult = weatherRepository.fetchTodayWeatherByLocation(2000.0, 2000.0)

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}