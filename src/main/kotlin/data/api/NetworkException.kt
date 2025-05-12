package data.api

open class NetworkException(message: String) : Exception(message)

class NoInternetException : NetworkException("please connect to internet")
class SerializationFailedException : NetworkException( "error in serialize api response")
class ResponseMayBeChangedException: NetworkException("error in dto response may be changed or wrong mapped")
class ServerErrorException: NetworkException("error in server side")
data class UnknownErrorException(val code: Int, val errorMessage: String) :
    NetworkException("unhandled error with code:$code and message: $errorMessage")