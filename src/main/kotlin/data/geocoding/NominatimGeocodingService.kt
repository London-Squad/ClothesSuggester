package data.geocoding

import data.api.ApiResponseHandler
import data.model.ApiResult
import data.model.Location
import data.model.LocationResponse
import io.ktor.client.*
import io.ktor.client.request.get

class NominatimGeocodingService(
    private val client: HttpClient,
    private val apiHandler: ApiResponseHandler = ApiResponseHandler()
) : GeocodingService {

    override suspend fun getCoordinates(city: String): ApiResult<Location> {
        val response = apiHandler.safeApiCall<LocationResponse> {
            client.get("https://nominatim.openstreetmap.org/search") {
                url {
                    parameters.append("city", city)
                    parameters.append("format", "json")
                    parameters.append("limit", "1")
                }
                headers.append("User-Agent", "ClothesSuggesterApp/1.0")
            }
        }

        return when (response) {
            is ApiResult.Success -> {
                val locations = response.data.locations
                if (!locations.isNullOrEmpty()) {
                    val location = locations.first()
                    ApiResult.Success(location)
                } else {
                    ApiResult.Error(true, "No results for $city")
                }
            }

            is ApiResult.Error -> response
        }
    }
}
