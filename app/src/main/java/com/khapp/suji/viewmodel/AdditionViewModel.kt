package com.khapp.suji.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import com.kevinhqf.app.quicknote.utils.NumPadUtils
import com.khapp.suji.Constance
import com.khapp.suji.data.Resources
import com.khapp.suji.database.entity.DataType
import com.khapp.suji.database.entity.TransactionInfo
import com.khapp.suji.repository.AdditionRepository
import com.khapp.suji.utils.IconSet
import com.khapp.suji.view.addition.DataTypeAdapter
import com.khapp.suji.view.comm.BaseViewModel

class AdditionViewModel(private val repository: AdditionRepository) : BaseViewModel() {
    private val userId = MutableLiveData<Long>()

    /**
     * 要添加的值
     */
    val newValues: MutableLiveData<String> by lazy {
        MutableLiveData<String>("0")
    }

    /**
     * 类型列表
     */
    val dataTypes = switchMap(map(userId) {
        repository.loadDataTypeByUid(Constance.userId)
    }){it}


    /**
     * 要添加的类型
     */
    val newDataType: MutableLiveData<DataType> = MutableLiveData()

    /**
     * 添加记账类型
     */
    fun addDataType(data: DataType, sameContentInfo: DataTypeAdapter.SameContentInfo) {
        launchIO {
            //添加记账类型时检查是否已存在相同的类型，如果有则更新使用时间，让其排序靠前，如没有则添加
            if (sameContentInfo.isSame) {
                repository.updateDataTypeUseTime(
                    System.currentTimeMillis(),
                    sameContentInfo.dataTypeId!!
                )
            } else {
                repository.addDataType(data)
            }
        }
    }

    fun switchUser(uid:Long){
        userId.postValue(uid)
    }

    fun setNewDataType(data: DataType) {
        newDataType.postValue(data)
    }


    fun getAllIcons() {
        if (IconSet.presetIcons.isEmpty())
            launchIO {
                val allIcons = repository.getAllIcons()
                IconSet.presetIcons = allIcons
            }
    }

    fun addTransaction(typeError: () -> Unit, valueError: () -> Unit, completed: () -> Unit) {
        when {
            newDataType.value == null -> {
                typeError()
            }
            newValues.value.isNullOrEmpty() || newValues.value?.toFloat() == 0f -> {
                valueError()
            }
            else -> {
                launchIO {
                    newDataType.value?.let {
                        repository.addTransaction(
                            TransactionInfo(
                                newValues.value?.toFloat()!!,
                                Constance.userId,
                                Constance.lat,
                                Constance.lng,
                                Constance.address,
                                it.id,
                                it.name,
                                it.icon,
                                it.type
                            )
                        )
                        repository.updateDataTypeUseTime(System.currentTimeMillis(), it.id)

                    }
                    launchUI { completed() }
                    reset()
                }
            }
        }

    }

    private fun reset() {
        newValues.postValue("0")
        newDataType.postValue(null)
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

