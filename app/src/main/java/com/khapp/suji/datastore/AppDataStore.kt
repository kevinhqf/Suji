package com.khapp.suji.datastore

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.khapp.suji.App
import com.khapp.suji.Constance
import com.khapp.suji.data.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object AppDataStore {
    private val dataStore: DataStore<Preferences> = App.instance().createDataStore("suji_data")

    private val USER_ID = preferencesKey<Long>("user_id")
    private val USER_NAME = preferencesKey<String>("user_name")
    private val USER_PHONE = preferencesKey<String>("user_phone")
    private val USER_EMAIL = preferencesKey<String>("user_email")
    private val USER_AVATAR_URL = preferencesKey<String>("user_avatar_url")
    private val USER_TOKEN = preferencesKey<String>("user_token")


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
        }

    }

    suspend fun loadUser() {
        val u = dataStore.data.map {
            UserResponse(
                it[USER_ID]?: -1L,
                it[USER_PHONE] ?: "",
                it[USER_NAME] ?: "",
                it[USER_AVATAR_URL] ?: "",
                it[USER_EMAIL] ?: "",
                token = it[USER_TOKEN] ?: ""
            )
        }.first()
        Constance.user = u
        Constance.userId = u.id
    }

}