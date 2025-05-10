package di

import org.koin.dsl.module
import ui.ClothesOutputCLI
import ui.ClothesSuggesterCLI

val uiModule = module {
    single<ClothesSuggesterCLI> { ClothesSuggesterCLI(get(),get()) }
    single<ClothesOutputCLI> { ClothesOutputCLI() }
}