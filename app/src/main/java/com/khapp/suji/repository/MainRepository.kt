package com.khapp.suji.repository

import com.khapp.suji.Constance
import com.khapp.suji.datastore.AppDataStore
import com.khapp.suji.preset.Currency
import com.khapp.suji.preset.Language
import com.khapp.suji.preset.Theme
import com.khapp.suji.view.comm.BaseRepository

class MainRepository(private val dataStore: AppDataStore):BaseRepository() {


    fun readConfig() = dataStore.loadConfig()

    suspend fun saveConfig(theme: Theme, language: Language, currency: Currency){
        dataStore.saveConfig(theme, language, currency)
    }
}