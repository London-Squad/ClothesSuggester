package ui

import kotlinx.coroutines.*
import logic.exception.NetworkException

abstract class BaseView {
    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is NetworkException -> onError(throwable)
        }
    }
    protected val scope = CoroutineScope(Dispatchers.Default + errorHandler)
    protected var job: Job? = null

    abstract fun onError(throwable: Throwable)

    fun waitForJob() {
        runBlocking { job?.join() }
    }
}