package com.khapp.suji.data

import com.khapp.suji.Constance

data class UserResponse(
    val id: Long,
    val phone: String = "",
    var name: String = "",
    var avatar: String = "",
    var email: String = "",
    var register_time: String = System.currentTimeMillis().toString(),
    var last_login_time: String = "",
    var token: String = ""
) {
    fun getAvatarUrl(): String {
        return if (avatar.isNotEmpty()) (Constance.API_RESOURCES_HOST + avatar) else ""
    }

}