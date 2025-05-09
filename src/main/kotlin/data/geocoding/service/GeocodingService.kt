package data.geocoding.service

import data.model.ApiResult
import data.model.LocationData

interface GeocodingService {
    suspend fun getCoordinates(city: String): ApiResult<List<LocationData>>
}