package ui

import logic.usecase.ClothingSuggestionUseCase
import logic.result.LogicResponse

class ClothesSuggesterCLI(
    private val suggestionUseCase: ClothingSuggestionUseCase,
    private val clothesOutputCLI: ClothesOutputCLI
) {
    fun start() {
        val city = getUserCity()
        if (city != null) {
            when (val response = suggestionUseCase(city)) {
                is LogicResponse.Success -> clothesOutputCLI.showClothingSuggestion(response.data)
                is LogicResponse.Error -> clothesOutputCLI.showError(response.errorMessage)
            }
        } else {
            clothesOutputCLI.showError("City name cannot be empty.")
        }
    }

    private fun getUserCity(): String? {
        println("🌍 Please enter your city name:")
        val city = readLine()?.trim()
        return if (city.isNullOrBlank()) {
            null
        } else {
            city
        }
    }
}
