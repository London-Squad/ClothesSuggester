package ui

import kotlinx.coroutines.*

abstract class BaseView {
    private val errorHandler = CoroutineExceptionHandler { corouineContext, throwable ->
        onError(throwable)
    }
    protected val scope = CoroutineScope( SupervisorJob() + Dispatchers.Default + errorHandler)
    protected var job: Job? = null

    abstract fun onError(throwable: Throwable)
}