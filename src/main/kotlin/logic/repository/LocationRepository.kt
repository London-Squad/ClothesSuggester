package logic.repository

import logic.entity.Location
import logic.result.LogicResponse

interface LocationRepository {
    suspend fun fetchLocation(city: String): LogicResponse<Location>
}