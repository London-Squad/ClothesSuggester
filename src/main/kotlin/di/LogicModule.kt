package di

import data.geocoding.repository.LocationRepositoryImpl
import data.weather.repository.WeatherRepositoryImpl
import logic.repository.LocationRepository
import logic.repository.WeatherRepository
import logic.usecase.SuggestSuitableClothesUseCase
import org.koin.dsl.module

val logicModule = module {
    single<LocationRepository> { LocationRepositoryImpl(get()) }
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
    single<SuggestSuitableClothesUseCase> { SuggestSuitableClothesUseCase(get(), get()) }
}