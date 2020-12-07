package com.khapp.suji.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.khapp.suji.Constance
import com.khapp.suji.data.UserResponse
import com.khapp.suji.preset.AppConfig
import com.khapp.suji.preset.Currency
import com.khapp.suji.preset.Language
import com.khapp.suji.preset.Theme
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class AppDataStore(val context: Context) {
    private val dataStore: DataStore<Preferences> = context.createDataStore("suji_data")

    companion object {
        private val USER_ID = preferencesKey<Long>("user_id")
        private val USER_NAME = preferencesKey<String>("user_name")
        private val USER_PHONE = preferencesKey<String>("user_phone")
        private val USER_EMAIL = preferencesKey<String>("user_email")
        private val USER_AVATAR_URL = preferencesKey<String>("user_avatar_url")
        private val USER_TOKEN = preferencesKey<String>("user_token")


        private val CONFIG_THEME = preferencesKey<String>("config_theme")
        private val CONFIG_LANGUAGE = preferencesKey<String>("config_language")
        private val CONFIG_CURRENCY = preferencesKey<String>("config_currency")
    }


    suspend fun saveConfig(theme: Theme, language: Language, currency: Currency) {
        val config = AppConfig(theme, language, currency)
        Constance.config = config
        dataStore.edit {
            it[CONFIG_THEME] = theme.toString()
            it[CONFIG_LANGUAGE] = language.toString()
            it[CONFIG_CURRENCY] = currency.toString()
        }
    }

    fun getLanguage() = dataStore.data.catch {
        if (it is IOException) {
            it.printStackTrace()
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        val language = Language.valueOf(it[CONFIG_LANGUAGE] ?: "CHN")
        language
    }


    fun loadConfig() =
        dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            val currency = Currency.valueOf(it[CONFIG_CURRENCY] ?: "CNY")
            val theme = Theme.valueOf(it[CONFIG_THEME] ?: "AUTO")
            val language = Language.valueOf(it[CONFIG_LANGUAGE] ?: "CHN")
            val config = AppConfig(theme, language, currency)
            Constance.config = config
            config
        }


    suspend fun saveUserData(user: UserResponse) {
        Constance.user = user
        Constance.userId = user.id
        dataStore.edit {
            it[USER_ID] = user.id
            it[USER_NAME] = user.name
            it[USER_PHONE] = user.phone
            it[USER_EMAIL] = user.email
            it[USER_AVATAR_URL] = user.avatar
            it[USER_TOKEN] = user.token
            Log.e("saveUserData: ", "id${user.id}")
        }

    }

    fun loadUser() = dataStore.data.catch {
        if (it is IOException) {
            it.printStackTrace()
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        val u = UserResponse(
            it[USER_ID] ?: -1L,
            it[USER_PHONE] ?: "",
            it[USER_NAME] ?: "",
            it[USER_AVATAR_URL] ?: "",
            it[USER_EMAIL] ?: "",
            token = it[USER_TOKEN] ?: ""
        )
        Constance.user = u
        Constance.userId = u.id
        u
    }


}