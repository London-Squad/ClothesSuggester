package logic.usecase

import logic.entity.ClothingType
import logic.repository.WeatherRepository
import logic.result.LogicResponse

class ClothingSuggestionUseCase(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(countryName: String): LogicResponse<ClothingType> {
        TODO("wait for geoCoding")
    }
}