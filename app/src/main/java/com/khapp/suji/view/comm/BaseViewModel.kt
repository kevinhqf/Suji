package com.khapp.suji.view.comm

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel(), LifecycleObserver {

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun launchUI(block: suspend CoroutineScope.() -> Unit)
    {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                block()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun launchIO(
        exceptionHandler: suspend CoroutineScope.(Throwable) -> Unit = {},
        finalHandler: suspend CoroutineScope.() -> Unit = {},
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            coroutineScope {
                try {
                    block()
                } catch (e: Exception) {
                    e.printStackTrace()
                    exceptionHandler(e)
                } finally {
                    finalHandler()
                }
            }
        }
    }

}