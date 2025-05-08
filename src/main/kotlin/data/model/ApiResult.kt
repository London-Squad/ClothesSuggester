package data.model

sealed class ApiResult<out T: DataModel> {
    data class Success<T: DataModel>(val data: T): ApiResult<T>()
    data class Error(val error: Boolean, val reason: String): ApiResult<Nothing>()
}