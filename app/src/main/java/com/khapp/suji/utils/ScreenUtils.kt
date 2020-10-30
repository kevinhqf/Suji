package com.khapp.suji.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

object ScreenUtils {
    fun getWidth(context: Context): Int {
        val wm:WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }
    fun getHeight(context: Context): Int {
        val wm:WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
    }
}