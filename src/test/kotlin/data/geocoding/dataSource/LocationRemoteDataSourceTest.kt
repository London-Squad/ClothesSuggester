package data.geocoding.dataSource

import org.junit.jupiter.api.Assertions.*
import com.google.common.truth.Truth.assertThat
import data.geocoding.dataSource.LocationRemoteDataSource
import data.model.ApiResult
import data.model.LocationData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.entity.Location
import logic.result.LogicResponse
import org.junit.jupiter.api.Test

class LocationRemoteDataSourceTest {

    private val fakeService = mockk<data.geocoding.service.GeocodingService>()
    private val dataSource = LocationRemoteDataSource(fakeService)

    @Test
    fun fetchLocation_shouldReturnSuccess_whenServiceReturnsValidLocation() = runTest {
        val city = "Cairo"
        val locationData = LocationData(lat = 30.0444, lon = 31.2357)
        coEvery { fakeService.getCoordinates(city) } returns ApiResult.Success(listOf(locationData))

        val result = dataSource.fetchLocation(city)

        assertThat(result).isInstanceOf(LogicResponse.Success::class.java)
        val location = (result as LogicResponse.Success).data
        assertThat(location.lat).isEqualTo(30.0444)
        assertThat(location.lon).isEqualTo(31.2357)
    }


}