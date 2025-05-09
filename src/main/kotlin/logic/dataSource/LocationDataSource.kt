package logic.dataSource

import logic.entity.Location
import logic.result.LogicResponse

interface LocationDataSource {
    suspend fun fetchLocation(city: String): LogicResponse<Location>
}
