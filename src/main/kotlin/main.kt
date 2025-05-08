import di.dataModule
import di.logicModule
import org.koin.core.context.startKoin

fun main() {
    startKoin { modules(logicModule, dataModule) }
}