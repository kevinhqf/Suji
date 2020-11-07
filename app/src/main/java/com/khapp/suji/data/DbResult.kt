package com.khapp.suji.data

data class DbResult<out T>(val code: Int, val msg: String, val data: T?)