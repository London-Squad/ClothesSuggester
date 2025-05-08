package data.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val lat: Double,
    val lon: Double,
    override val error: Boolean? = null,
    override val reason: String? = null
): DataModel

@Serializable
data class LocationResponse(
    val locations: List<Location>?= null,
    override val error: Boolean? = null,
    override val reason: String? = null
) : DataModel