package ui

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import logic.entity.Clothes
import logic.exception.NetworkException
import logic.usecase.SuggestSuitableClothesUseCase
import logic.result.LogicResponse

class ClothesSuggesterCLI(
    private val suggestionUseCase: SuggestSuitableClothesUseCase,
    private val clothesOutputCLI: ClothesOutputCLI
) : BaseView() {

    fun start() {
        while (true) {
            waitForJob()
            when (val city = getUserCity()) {
                null -> clothesOutputCLI.showError("City name cannot be empty.")
                "0" -> break
                else -> showSuggestedClothes(city)
            }
        }
    }

    private fun showSuggestedClothes(city: String) {
        job = scope.launch {
            suggestionUseCase(city)
                .collect { response ->
                    when (response) {
                        is LogicResponse.Success<Clothes> -> clothesOutputCLI.showClothingSuggestion(response.data.type)
                        is LogicResponse.Error -> clothesOutputCLI.showError(response.errorMessage)
                    }
                }
        }
    }

    private fun getUserCity(): String? {
        print("🌍 Please enter your city name or 0 to exit: ")
        val city = readlnOrNull()?.trim()
        return if (city.isNullOrBlank()) null
        else city
    }

    override fun onError(throwable: Throwable) {
        clothesOutputCLI.showError(throwable.message!!)
    }
}