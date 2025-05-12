package ui

import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import logic.entity.Clothes
import logic.usecase.SuggestSuitableClothesUseCase
import logic.result.LogicResponse

class ClothesSuggesterCLI(
    private val suggestionUseCase: SuggestSuitableClothesUseCase,
    private val clothesOutputCLI: ClothesOutputCLI
) : BaseView() {

    private val _uiAction = MutableStateFlow<UiActions>(UiActions.Idle)
    private val uiAction = _uiAction.asStateFlow()

    override fun onError(throwable: Throwable) {
        _uiAction.update { UiActions.ShowError(throwable.message!!) }
    }

    fun start() = runBlocking {
        observeCurrentState()
        job?.join()
        scope.cancel()
    }

    private fun observeCurrentState() {
        job = scope.launch {
            uiAction.collectLatest { action ->
                when (action) {
                    UiActions.Idle -> getCityFromUser()
                    UiActions.ShowLoading -> showLoading()
                    is UiActions.ReturnResponse -> showClothesResponse(action.response)
                    is UiActions.ShowError -> showErrorMessage(action.message)
                }
            }
        }
    }

    private fun getCityFromUser() {
        when (val city = getUserCity()) {
            null -> clothesOutputCLI.showError("City name cannot be empty.")
            "0" -> job?.cancel()
            else -> showSuggestedClothes(city)
        }
    }

    private fun showLoading() {
        println(".........................Loading............................")
    }

    private fun showClothesResponse(clothes: Clothes) {
        clothesOutputCLI.showClothingSuggestion(clothes.type)
        _uiAction.update { UiActions.Idle }
    }

    private fun showErrorMessage(message: String) {
        clothesOutputCLI.showError(message)
        _uiAction.update { UiActions.Idle }
    }

    private fun getUserCity(): String? {
        print("🌍 Please enter your city name or 0 to exit: ")
        val city = readlnOrNull()?.trim()
        return if (city.isNullOrBlank()) null
        else city
    }

    private fun showSuggestedClothes(city: String) {
        scope.launch {
            _uiAction.update { UiActions.ShowLoading }
            when (val response = suggestionUseCase(city)) {
                is LogicResponse.Success<Clothes> -> _uiAction.update { UiActions.ReturnResponse(response.data) }
                is LogicResponse.Error -> _uiAction.update { UiActions.ShowError(response.errorMessage) }
            }
        }
    }

}