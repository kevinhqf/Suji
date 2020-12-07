package com.khapp.suji.view.comm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khapp.suji.repository.AdditionRepository
import com.khapp.suji.repository.MainRepository
import com.khapp.suji.repository.UserRepository
import com.khapp.suji.repository.TransactionRepository
import com.khapp.suji.viewmodel.AdditionViewModel
import com.khapp.suji.viewmodel.UserViewModel
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

class UserViewModelFactory(private val repository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(repository) as T
    }
}

class MainViewModelFactory(private val mainRepository: MainRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(mainRepository) as T
    }
}