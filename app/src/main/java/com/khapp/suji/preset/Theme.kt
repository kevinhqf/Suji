package com.khapp.suji.preset

import android.content.Context
import com.khapp.suji.App
import com.khapp.suji.R
import java.io.FileDescriptor

enum class Theme(val id: Int, val description: String, val value: Int) {
    LIGHT(1, App.instance().getString(R.string.light_text), 0),
    DARK(2, App.instance().getString(R.string.dark_text), 1),
    AUTO(3, App.instance().getString(R.string.auto_text), 2);

    fun getDescriptionStr(context: Context): String {
        return when (id) {
            1 -> context.getString(R.string.light_text)
            2 -> context.getString(R.string.dark_text)
            3 -> context.getString(R.string.auto_text)
            else -> ""
        }

    }
}