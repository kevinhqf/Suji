package com.khapp.suji.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.khapp.suji.utils.Utils

@Entity(tableName = "data_type")
data class DataType(
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "icon_id") var icon: Int,
    @ColumnInfo(name = "type") var type: Int,
    @ColumnInfo(name = "uid") var uid: Long,
    @ColumnInfo(name="last_use_time")var lastUseTime:Long=System.currentTimeMillis(),
    @ColumnInfo(name = "create_time") val createTime: Long=System.currentTimeMillis()
){
}