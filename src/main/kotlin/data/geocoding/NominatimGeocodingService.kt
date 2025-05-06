package data.geocoding

import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import org.json.JSONArray

class NominatimGeocodingService : GeocodingService {
    override fun getCoordinates(city: String): GeocodingResult {
        return try {
            val encodedCity = URLEncoder.encode(city, "UTF-8")
            val urlString = "https://nominatim.openstreetmap.org/search?city=$encodedCity&format=json&limit=1"
            val url = URL(urlString)

            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("User-Agent", "ClothesSuggesterApp/1.0 (contact@example.com)")
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                return GeocodingResult.Error("HTTP error: ${connection.responseCode}")
            }

            val response = connection.inputStream.bufferedReader().readText()
            val results = JSONArray(response)

            if (results.length() == 0) return GeocodingResult.Error("City not found")

            val firstResult = results.getJSONObject(0)
            val lat = firstResult.getDouble("lat")
            val lon = firstResult.getDouble("lon")

            GeocodingResult.Success(lat, lon)
        } catch (e: Exception) {
            GeocodingResult.Error("Exception: ${e.localizedMessage}")
        }
    }
}