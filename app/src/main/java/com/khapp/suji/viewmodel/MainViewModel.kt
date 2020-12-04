package com.khapp.suji.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.khapp.suji.EMPTY_USER
import com.khapp.suji.repository.UserRepository
import com.khapp.suji.utils.IconSet
import com.khapp.suji.view.comm.BaseViewModel

class MainViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    val menuPosition = MutableLiveData<Int>(1)



    fun changeMenuPosition(position: Int) {
        menuPosition.postValue(position)
    }



}