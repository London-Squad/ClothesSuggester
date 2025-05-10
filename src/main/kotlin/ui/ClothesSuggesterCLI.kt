package ui

import kotlinx.coroutines.*
import logic.entity.Clothes
import logic.exception.NetworkException
import logic.usecase.ClothingSuggestionUseCase
import logic.result.LogicResponse

class ClothesSuggesterCLI(
    private val suggestionUseCase: ClothingSuggestionUseCase,
    private val clothesOutputCLI: ClothesOutputCLI
) {
    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is NetworkException -> clothesOutputCLI.showError(throwable.message!!)
        }
    }
    private val scope = CoroutineScope(Dispatchers.IO + errorHandler)
    private var job: Job? = null

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

    private fun waitForJob() {
        runBlocking { job?.join() }
    }

    private fun showSuggestedClothes(city: String) {
        job = scope.launch {
            suggestionUseCase(city).collect { response ->
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
}