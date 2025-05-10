package data.geocoding.repository.dataSource

import data.model.ApiResult
import data.model.LocationData

interface LocationDataSource {
    suspend fun fetchLocation(city: String): ApiResult<List<LocationData>>
}
