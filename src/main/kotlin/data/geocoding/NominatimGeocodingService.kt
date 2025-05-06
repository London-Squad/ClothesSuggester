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
            }.body<String>()


            val jsonArray = Json.parseToJsonElement(responseText).jsonArray


            if (jsonArray.isEmpty()) return Result.Error("City not found")


            val first = jsonArray[0].jsonObject

            val latString = first["lat"]?.jsonPrimitive?.content
            val lonString = first["lon"]?.jsonPrimitive?.content

            val lat = latString?.toDoubleOrNull()
            val lon = lonString?.toDoubleOrNull()

            if (lat == null || lon == null) {
                return Result.Error("Invalid coordinates received")
            }


            Result.Success(Location(lat, lon))
        } catch (e: Exception) {

            Result.Error("Exception: ${e.localizedMessage}")
        }
    }

}
