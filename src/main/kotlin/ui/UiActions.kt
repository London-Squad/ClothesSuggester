package ui

import logic.entity.Clothes

sealed interface UiActions {
    data object Idle : UiActions
    data object ShowLoading : UiActions
    data class ReturnResponse(val response: Clothes) : UiActions
    data class ShowError(val message: String) : UiActions
}