package data.geocoding.repository

import logic.dataSource.LocationDataSource
import logic.entity.Location
import logic.repository.LocationRepository
import logic.result.LogicResponse

class LocationRepositoryImpl  (
    private val locationDataSource: LocationDataSource
): LocationRepository {
    override suspend fun fetchLocation(city: String): LogicResponse<Location> {
        return locationDataSource.fetchLocation(city)
    }

}