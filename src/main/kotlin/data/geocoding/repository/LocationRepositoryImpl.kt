package data.geocoding.repository

import data.geocoding.mapper.toLocation
import data.geocoding.repository.dataSource.LocationDataSource
import data.model.ApiResult
import data.model.LocationData
import logic.entity.Location
import logic.repository.LocationRepository
import logic.result.LogicResponse

class LocationRepositoryImpl(
    private val locationDataSource: LocationDataSource
) : LocationRepository {
    override suspend fun fetchLocation(city: String): LogicResponse<Location> {
        return when (val response: ApiResult<List<LocationData>> = locationDataSource.fetchLocation(city)) {
            is ApiResult.Success<List<LocationData>> -> LogicResponse.Success(response.data.first().toLocation())
            is ApiResult.Error -> LogicResponse.Error(response.reason)
        }
    }

}