package com.khapp.suji.utils

import com.khapp.suji.database.Database
import com.khapp.suji.repository.AdditionRepository
import com.khapp.suji.repository.TransactionRepository
import com.khapp.suji.view.comm.AdditionViewModelFactory
import com.khapp.suji.view.comm.TransactionViewModelFactory
import com.khapp.suji.viewmodel.TransactionViewModel

object InjectorUtils {
    fun provideAdditionViewModelFactory():AdditionViewModelFactory= AdditionViewModelFactory(
        AdditionRepository(Database.dataTypeDao,Database.transactionDao)
    )

    fun provideTransactionViewModelFactory():TransactionViewModelFactory = TransactionViewModelFactory(
        TransactionRepository(Database.transactionDao)
    )
}