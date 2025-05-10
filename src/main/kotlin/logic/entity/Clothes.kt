package logic.entity

data class Clothes(
    val type: ClothingType
)

enum class ClothingType(val label : String) {
    LIGHT("Light clothing"),
    MEDIUM("Medium clothing"),
    HEAVY("Heavy clothing");
}