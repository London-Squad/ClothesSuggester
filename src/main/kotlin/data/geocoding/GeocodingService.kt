package data.geocoding

import data.model.Location

import data.model.ApiResult

interface GeocodingService {
    suspend fun getCoordinates(city: String): ApiResult<Location>
}
