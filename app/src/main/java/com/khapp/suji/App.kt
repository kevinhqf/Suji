package com.khapp.suji

import android.app.Application
import kotlin.properties.Delegates

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    companion object {
        private var instance: App by Delegates.notNull()
        fun instance() = instance
    }

}