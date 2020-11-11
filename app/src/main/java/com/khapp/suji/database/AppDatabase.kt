package com.khapp.suji.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.khapp.suji.database.dao.DataTypeDao
import com.khapp.suji.database.dao.TransactionDao
import com.khapp.suji.database.entity.DataType
import com.khapp.suji.database.entity.TransactionInfo

@Database(entities = [DataType::class,TransactionInfo::class],exportSchema = false,version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun dataTypeDao():DataTypeDao
    abstract fun transactionDao():TransactionDao
}