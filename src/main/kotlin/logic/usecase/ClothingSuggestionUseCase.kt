package logic.usecase

import logic.entity.ClothingType



data class ClothingSuggestionResult(
    val type: ClothingType,
    val note: String = ""
)
class ClothingSuggestionUseCase {
    fun suggestClothing(
        temperature: Double,
        isRaining: Boolean
    ): ClothingSuggestionResult {
        val clothingType = when {
            temperature >= 25 -> ClothingType.LIGHT
            temperature in 15.0..24.9 -> ClothingType.MEDIUM
            else -> ClothingType.HEAVY
        }

        val rainNote = if (isRaining)
            "Note: It's raining today, consider carrying an umbrella."
        else ""

        return ClothingSuggestionResult(clothingType, rainNote)
    }
}
