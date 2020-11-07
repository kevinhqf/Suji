package com.khapp.suji.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.khapp.suji.database.dao.TransactionDao
import com.khapp.suji.database.entity.TransactionDetail
import com.khapp.suji.view.comm.BaseRepository

class TransactionRepository(private val transactionDao: TransactionDao) : BaseRepository() {

    fun loadTransactionsByUid(uid: Long): LiveData<PagedList<TransactionDetail>> {
        return LivePagedListBuilder(
            transactionDao.loadTransactionDetailByUid(uid),
            PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(10)
                .setInitialLoadSizeHint(20).build()
        ).build()

    }
}