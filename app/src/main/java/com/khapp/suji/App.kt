package com.khapp.suji

import android.app.Application
import android.preference.Preference
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import com.khapp.suji.datastore.AppDataStore
import com.khapp.suji.preset.Theme
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlin.properties.Delegates

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        runBlocking {
            val mode = when(AppDataStore(instance).getTheme().first()){
                Theme.AUTO -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                Theme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                Theme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            }
            AppCompatDelegate.setDefaultNightMode(mode)
        }


    }


    companion object {

        private var instance: App by Delegates.notNull()
        fun instance() = instance
    }

}