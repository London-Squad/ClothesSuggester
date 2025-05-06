package data.geocoding

import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import org.json.JSONArray

class NominatimGeocodingService : GeocodingService{
    override fun getCoordinates(city: String): Pair<Double, Double>? {
        try {
            val encodedCity = URLEncoder.encode(city, "UTF-8")
            val urlString = "https://nominatim.openstreetmap.org/search?city=$encodedCity&format=json&limit=1"
            val url = URL(urlString)

            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "GET"
                setRequestProperty("User-Agent", "ClothesSuggesterApp/1.0 (contact@example.com)") // Required by Nominatim
                connectTimeout = 5000
                readTimeout = 5000

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = inputStream.bufferedReader().readText()
                    val results = JSONArray(response)

                    if (results.length() == 0) return null

                    val firstResult = results.getJSONObject(0)
                    val Latitude = firstResult.getDouble("Lat")
                    val Longitude = firstResult.getDouble("Lon")

                    return Pair(Latitude, Longitude)
                } else {
                    println("Geocoding failed. HTTP response code: $responseCode")
                }
            }
        } catch (e: Exception) {
            println("Error during geocoding: ${e.message}")
        }

        return null
    }
}