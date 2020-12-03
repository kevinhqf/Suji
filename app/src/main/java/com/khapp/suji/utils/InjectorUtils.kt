package com.khapp.suji.utils

import com.khapp.suji.App
import com.khapp.suji.database.Database
import com.khapp.suji.datastore.AppDataStore
import com.khapp.suji.repository.AdditionRepository
import com.khapp.suji.repository.LoginRepository
import com.khapp.suji.repository.TransactionRepository
import com.khapp.suji.view.comm.AdditionViewModelFactory
import com.khapp.suji.view.comm.LoginViewModelFactory
import com.khapp.suji.view.comm.MainViewModelFactory
import com.khapp.suji.view.comm.TransactionViewModelFactory
import com.khapp.suji.viewmodel.TransactionViewModel

object InjectorUtils {
    //保证dataStore是同一个对象
    private val dataStore = AppDataStore(App.instance())
    fun provideAdditionViewModelFactory(): AdditionViewModelFactory = AdditionViewModelFactory(
        AdditionRepository(Database.dataTypeDao, Database.transactionDao)
    )

    fun provideTransactionViewModelFactory(): TransactionViewModelFactory =
        TransactionViewModelFactory(
            TransactionRepository(Database.transactionDao)
        )

    fun provideLoginViewModelFactory(): LoginViewModelFactory = LoginViewModelFactory(
        LoginRepository(
            dataStore
        )
    )

    fun provideMainViewModelFactory(): MainViewModelFactory = MainViewModelFactory(
        LoginRepository(
            dataStore
        )
    )
}