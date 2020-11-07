package com.khapp.suji.database.entity


data class TransactionDetail(
    val id: Long,
    val uid: Long,
    val value: Float,
    val address: String,
    val createTime: String,
    val dataTypeId: Int,
    val dataTypeName: String,
    val dataTypeIcon: Int,
    val dataTypeValue: Int
)