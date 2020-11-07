package com.khapp.suji.view.comm

import com.khapp.suji.api.API
import com.khapp.suji.data.ApiResponse
import com.khapp.suji.data.DbResult
import com.khapp.suji.database.Database

open class BaseRepository {
    suspend fun <T : Any?> apiCall(call: suspend () -> ApiResponse<T>): ApiResponse<T> {
        return try {
            call.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
            API.ERROR
        }
    }



}