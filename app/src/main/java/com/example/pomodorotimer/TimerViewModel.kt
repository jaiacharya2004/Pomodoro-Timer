package com.example.pomodorotimer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class TimerViewModel : ViewModel() {
    private var timerJob: Job? = null
    private var timerRunning = false
    var currentSeconds: MutableState<Int> = mutableStateOf(1500)
    private var onTickListener: ((Int) -> Unit)? = null
    private var onFinishListener: (() -> Unit)? = null

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    fun setOnTickListener(listener: (Int) -> Unit) {
        onTickListener = listener
    }

    fun setOnFinishListener(listener: () -> Unit) {
        onFinishListener = listener
    }

    fun startTimer() {
        if (!timerRunning) {
            timerRunning = true
            timerJob = coroutineScope.launch {
                while (currentSeconds.value > 0) {
                    delay(1000)
                    currentSeconds.value--
                    onTickListener?.invoke(currentSeconds.value)
                }
                onFinishListener?.invoke()
                timerRunning = false
            }
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        timerRunning = false
    }

    fun resetTimer() {
        timerJob?.cancel()
        timerRunning = false
        currentSeconds.value = 1500
        onTickListener?.invoke(currentSeconds.value)
    }

    fun setTimerDuration(seconds: Int) {
        currentSeconds.value = seconds
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.coroutineContext.cancelChildren()
    }
}
