package com.khapp.suji.repository

import com.bumptech.glide.util.Util
import com.khapp.suji.App
import com.khapp.suji.CODE_SUCCESS
import com.khapp.suji.api.API
import com.khapp.suji.data.ApiResponse
import com.khapp.suji.data.Resources
import com.khapp.suji.data.UserResponse
import com.khapp.suji.datastore.AppDataStore
import com.khapp.suji.utils.Utils
import com.khapp.suji.view.comm.BaseRepository

class UserRepository(private val dataStore: AppDataStore) : BaseRepository() {


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

    suspend fun saveUserLocal(userResponse: UserResponse) {
        dataStore.saveUserData(userResponse)
    }

    fun readLocalUser() = dataStore.loadUser()

    suspend fun loadAvatars(): List<Resources> {
        val apiCall = apiCall { API.service.getAvatar() }
        if (apiCall.code == CODE_SUCCESS) {
            return apiCall.data!!
        }
        return ArrayList()
    }

    suspend fun changeUserProfile(
        uid: Long,
        token: String,
        name: String,
        email: String,
        avatar: String,
        password: String
    ): ApiResponse<UserResponse> {
        return apiCall {
            var p = password
            if (p.isNotEmpty())
                p = Utils.md5(p)
            API.service.changeInfo(uid, token, name, email, avatar, p)
        }
    }

}