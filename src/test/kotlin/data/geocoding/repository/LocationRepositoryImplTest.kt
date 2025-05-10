package data.geocoding.repository

import com.google.common.truth.Truth.assertThat
import data.geocoding.repository.dataSource.LocationDataSource
import data.model.ApiResult
import data.model.LocationData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.repository.LocationRepository
import logic.result.LogicResponse
import org.junit.jupiter.api.Test


class LocationRepositoryImplTest {
    private val mockDataSource = mockk<LocationDataSource>()
    private val repository: LocationRepository = LocationRepositoryImpl(mockDataSource)

    @Test
    fun fetchLocation_shouldReturnSuccess_whenDataSourceReturnsSuccess() = runTest {
        val city = "Paris"
        val location = LocationData(lat = 48.8566, lon = 2.3522)
        coEvery { mockDataSource.fetchLocation(city) } returns ApiResult.Success(listOf(location))

        val result = repository.fetchLocation(city)

        assertThat(result).isInstanceOf(LogicResponse.Success::class.java)
        val data = (result as LogicResponse.Success).data
        assertThat(data.latitude).isEqualTo(48.8566)
        assertThat(data.longitude).isEqualTo(2.3522)
    }


}