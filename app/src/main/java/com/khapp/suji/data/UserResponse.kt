package com.khapp.suji.data

data class UserResponse(
    val id: Int,
    val phone: String = "",
    var name: String = "",
    var avatar: String = "",
    var email: String = "",
    var register_time: String = System.currentTimeMillis().toString(),
    var last_login_time: String = "",
    var token: String = "") {
}