package com.khapp.suji.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.khapp.suji.utils.Utils

@Entity(tableName = "transaction_info")
data class TransactionInfo(

    @field:SerializedName("value") val value: Float,
    @field:SerializedName("uid") val uid: Long,
    @field:SerializedName("lat") val lat: Double = 0.0,
    @field:SerializedName("lng") val lng: Double = 0.0,
    @field:SerializedName("address") val address: String = "",
    @field:SerializedName("dataTypeId") val dataTypeId: Int,
    @field:SerializedName("dataTypeName") val dataTypeName: String,
    @field:SerializedName("dataTypeIcon") val dataTypeIcon: Int,
    @field:SerializedName("dataTypeValue") val dataTypeValue: Int,
    @field:SerializedName("createTime") val createTime: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) @field:SerializedName("id") val id: Long = 0
)