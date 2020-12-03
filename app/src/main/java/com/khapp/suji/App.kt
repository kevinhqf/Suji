package com.khapp.suji

import android.app.Application
import android.preference.Preference
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import com.khapp.suji.datastore.AppDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

        GlobalScope.launch(Dispatchers.IO) {
            AppDataStore.loadUser()
        }

    }


    companion object {

        private var instance: App by Delegates.notNull()
        fun instance() = instance
    }

}