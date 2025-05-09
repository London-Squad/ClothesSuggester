import data.api.ApiResponseHandler
import data.weather.dataSource.WeatherRemoteDataSource
import data.weather.repository.WeatherRepositoryImpl
import data.weather.service.OpenMeteoWeatherService
import di.dataModule
import di.logicModule
import io.ktor.client.HttpClient
import logic.usecase.ClothingSuggestionUseCase
import org.koin.core.context.startKoin

import ui.ClothesOutputCLI
import ui.ClothesSuggesterCLI


fun main() {
    startKoin { modules(logicModule, dataModule)
    }
    val clothesSuggesterCLI = ClothesSuggesterCLI(
        suggestionUseCase = ClothingSuggestionUseCase(
            WeatherRepositoryImpl(
                WeatherRemoteDataSource(
                    OpenMeteoWeatherService(HttpClient(), ApiResponseHandler())
                )
            )
        ),
        clothesOutputCLI = ClothesOutputCLI()
    )


    clothesSuggesterCLI.start()

}