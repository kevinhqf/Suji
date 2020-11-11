package com.khapp.suji.viewmodel

import androidx.lifecycle.MutableLiveData
import com.khapp.suji.Constance
import com.khapp.suji.data.NoteType
import com.khapp.suji.data.MoneyOverview
import com.khapp.suji.database.entity.TransactionInfo
import com.khapp.suji.repository.TransactionRepository
import com.khapp.suji.view.comm.BaseViewModel

class TransactionViewModel(private val repository: TransactionRepository) : BaseViewModel() {

    val transactionList = repository.loadTransactionsByUid(Constance.userId)
    val todayTransaction = repository.getTodayTransaction(Constance.userId)
    val todayOverview = MutableLiveData<MoneyOverview>()

    fun calcTodayMoney(list: List<TransactionInfo>) {
        todayOverview.postValue(calcMoneyOverview(list))

    }

    fun calcMoneyOverview(list: List<TransactionInfo>): MoneyOverview {
        val incomeValue =
            list.filter { it.dataTypeValue == NoteType.INCOME.value }?.fold(0f) { acc, info ->
                acc + info.value
            }
        val expenseValue =
            list.filter { it.dataTypeValue == NoteType.EXPENSE.value }?.fold(0f) { acc, info ->
                acc + info.value
            }
        return MoneyOverview(
            incomeValue,
            expenseValue,
            incomeValue - expenseValue
        )
    }


}