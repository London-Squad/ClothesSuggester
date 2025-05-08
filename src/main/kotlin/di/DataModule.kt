package di

import data.api.ApiResponseHandler
import data.api.HttpClientFactory
import data.weather.service.OpenMeteoWeatherService
import data.weather.service.WeatherService
import io.ktor.client.*
import org.koin.dsl.module

val dataModule = module {
    single<ApiResponseHandler> { ApiResponseHandler() }
    single<HttpClient> { HttpClientFactory().create() }
    single<WeatherService> { OpenMeteoWeatherService(get(), get()) }
}