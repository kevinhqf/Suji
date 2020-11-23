package com.khapp.suji.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.khapp.suji.data.AnalysisTimeUnit
import com.khapp.suji.database.dao.TransactionDao
import com.khapp.suji.database.entity.TransactionInfo
import com.khapp.suji.utils.DateUtils
import com.khapp.suji.utils.Utils
import com.khapp.suji.view.comm.BaseRepository

class TransactionRepository(private val transactionDao: TransactionDao) : BaseRepository() {

    fun loadTransactionsByUid(uid: Long): LiveData<PagedList<TransactionInfo>> {
        return transactionDao.loadTransactionByUid(uid).toLiveData(pageSize = 50)

    }

    fun getTodayTransaction(uid: Long): LiveData<List<TransactionInfo>> {
        val today = DateUtils.getTodayTimeUnit()
        return transactionDao.getTransactionByTime(uid, today.start, today.end)
    }


    suspend fun getThisWeekTransaction(uid: Long): List<TransactionInfo> {
        val thisWeek = DateUtils.getThisWeekTimeUnit()
        //Log.e("ThisWeekTransaction: ", "start=${thisWeek.start},end = ${thisWeek.end}")
        return transactionDao.getTransactionListByTime(uid, thisWeek.start, thisWeek.end)
    }

    suspend fun getThisMonthTransaction(uid: Long): List<TransactionInfo> {
        val thisMonth = DateUtils.getThisMonthTimeUnit()
        return transactionDao.getTransactionListByTime(uid, thisMonth.start, thisMonth.end)
    }

    suspend fun getLatelyHalfYearTransaction(uid: Long): List<TransactionInfo> {
        val latelyHalfYear = DateUtils.getLatelyHalfYearTimeUnit()
        return transactionDao.getTransactionListByTime(
            uid,
            latelyHalfYear.start,
            latelyHalfYear.end
        )
    }

    suspend fun getAnalysisTransaction(
        uid: Long,
        timeUnit: AnalysisTimeUnit
    ): List<TransactionInfo> {
        return when (timeUnit) {
            AnalysisTimeUnit.THIS_WEEK -> getThisWeekTransaction(uid)
            AnalysisTimeUnit.THIS_MONTH -> getThisMonthTransaction(uid)
            AnalysisTimeUnit.LATELY_HALF_YEAR -> getLatelyHalfYearTransaction(uid)
        }
    }


}