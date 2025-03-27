package com.christsondev.utilities

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class CountdownTimer(
    dispatcherProvider: DispatcherProvider,
) {
    private val coroutineScope = CoroutineScope(DispatcherProvider.io())
    private var job: Job? = null

    fun startTimer(
        delayMs: Long,
        onTimeout: () -> Unit,
        onEachSecond: (Long) -> Unit,
    ) {
        stopTimer()

        job = coroutineScope.launch {
            flow {
                var currentMs = delayMs
                emit(currentMs)

                repeat((currentMs / OneSecond).toInt()) {
                    delay(OneSecond)

                    currentMs -= OneSecond
                    emit(currentMs)
                }

                withTimeout(delayMs) {
                    onTimeout.invoke()
                    stopTimer()
                }
            }.collect { currentMs -> onEachSecond.invoke(currentMs) }
        }
    }

    fun stopTimer() {
        job?.cancel()
        job = null
    }

    private companion object {
        const val OneSecond = 1_000L
    }
}