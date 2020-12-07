package com.khapp.suji

import com.khapp.suji.data.UserResponse
import com.khapp.suji.preset.AppConfig
import com.khapp.suji.preset.Currency
import com.khapp.suji.preset.Theme

object Constance {
    var config: AppConfig = AppConfig()
    var user: UserResponse = EMPTY_USER
    var userId: Long = -1L
    var lat = 0.0
    var lng = 0.0
    var address = ""
    var API_RESOURCES_HOST = "http://106.53.237.34"
}

val EMPTY_USER = UserResponse(-1)
const val CODE_USER_LOGIN = 1
const val CODE_USER_SIGNUP = 2
const val CODE_SUCCESS = 0
const val CODE_ERROR_USER_NOT_EXIST = -1
const val CODE_ERROR_USER_WRONG_PASSWORD = -2
const val CODE_ERROR_USER_PHONE_REGISTERED = -3
const val CODE_ERROR_USER_INVALID_TOKEN = -5