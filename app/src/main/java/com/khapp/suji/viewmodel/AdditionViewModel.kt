package com.khapp.suji.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kevinhqf.app.quicknote.utils.NumPadUtils
import com.khapp.suji.Constance
import com.khapp.suji.database.entity.DataType
import com.khapp.suji.database.entity.TransactionInfo
import com.khapp.suji.repository.AdditionRepository
import com.khapp.suji.view.comm.BaseViewModel

class AdditionViewModel(private val repository: AdditionRepository) : BaseViewModel() {
    val newValues: MutableLiveData<String> by lazy {
        MutableLiveData<String>("0")
    }
    val dataTypes: LiveData<List<DataType>>
        get() {
            return repository.loadDataTypeByUid(Constance.userId)
        }

    val newDataType: MutableLiveData<DataType> = MutableLiveData()


    fun addDataType(data: DataType) {
        launchIO {
            repository.addDataType(data)
        }
    }



    fun addTransaction() {
        //todo 检查，重置，更新datatype的usetime
        launchIO {
            newDataType.value?.let {
                repository.addTransaction(
                    TransactionInfo(
                        it.id,
                        newValues.value?.toFloat()!!,
                        Constance.userId,
                        Constance.lat,
                        Constance.lng,
                        Constance.address
                    )
                )
            }
        }
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

