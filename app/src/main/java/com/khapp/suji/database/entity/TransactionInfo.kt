package com.khapp.suji.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.khapp.suji.utils.Utils

@Entity(tableName = "transaction_info")
data class TransactionInfo(
    @ColumnInfo(name = "dataTypeId") val dataTypeId: Int,
    @ColumnInfo(name = "value") val value: Float,
    @ColumnInfo(name = "uid") val uid: Long,
    @ColumnInfo(name = "lat") val lat: Double = 0.0,
    @ColumnInfo(name = "lng") val lng: Double = 0.0,
    @ColumnInfo(name = "address") val address: String = "",
    @ColumnInfo(name = "createTime") val createTime: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)