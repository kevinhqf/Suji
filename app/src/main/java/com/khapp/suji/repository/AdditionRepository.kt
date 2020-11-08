package com.khapp.suji.repository

import androidx.lifecycle.LiveData
import com.khapp.suji.data.DbResult
import com.khapp.suji.database.Database
import com.khapp.suji.database.dao.DataTypeDao
import com.khapp.suji.database.dao.TransactionDao
import com.khapp.suji.database.entity.DataType
import com.khapp.suji.database.entity.TransactionInfo
import com.khapp.suji.view.comm.BaseRepository

class AdditionRepository(private val dtDao: DataTypeDao,private val transactionDao: TransactionDao) : BaseRepository() {
    suspend fun addDataType(data: DataType) {
        dtDao.insert(data)
    }

    fun loadDataTypeByUid(uid: Long): LiveData<List<DataType>> {
        return dtDao.loadByUid(uid)
    }

    suspend fun addTransaction(data:TransactionInfo){
        transactionDao.insert(data)
    }

     fun updateDataTypeUseTime(useTime:Long,dataTypeId:Int){
        dtDao.updateUseTime(useTime,dataTypeId)
    }
}