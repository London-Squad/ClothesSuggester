package data.geocoding

import data.model.Location
import data.Result
interface GeocodingService {
    suspend fun getCoordinates(city: String): Result<Location>
}


