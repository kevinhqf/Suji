package com.khapp.suji.api

import com.khapp.suji.data.ApiResponse
import com.khapp.suji.data.Resources
import com.khapp.suji.data.UserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("api/resources/icons")
    suspend fun getIcons(): ApiResponse<List<Resources>>

    @GET("api/resources/avatar")
    suspend fun getAvatar(): ApiResponse<List<Resources>>

    @FormUrlEncoded
    @POST("api/user/find")
    suspend fun checkPhone(@Field("phone") phone: String): ApiResponse<Int>

    @FormUrlEncoded
    @POST("api/user/login")
    suspend fun login(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): ApiResponse<UserResponse>

    @FormUrlEncoded
    @POST("api/user/signup")
    suspend fun signup(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): ApiResponse<UserResponse>

    @FormUrlEncoded
    @POST("api/user/refreshToken")
    suspend fun refreshToken(
        @Field("uid") uid: Long,
        @Field("token") token: String
    ): ApiResponse<String>

    @FormUrlEncoded
    @POST("api/user/changeInfo")
    suspend fun changeInfo(
        @Field("uid") uid: Long,
        @Field("token") token: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("avatar") avatar: String,
        @Field("password") password: String
    ): ApiResponse<UserResponse>

}