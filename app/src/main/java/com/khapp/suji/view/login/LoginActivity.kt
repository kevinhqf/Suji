package com.khapp.suji.view.login

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.khapp.suji.R
import com.khapp.suji.api.API
import com.khapp.suji.ui.LoadingDialog
import com.khapp.suji.utils.InjectorUtils
import com.khapp.suji.utils.Utils
import com.khapp.suji.view.comm.BaseActivity
import com.khapp.suji.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.widget_back.*
import kotlinx.android.synthetic.main.widget_numpad2.*

class LoginActivity : BaseActivity(R.layout.activity_login) {

    private val loginViewModel: LoginViewModel by viewModels {
        InjectorUtils.provideLoginViewModelFactory()
    }
    private lateinit var loading: LoadingDialog
    override fun beforeSetContent() {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
    }

    override fun lastOnCreate() {


    }

    override fun initData() {

    }

    override fun initViews() {
        loading = LoadingDialog(this)
    }

    override fun initListeners() {
        numpad2_0.setOnClickListener {
            loginViewModel.numpadAction("0")
        }
        numpad2_1.setOnClickListener {
            loginViewModel.numpadAction("1")
        }
        numpad2_2.setOnClickListener {
            loginViewModel.numpadAction("2")
        }
        numpad2_3.setOnClickListener {
            loginViewModel.numpadAction("3")
        }
        numpad2_4.setOnClickListener {
            loginViewModel.numpadAction("4")
        }
        numpad2_5.setOnClickListener {
            loginViewModel.numpadAction("5")
        }
        numpad2_6.setOnClickListener {
            loginViewModel.numpadAction("6")
        }
        numpad2_7.setOnClickListener {
            loginViewModel.numpadAction("7")
        }
        numpad2_8.setOnClickListener {
            loginViewModel.numpadAction("8")
        }
        numpad2_9.setOnClickListener {
            loginViewModel.numpadAction("9")
        }
        numpad2_del.setOnClickListener {
            loginViewModel.numpadDel()
        }
        iv_back.setOnClickListener { onBackPressed() }
        al_btn_next.setOnClickListener {
            showLoading()
            loginViewModel.checkPhone { phone, code ->
                dismissLoading()
                if (code == API.ERROR.code) {
                    Utils.toast(this, API.ERROR.msg)
                } else {
                    startActivity(
                        Intent(this, PasswordActivity::class.java).apply {
                            putExtra(PasswordActivity.KEY_PHONE, phone)
                            putExtra(PasswordActivity.KEY_ACTION_CODE, code)
                        },
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
                    )
                }
            }
        }
    }

    private fun showLoading() {
        loading.show()
    }

    private fun dismissLoading() {
        loading.dismiss()
    }

    override fun initObservers() {
        loginViewModel.phone.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                al_tv_phone.text = LoginViewModel.DEFAULT_PHONE
                al_tv_phone.setTextColor(Color.parseColor("#D2D2D4"))
            } else {
                al_tv_phone.text = it
                al_tv_phone.setTextColor(Color.parseColor("#D9000000"))
                al_btn_next.isEnabled = it.length == LoginViewModel.DEFAULT_PHONE.length

            }
        })
    }

}