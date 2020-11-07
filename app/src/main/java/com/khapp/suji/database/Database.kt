package com.khapp.suji.database

import androidx.room.Database
import androidx.room.Room
import com.khapp.suji.App
import com.khapp.suji.data.DbResult

object Database {
    private val instance: AppDatabase by lazy {
        Room.databaseBuilder(App.instance(), AppDatabase::class.java, "suji").build()
    }

    val dataTypeDao = instance.dataTypeDao()
    val transactionDao = instance.transactionDao()
    val DB_ERROR=DbResult(-1,"数据库错误",null)
}