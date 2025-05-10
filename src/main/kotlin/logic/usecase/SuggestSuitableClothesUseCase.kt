package logic.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import logic.entity.Clothes
import logic.entity.ClothingType
import logic.entity.Location
import logic.entity.Temperature
import logic.repository.LocationRepository
import logic.repository.WeatherRepository
import logic.result.LogicResponse

class ClothingSuggestionUseCase(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository
) {
    operator fun invoke(countryName: String): Flow<LogicResponse<Clothes>> = flow {
        when (val countryLocation = locationRepository.fetchLocation(countryName)) {
            is LogicResponse.Success<Location> -> emit(fetchTemperature(countryLocation.data))
            is LogicResponse.Error -> emit(countryLocation)
        }
    }

    private suspend fun fetchTemperature(countryLocation: Location): LogicResponse<Clothes> {
        val countryTemperature = weatherRepository.fetchTodayWeatherByLocation(
            countryLocation.latitude,
            countryLocation.longitude
        )
        when (countryTemperature) {
            is LogicResponse.Success<Temperature> -> {
                val clothes = suggestClothing(countryTemperature.data.value)
                return LogicResponse.Success(clothes)
            }
            is LogicResponse.Error -> return countryTemperature
        }
    }

    private fun suggestClothing(temperature: Double): Clothes {
        val type = when {
            temperature >= 25 -> ClothingType.LIGHT
            temperature > 15 && temperature < 25 -> ClothingType.MEDIUM
            else -> ClothingType.HEAVY
        }
        return Clothes(type)
    }
}