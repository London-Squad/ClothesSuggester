package logic.result


sealed class LogicResponse<out T> {
    data class Success<T>(val data: T): LogicResponse<T>()
    data class Error(val errorMessage: String): LogicResponse<Nothing>()
}