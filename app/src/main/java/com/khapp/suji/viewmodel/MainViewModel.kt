package com.khapp.suji.viewmodel

import androidx.lifecycle.MutableLiveData
import com.khapp.suji.Constance
import com.khapp.suji.EMPTY_USER
import com.khapp.suji.data.UserResponse
import com.khapp.suji.datastore.AppDataStore
import com.khapp.suji.view.comm.BaseViewModel

class MainViewModel : BaseViewModel() {

    val menuPosition = MutableLiveData<Int>(1)
    val user = MutableLiveData<UserResponse>()

    fun changeMenuPosition(position: Int) {
        menuPosition.postValue(position)
    }

    fun loadUser() {
        user.postValue(Constance.user)
    }


    fun logout() {
        //todo 二次确认,绑定的数据需要发生变化
        launchIO {
            AppDataStore.saveUserData(EMPTY_USER)
            loadUser()
        }
    }

}