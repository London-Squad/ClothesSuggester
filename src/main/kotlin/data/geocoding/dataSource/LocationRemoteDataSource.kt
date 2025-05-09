package data.geocoding.dataSource

import data.geocoding.service.GeocodingService
import data.geocoding.mapper.toLocation
import data.model.ApiResult
import data.model.LocationData
import logic.dataSource.LocationDataSource
import logic.entity.Location
import logic.result.LogicResponse

class LocationRemoteDataSource (
    private val geocodingService: GeocodingService
    ): LocationDataSource {
        override suspend fun fetchLocation(city: String): LogicResponse<Location> {
            return when(val response: ApiResult<List<LocationData>>  = geocodingService.getCoordinates(city)) {
                is ApiResult.Success<List<LocationData>> -> LogicResponse.Success(response.data.first().toLocation())
                is ApiResult.Error -> LogicResponse.Error(response.reason)
            }
        }
    }
