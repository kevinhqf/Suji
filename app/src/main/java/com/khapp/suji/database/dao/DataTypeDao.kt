package com.khapp.suji.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khapp.suji.database.entity.DataType

@Dao
interface DataTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg dataType: DataType)

    @Query("SELECT * FROM data_type WHERE uid = :uid ORDER BY last_use_time DESC")
    fun loadByUid(uid: Long): LiveData<List<DataType>>

    @Query("UPDATE data_type SET last_use_time = :useTime WHERE id=:id")
    fun updateUseTime(useTime: Long, id: Int)

}