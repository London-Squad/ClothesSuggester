package data.model

import kotlinx.serialization.Serializable


@Serializable
data class Location(
    val lat: Double,
    val lon: Double
)