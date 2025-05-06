package data.geocoding

import data.Result
import data.model.Location
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray

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
            }.body<String>()

            val json = Json.parseToJsonElement(responseText).jsonArray

            if (json.isEmpty()) return Result.Error("City not found")

            val first = json[0] as JsonObject
            val lat = first["lat"]!!.toString().toDouble()
            val lon = first["lon"]!!.toString().toDouble()

            Result.Success(Location(lat, lon))
        } catch (e: Exception) {
            Result.Error("Exception: ${e.localizedMessage}")
        }
    }
}
