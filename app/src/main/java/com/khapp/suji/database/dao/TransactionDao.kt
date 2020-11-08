package com.khapp.suji.database.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khapp.suji.database.entity.TransactionDetail
import com.khapp.suji.database.entity.TransactionInfo

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg data:TransactionInfo)

    @Query("SELECT transaction_info.id,transaction_info.uid,transaction_info.value,transaction_info.address,transaction_info.createTime, data_type.id AS dataTypeId,data_type.name AS dataTypeName,data_type.icon_id AS dataTypeIcon,data_type.type AS dataTypeValue FROM transaction_info,data_type WHERE transaction_info.dataTypeId = data_type.id AND transaction_info.uid = :uid ORDER BY transaction_info.createTime DESC"
    )
    fun loadTransactionDetailByUid(uid:Long):DataSource.Factory<Int,TransactionDetail>


}