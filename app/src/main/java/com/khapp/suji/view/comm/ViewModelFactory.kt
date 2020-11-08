package com.khapp.suji.view.comm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khapp.suji.repository.AdditionRepository
import com.khapp.suji.repository.TransactionRepository
import com.khapp.suji.viewmodel.AdditionViewModel
import com.khapp.suji.viewmodel.TransactionViewModel

class AdditionViewModelFactory(private val repository: AdditionRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AdditionViewModel(repository) as T
    }
}

class TransactionViewModelFactory(private val repository: TransactionRepository):ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TransactionViewModel(repository) as T
    }
}