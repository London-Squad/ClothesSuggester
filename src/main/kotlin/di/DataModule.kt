package di

import data.api.ApiResponseHandler
import data.api.HttpClientFactory
import data.geocoding.dataSource.LocationRemoteDataSource
import data.geocoding.repository.dataSource.LocationDataSource
import data.geocoding.service.GeocodingService
import data.geocoding.service.NominatimGeocodingService
import data.weather.dataSource.WeatherRemoteDataSource
import data.weather.repository.dataSource.WeatherDataSource
import data.weather.service.OpenMeteoWeatherService
import data.weather.service.WeatherService
import io.ktor.client.*
import org.koin.dsl.module

val dataModule = module {
    single<ApiResponseHandler> { ApiResponseHandler() }
    single<HttpClient> { HttpClientFactory().create() }
    single<WeatherService> { OpenMeteoWeatherService(get(), get()) }
    single<WeatherDataSource> { WeatherRemoteDataSource(get()) }
    single<GeocodingService> { NominatimGeocodingService(get(), get()) }
    single<LocationDataSource> { LocationRemoteDataSource(get()) }
}