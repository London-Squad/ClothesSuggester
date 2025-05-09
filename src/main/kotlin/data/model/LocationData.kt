package data.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationData(
    val lat: Double,
    val lon: Double,
)
