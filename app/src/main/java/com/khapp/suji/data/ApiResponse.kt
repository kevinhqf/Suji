package com.khapp.suji.data

data class ApiResponse<out T>(val code: Int, val msg: String, val data: T?)