package di

import org.koin.dsl.module
import ui.ClothesSuggesterCLI

object AppModule {
    val appModule = module {

        includes(dataModule, logicModule)


        

    }
}