import data.api.ApiResponseHandler
import data.api.HttpClientFactory
import di.dataModule
import di.logicModule
import org.koin.core.context.startKoin
import data.geocoding.service.NominatimGeocodingService
import kotlinx.coroutines.runBlocking

fun main() {
    startKoin { modules(logicModule, dataModule) }
}