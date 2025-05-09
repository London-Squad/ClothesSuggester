package data.geocoding

interface GeocodingService {
    fun getCoordinates(city: String): Pair<Double, Double>?
}