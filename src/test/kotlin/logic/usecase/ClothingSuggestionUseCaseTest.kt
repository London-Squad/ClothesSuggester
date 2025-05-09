package logic.usecase

import logic.entity.ClothingType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ClothingSuggestionUseCaseTest {

    private val useCase = ClothingSuggestionUseCase()

    // LIGHT
    @Test
    fun `temperature exactly 25 should return LIGHT`() {
        val result = useCase.suggestClothing(25.0, isRaining = false)
        assertEquals(ClothingType.LIGHT, result.type)
    }

    @Test
    fun `temperature above 25 should return LIGHT`() {
        val result = useCase.suggestClothing(30.0, isRaining = false)
        assertEquals(ClothingType.LIGHT, result.type)
    }

    // MEDIUM
    @Test
    fun `temperature 24 point 9 should return MEDIUM`() {
        val result = useCase.suggestClothing(24.9, isRaining = false)
        assertEquals(ClothingType.MEDIUM, result.type)
    }

    @Test
    fun `temperature between 15 and 24 point 9 should return MEDIUM`() {
        val result = useCase.suggestClothing(18.5, isRaining = false)
        assertEquals(ClothingType.MEDIUM, result.type)
    }

    @Test
    fun `temperature exactly 15 should return MEDIUM`() {
        val result = useCase.suggestClothing(15.0, isRaining = false)
        assertEquals(ClothingType.MEDIUM, result.type)
    }

    // HEAVY
    @Test
    fun `temperature below 15 should return HEAVY`() {
        val result = useCase.suggestClothing(10.0, isRaining = false)
        assertEquals(ClothingType.HEAVY, result.type)
    }

    // Rain note
    @Test
    fun `should include rain note when raining`() {
        val result = useCase.suggestClothing(22.0, isRaining = true)
        assertTrue(result.note.contains("raining", ignoreCase = true))
    }

    @Test
    fun `should not include rain note when not raining`() {
        val result = useCase.suggestClothing(22.0, isRaining = false)
        assertTrue(result.note.isEmpty())
    }

    @Test
    fun `should return correct type and rain note when cold and raining`() {
        val result = useCase.suggestClothing(5.0, isRaining = true)
        assertEquals(ClothingType.HEAVY, result.type)
        assertTrue(result.note.contains("raining", ignoreCase = true))
    }
}