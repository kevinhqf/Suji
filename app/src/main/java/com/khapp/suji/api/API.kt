package com.khapp.suji.api

import android.util.Log
import com.khapp.suji.App
import com.khapp.suji.data.ApiResponse
import com.khapp.suji.utils.NetworkUtils
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object API {
    val service by lazy { create() }
    private const val TIME_OUT = 5
    val ERROR = ApiResponse(-1, "服务器错误", null)
    private const val BASE_URL = "http://106.53.237.34:8080"
    private val client: OkHttpClient
        get() {
            val logger = HttpLoggingInterceptor {
                Log.i("OkHttp", it)
            }
            logger.level = HttpLoggingInterceptor.Level.BODY
            val builder = OkHttpClient.Builder().connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .addInterceptor(logger)
            handleBuilder(builder)
            return builder.build()
        }

    private fun handleBuilder(builder: OkHttpClient.Builder) {

        val httpCacheDirectory = File(App.instance().cacheDir, "responses")
        val cacheSize = 10 * 1024 * 1024L // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize)
        builder.cache(cache)

            .addInterceptor { chain ->
                var request = chain.request()
                if (!NetworkUtils.isNetworkAvailable(App.instance())) {
                    request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
                }
                val response = chain.proceed(request)
                if (!NetworkUtils.isNetworkAvailable(App.instance())) {
                    val maxAge = 60 * 60
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .build()
                } else {
                    val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
                }

                response
            }
    }

    private fun create(httpUrl: HttpUrl): ApiService = Retrofit.Builder().client(client).baseUrl(
        httpUrl).addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)
    private fun create() = create(BASE_URL.toHttpUrlOrNull()!!)
}