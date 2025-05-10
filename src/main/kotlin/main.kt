import data.api.ApiResponseHandler
import data.api.HttpClientFactory
import data.geocoding.dataSource.LocationRemoteDataSource
import data.geocoding.repository.LocationRepositoryImpl
import data.geocoding.service.NominatimGeocodingService
import data.weather.dataSource.WeatherRemoteDataSource
import data.weather.repository.WeatherRepositoryImpl
import data.weather.service.OpenMeteoWeatherService
import di.dataModule
import di.logicModule
import logic.usecase.ClothingSuggestionUseCase
import org.koin.core.context.startKoin
import ui.ClothesOutputCLI
import ui.ClothesSuggesterCLI


fun main() {
    startKoin { modules(logicModule, dataModule)
    }
    val ktor = HttpClientFactory().create()
    val clothesSuggesterCLI = ClothesSuggesterCLI(
        suggestionUseCase = ClothingSuggestionUseCase(
            WeatherRepositoryImpl(
                WeatherRemoteDataSource(
                    OpenMeteoWeatherService(ktor, ApiResponseHandler())
                )
            ),
            LocationRepositoryImpl(
                LocationRemoteDataSource(
                    NominatimGeocodingService(ktor)
                )
            )
        ),
        clothesOutputCLI = ClothesOutputCLI()
    )


    clothesSuggesterCLI.start()

}