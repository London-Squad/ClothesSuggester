package data.geocoding.repository

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.dataSource.LocationDataSource
import logic.entity.Location
import logic.repository.LocationRepository
import logic.result.LogicResponse
import org.junit.jupiter.api.Test


class LocationRepositoryImplTest {
    private val mockDataSource = mockk<LocationDataSource>()
    private val repository: LocationRepository = LocationRepositoryImpl(mockDataSource)

    @Test
    fun fetchLocation_shouldReturnSuccess_whenDataSourceReturnsSuccess() = runTest {
        val city = "Paris"
        val location = Location(lat = 48.8566, lon = 2.3522)
        coEvery { mockDataSource.fetchLocation(city) } returns LogicResponse.Success(location)

        val result = repository.fetchLocation(city)

        assertThat(result).isInstanceOf(LogicResponse.Success::class.java)
        val data = (result as LogicResponse.Success).data
        assertThat(data.lat).isEqualTo(48.8566)
        assertThat(data.lon).isEqualTo(2.3522)
    }


}