package com.khapp.suji.repository

import com.khapp.suji.App
import com.khapp.suji.api.API
import com.khapp.suji.data.ApiResponse
import com.khapp.suji.data.UserResponse
import com.khapp.suji.datastore.AppDataStore
import com.khapp.suji.utils.Utils
import com.khapp.suji.view.comm.BaseRepository

class LoginRepository(private val dataStore: AppDataStore) : BaseRepository() {


    suspend fun checkPhoneCode(phone: String): Int {
        val apiCall = apiCall { API.service.checkPhone(phone) }
        return if (apiCall.code == 0) apiCall.data!! else apiCall.code
    }

    suspend fun signup(phone: String, password: String): ApiResponse<UserResponse> {
        return apiCall { API.service.signup(phone, Utils.md5(password)) }

    }

    suspend fun login(phone: String, password: String): ApiResponse<UserResponse> {
        return apiCall { API.service.login(phone, Utils.md5(password)) }
    }

    suspend fun saveUserLocal(userResponse: UserResponse){
        dataStore.saveUserData(userResponse)
    }

    fun readLocalUser()=dataStore.loadUser()

}