package com.khapp.suji.database.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khapp.suji.data.TransactionDetail
import com.khapp.suji.database.entity.TransactionInfo

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg data: TransactionInfo)

    @Query(
        "SELECT * FROM transaction_info WHERE transaction_info.uid = :uid ORDER BY transaction_info.createTime DESC LIMIT :start,:pageSize"
    )
    fun loadTransactionByUid(uid: Long, start: Int, pageSize: Int): List<TransactionInfo>

    @Query(
        "SELECT * FROM transaction_info WHERE transaction_info.uid = :uid ORDER BY transaction_info.createTime DESC"
    )
    fun loadTransactionByUid(uid: Long): DataSource.Factory<Int, TransactionInfo>

    @Query(
        "SELECT * FROM transaction_info WHERE transaction_info.uid = :uid AND transaction_info.createTime BETWEEN :start AND :end"
    )
    fun getTransactionByTime(uid: Long, start: Long, end: Long): LiveData<List<TransactionInfo>>

    @Query(
        "SELECT * FROM transaction_info WHERE transaction_info.uid = :uid AND transaction_info.createTime BETWEEN :start AND :end"
    )
    suspend fun getTransactionListByTime(uid: Long, start: Long, end: Long): List<TransactionInfo>

}
