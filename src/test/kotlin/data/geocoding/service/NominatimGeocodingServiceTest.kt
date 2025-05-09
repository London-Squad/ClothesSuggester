package data.geocoding.service

import org.junit.jupiter.api.Assertions.*
import com.google.common.truth.Truth.assertThat
import data.api.ApiResponseHandler
import data.geocoding.service.NominatimGeocodingService
import data.model.ApiResult
import data.model.LocationData
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test

class NominatimGeocodingServiceTest {

    @Test
    fun getCoordinates_shouldReturnSuccess_whenLocationExists() = runBlocking {
        val mockEngine = MockEngine { _ ->
            respond(
                content = """
                    [
                        { "lat": 40.7128, "lon": -74.0060 }
                    ]
                """.trimIndent(),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val service = NominatimGeocodingService(client)

        val result = service.getCoordinates("New York")

        assertThat(result).isInstanceOf(ApiResult.Success::class.java)
        val data = (result as ApiResult.Success).data
        assertThat(data).isNotEmpty()
        assertThat(data.first().lat).isEqualTo(40.7128)
        assertThat(data.first().lon).isEqualTo(-74.0060)
    }
}