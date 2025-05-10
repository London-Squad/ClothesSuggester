import di.dataModule
import di.logicModule
import di.uiModule
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent
import ui.ClothesSuggesterCLI


fun main() {
    startKoin { modules(logicModule, dataModule, uiModule)
    }
    val clothesSuggesterCLI = KoinJavaComponent.getKoin().get<ClothesSuggesterCLI>()
    clothesSuggesterCLI.start()

}