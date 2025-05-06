package data.geocoding

interface GeocodingService {
 //   fun getCoordinates(city: String): Pair<Double, Double>?
    fun getCoordinates(city: String): GeocodingResult
}