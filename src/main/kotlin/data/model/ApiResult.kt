package data.model

sealed class ApiResult<out T> {
    data class Success<T>(val data: T): ApiResult<T>()
    data class Error(val error: Boolean, val reason: String): ApiResult<Nothing>()
}