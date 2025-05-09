package data.geocoding.service

import data.api.ApiResponseHandler
import data.model.ApiResult
import data.model.LocationData
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class NominatimGeocodingService(
    private val client: HttpClient,
    private val apiHandler: ApiResponseHandler = ApiResponseHandler()
) : GeocodingService {

    override suspend fun getCoordinates(city: String): ApiResult<List<LocationData>> {
        val response = apiHandler.safeApiCall<List<LocationData>> {
            client.get("https://nominatim.openstreetmap.org/search") {
                url {
                    parameters.append("city", city)
                    parameters.append("format", "json")
                    parameters.append("limit", "1")
                }
                headers.append("User-Agent", "ClothesSuggesterApp/1.0")
            }
        }
        return response
    }
}