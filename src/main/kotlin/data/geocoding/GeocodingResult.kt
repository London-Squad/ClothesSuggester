package data.geocoding

sealed class GeocodingResult {
    data class Success(val lat: Double, val lon: Double) : GeocodingResult()
    data class Error(val message: String) : GeocodingResult()
}