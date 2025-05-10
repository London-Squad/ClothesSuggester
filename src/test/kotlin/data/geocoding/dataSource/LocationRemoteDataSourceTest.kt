package data.geocoding.dataSource

import com.google.common.truth.Truth.assertThat
import data.model.ApiResult
import data.model.LocationData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
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

        assertThat(result).isInstanceOf(ApiResult.Success::class.java)
        val location = (result as ApiResult.Success).data.first()
        assertThat(location.lat).isEqualTo(30.0444)
        assertThat(location.lon).isEqualTo(31.2357)
    }


}