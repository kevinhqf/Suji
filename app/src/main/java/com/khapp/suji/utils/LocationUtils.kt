package com.khapp.suji.utils

import android.content.Context
import android.os.Looper
import com.tencent.map.geolocation.TencentLocationListener
import com.tencent.map.geolocation.TencentLocationManager

object LocationUtils {
    fun requestLocation(context: Context, listener: TencentLocationListener) {
        val instance = TencentLocationManager.getInstance(context)
        instance.requestSingleFreshLocation(null, listener, Looper.getMainLooper())
    }
}