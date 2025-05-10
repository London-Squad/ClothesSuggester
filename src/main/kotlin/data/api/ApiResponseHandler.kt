package data.api

import data.model.ApiResult
import data.model.DataModel
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.serialization.*
import io.ktor.util.network.*
import logic.exception.*

class ApiResponseHandler {
    suspend inline fun <reified T > safeApiCall(call: () -> HttpResponse): ApiResult<T> {
        return try {
            call().handleResponse<T>()
        } catch (e: UnresolvedAddressException) {
            throw NoInternetException()
        } catch (e: NoTransformationFoundException) {
            throw SerializationFailedException()
        } catch (e: JsonConvertException) {
            throw ResponseMayBeChangedException()
        } catch (e: Exception) {
            throw UnknownErrorException(999, e.message ?: "unkown error")
        }
    }

    suspend inline fun <reified T > HttpResponse.handleResponse(): ApiResult<T> {
        return when (status.value) {
            in 200..209 -> {
                val response: T = body()
                if (response is DataModel && response.reason != null) {
                    ApiResult.Error(true, response.reason ?:"")
                } else {
                    ApiResult.Success(response)
                }
            }
            in 500..599 -> throw ServerErrorException()
            else -> throw UnknownErrorException(code = status.value, errorMessage = status.description)
        }

    }
}