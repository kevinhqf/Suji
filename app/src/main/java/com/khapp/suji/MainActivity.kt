package com.khapp.suji

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.khapp.suji.view.addition.AdditionDialog
import com.khapp.suji.viewmodel.AdditionViewModel

class MainActivity : AppCompatActivity() {
    private val additionViewModel: AdditionViewModel by viewModels()
    private lateinit var additionDialog: AdditionDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initListeners()
        initObservers()
        additionDialog.show()
    }

    private fun initObservers() {
        additionViewModel.newValues.observe(this, Observer {
            additionDialog.updateMoney(it)
        })
    }

    private fun initListeners() {
        additionDialog.initListeners(additionViewModel)
    }

    private fun initViews() {
        additionDialog = AdditionDialog(this)

    }
}