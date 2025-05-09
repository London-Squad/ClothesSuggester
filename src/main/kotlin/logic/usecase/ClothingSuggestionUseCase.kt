package logic.usecase

import data.geocoding.GeocodingService
import logic.entity.ClothingType
import logic.repository.WeatherRepository
import logic.result.LogicResponse

data class ClothingSuggestionResult(
    val type: ClothingType,

    )
class ClothingSuggestionUseCase(
    private val weatherRepository: WeatherRepository,


    ) {
    operator  fun invoke(countryName: String): LogicResponse<ClothingType> {

        TODO("wait for geoCoding")
    }

    private fun suggestClothing(temperature: Double, isRaining: Boolean): ClothingSuggestionResult {
        val type = when {
            temperature >= 25 -> ClothingType.LIGHT
            temperature > 15 && temperature < 25 -> ClothingType.MEDIUM
            else -> ClothingType.HEAVY
        }

        return ClothingSuggestionResult(type)
    }
}