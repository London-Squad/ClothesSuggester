import com.google.common.truth.Truth.assertThat
import data.geocoding.NominatimGeocodingService
import data.model.ApiResult
import data.model.Location
import data.model.LocationResponse
import data.api.ApiResponseHandler
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.assertTrue
import kotlin.test.assertEquals
import io.ktor.serialization.kotlinx.json.*
import kotlin.test.Test

class NominatimGeocodingServiceTest {

    @Test
    fun getCoordinates_shouldReturnError_whenNoLocationsFound() = runBlocking {
        val emptyResponse = LocationResponse(locations = emptyList())

        val mockEngine = MockEngine {
            respond(
                content = Json.encodeToString(emptyResponse),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val service = NominatimGeocodingService(client, ApiResponseHandler())


        val result = service.getCoordinates("UnknownCity")

        assertTrue(result is ApiResult.Error)
        assertEquals("No results for UnknownCity", (result as ApiResult.Error).reason)
    }

    @Test
    fun getCoordinates_shouldReturnSuccess_whenLocationExists() = runBlocking {
        val location = Location(40.7128, -74.0060)
        val response = LocationResponse(locations = listOf(location))

        val mockEngine = MockEngine {
            respond(
                content = Json.encodeToString(LocationResponse.serializer(), response),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val service = NominatimGeocodingService(client, ApiResponseHandler())

        val result = service.getCoordinates("New York")


        assertThat(result).isInstanceOf(ApiResult.Success::class.java)
        val data = (result as ApiResult.Success).data
        assertThat(data.lat).isEqualTo(40.7128)
        assertThat(data.lon).isEqualTo(-74.0060)
    }



}
