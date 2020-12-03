package com.khapp.suji.view.comm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khapp.suji.repository.AdditionRepository
import com.khapp.suji.repository.LoginRepository
import com.khapp.suji.repository.TransactionRepository
import com.khapp.suji.viewmodel.AdditionViewModel
import com.khapp.suji.viewmodel.LoginViewModel
import com.khapp.suji.viewmodel.MainViewModel
import com.khapp.suji.viewmodel.TransactionViewModel

class AdditionViewModelFactory(private val repository: AdditionRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AdditionViewModel(repository) as T
    }
}

class TransactionViewModelFactory(private val repository: TransactionRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TransactionViewModel(repository) as T
    }
}

class LoginViewModelFactory(private val repository: LoginRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}

class MainViewModelFactory() : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel() as T
    }
}