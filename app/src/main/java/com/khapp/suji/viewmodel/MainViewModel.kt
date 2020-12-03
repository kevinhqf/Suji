package com.khapp.suji.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.khapp.suji.Constance
import com.khapp.suji.EMPTY_USER
import com.khapp.suji.data.UserResponse
import com.khapp.suji.datastore.AppDataStore
import com.khapp.suji.repository.LoginRepository
import com.khapp.suji.view.comm.BaseViewModel
import kotlinx.coroutines.flow.first

class MainViewModel(private val loginRepository: LoginRepository) : BaseViewModel() {

    val menuPosition = MutableLiveData<Int>(1)
    val user = loginRepository.readLocalUser().asLiveData()



    fun changeMenuPosition(position: Int) {
        menuPosition.postValue(position)
    }

    fun logout() {
        //todo 二次确认,绑定的数据需要发生变化
        launchIO {
            loginRepository.saveUserLocal(EMPTY_USER)
        }
    }

}