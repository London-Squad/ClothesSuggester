package data.geocoding.dataSource

import data.geocoding.repository.dataSource.LocationDataSource
import data.geocoding.service.GeocodingService
import data.model.ApiResult
import data.model.LocationData

class LocationRemoteDataSource (
    private val geocodingService: GeocodingService
    ): LocationDataSource {
        override suspend fun fetchLocation(city: String): ApiResult<List<LocationData>> {
            return geocodingService.getCoordinates(city)
        }
    }