package com.khapp.suji.view.comm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(private val layoutId:Int): AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeSetContent()
        setContentView(layoutId)
        initData()
        initViews()
        initListeners()
        initObservers()
        lastOnCreate()
    }

    abstract fun beforeSetContent()

    abstract fun lastOnCreate()

    abstract fun initData()

    abstract fun initViews()

    abstract fun initListeners()

    abstract fun initObservers()


}