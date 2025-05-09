package ui

import logic.entity.ClothingType

class ClothesOutputCLI {
    fun showClothingSuggestion(clothingType: ClothingType) {
        println("👗 You should wear: ${clothingType.label}")
    }

    fun showError(message: String) {
        println("⚠️ Error: $message")
    }
}
