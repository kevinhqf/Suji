package com.khapp.suji.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kevinhqf.app.quicknote.utils.NumPadUtils

class AdditionViewModel : ViewModel() {
     val newValues : MutableLiveData<String> by lazy {
         MutableLiveData<String>("0")
     }



    fun numpadAction(action: String) {
        val curValue = newValues.value
        try {
            curValue?.let {
                val tmpValue = when (action) {
                    "1" -> {
                        NumPadUtils.numpadDigitalAction(it, 1)
                    }
                    "2" -> {
                        NumPadUtils.numpadDigitalAction(it, 2)
                    }
                    "3" -> {
                        NumPadUtils.numpadDigitalAction(it, 3)
                    }
                    "4" -> {
                        NumPadUtils.numpadDigitalAction(it, 4)
                    }
                    "5" -> {
                        NumPadUtils.numpadDigitalAction(it, 5)
                    }
                    "6" -> {
                        NumPadUtils.numpadDigitalAction(it, 6)
                    }
                    "7" -> {
                        NumPadUtils.numpadDigitalAction(it, 7)
                    }
                    "8" -> {
                        NumPadUtils.numpadDigitalAction(it, 8)
                    }
                    "9" -> {
                        NumPadUtils.numpadDigitalAction(it, 9)
                    }
                    "0" -> {
                        NumPadUtils.numpad0Action(it)
                    }
                    "." -> {
                        NumPadUtils.numpadDotAction(it)
                    }
                    "del" -> {
                        NumPadUtils.numpadBackspaceAction(it)
                    }
                    else -> {
                        it
                    }
                }
                newValues.postValue(tmpValue)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }
}