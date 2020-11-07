package com.khapp.suji.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import com.khapp.suji.repository.TransactionRepository
import com.khapp.suji.view.comm.BaseViewModel

class TransactionViewModel(private val repository: TransactionRepository) : BaseViewModel() {

    val uid = MutableLiveData<Long>()
    private val transactionResult = map(uid) {
        repository.loadTransactionsByUid(it)
    }
    val transactionList = switchMap(transactionResult) { it }

    fun loadUid(uid:Long){
        this.uid.postValue(uid)
    }
}