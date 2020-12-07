package com.khapp.suji.view.comm

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.khapp.suji.App
import com.khapp.suji.datastore.AppDataStore
import com.khapp.suji.utils.InjectorUtils
import com.khapp.suji.utils.LanguageUtils
import com.khapp.suji.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

abstract class BaseActivity(private val layoutId: Int) : AppCompatActivity() {

    val mainViewModel: MainViewModel by viewModels {
        InjectorUtils.provideMainViewModelFactory()
    }

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

    override fun attachBaseContext(newBase: Context?) {
        runBlocking {
            super.attachBaseContext(
                LanguageUtils.attachBaseContext(
                    newBase,
                    AppDataStore(App.instance()).getLanguage().first()
                )
            )
        }


    }

    abstract fun beforeSetContent()

    abstract fun lastOnCreate()

    abstract fun initData()

    abstract fun initViews()

    abstract fun initListeners()

    abstract fun initObservers()


}