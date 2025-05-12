package logic.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import logic.entity.Clothes
import logic.entity.ClothingType
import logic.entity.Location
import logic.entity.Temperature
import logic.repository.LocationRepository
import logic.repository.WeatherRepository
import logic.result.LogicResponse

class SuggestSuitableClothesUseCase(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(countryName: String): LogicResponse<Clothes> {
        return when (val countryLocation = locationRepository.fetchLocation(countryName)) {
            is LogicResponse.Success<Location> -> fetchTemperature(countryLocation.data)
            is LogicResponse.Error -> countryLocation
        }
    }

    private suspend fun fetchTemperature(countryLocation: Location): LogicResponse<Clothes> {
        return withContext(Dispatchers.IO) {
            val countryTemperature = weatherRepository.fetchTodayWeatherByLocation(
                countryLocation.latitude,
                countryLocation.longitude
            )
            when (countryTemperature) {
                is LogicResponse.Success<Temperature> -> {
                    val clothes = suggestClothing(countryTemperature.data.value)
                    LogicResponse.Success(clothes)
                }

                is LogicResponse.Error -> countryTemperature
            }
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