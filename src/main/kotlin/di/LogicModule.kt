package di

import data.weather.dataSource.WeatherRemoteDataSource
import data.weather.repository.WeatherRepositoryImpl
import data.weather.repository.dataSource.WeatherDataSource
import logic.repository.WeatherRepository
import org.koin.dsl.module

val logicModule = module {
    single<WeatherDataSource> { WeatherRemoteDataSource(get()) }
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
}