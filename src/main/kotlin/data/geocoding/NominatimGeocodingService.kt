package data.geocoding

import data.Result
import data.model.Location
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class NominatimGeocodingService(
    private val client: HttpClient
) : GeocodingService {

    override suspend fun getCoordinates(city: String): Result<Location> {
        return try {

            val responseText = client.get("https://nominatim.openstreetmap.org/search") {
                url {
                    parameters.append("city", city)
                    parameters.append("format", "json")
                    parameters.append("limit", "1")
                }
                headers.append("User-Agent", "ClothesSuggesterApp/1.0")
            }.body<List<Location>>()
            if(responseText.isEmpty()){
                return Result.Error("No results found for city: $city")
            }else{
            Result.Success(responseText.first())
            }
        } catch (e: Exception) {

            Result.Error("Exception: ${e.localizedMessage}")
        }
    }

}
