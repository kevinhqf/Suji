package com.khapp.suji.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.khapp.suji.database.dao.TransactionDao
import com.khapp.suji.database.entity.TransactionInfo
import com.khapp.suji.utils.Utils
import com.khapp.suji.view.comm.BaseRepository

class TransactionRepository(private val transactionDao: TransactionDao) : BaseRepository() {

    fun loadTransactionsByUid(uid: Long): LiveData<PagedList<TransactionInfo>> {
        return transactionDao.loadTransactionByUid(uid).toLiveData(pageSize = 50)

    }

    fun getTodayTransaction(uid: Long):LiveData<List<TransactionInfo>>{
        val today = Utils.getTodayTimeUnit()
        return transactionDao.getTransactionByTime(uid,today.start,today.end)
    }


}