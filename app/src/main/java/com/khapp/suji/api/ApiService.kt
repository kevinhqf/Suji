package com.khapp.suji.api

import com.khapp.suji.data.ApiResponse
import com.khapp.suji.data.Resources
import retrofit2.http.GET

interface ApiService {
    @GET("api/resources/icons")
    suspend fun getIcons():ApiResponse<List<Resources>>
}