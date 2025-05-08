package logic.exception

open class NetworkException(message: String) : Exception(message)

data class NoInternetException(val errorMessage: String? = null) : NetworkException(errorMessage ?: "please connect to internet")
data class SerializationFailedException(val errorMessage: String? = null) : NetworkException(errorMessage ?: "error in serialize api response")
data class ResponseMayBeChangedException(val errorMessage: String? = null) : NetworkException(errorMessage ?: "error in dto response may be changed or wrong mapped")
data class ServerErrorException(val errorMessage: String? = null) : NetworkException(errorMessage ?: "error in server side")
data class UnknownErrorException(val code: Int, val errorMessage: String) :
    NetworkException("unhandled error with code:$code and message: $errorMessage")
