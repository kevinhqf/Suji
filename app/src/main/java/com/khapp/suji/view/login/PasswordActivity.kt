package com.khapp.suji.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Window
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.khapp.suji.CODE_USER_LOGIN
import com.khapp.suji.CODE_USER_SIGNUP
import com.khapp.suji.MainActivity
import com.khapp.suji.R
import com.khapp.suji.repository.LoginRepository
import com.khapp.suji.ui.LoadingDialog
import com.khapp.suji.utils.InjectorUtils
import com.khapp.suji.utils.Utils
import com.khapp.suji.view.comm.BaseActivity
import com.khapp.suji.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_password.*
import kotlinx.android.synthetic.main.widget_back.*

class PasswordActivity : BaseActivity(R.layout.activity_password) {

    companion object {
        const val KEY_PHONE = "phone"
        const val KEY_ACTION_CODE = "action_code"
    }

    var phone: String = ""
    var code: Int = -1
    private val loginViewMode: LoginViewModel by viewModels {
        InjectorUtils.provideLoginViewModelFactory()
    }
    var canSee = false
    private lateinit var loading: LoadingDialog

    override fun beforeSetContent() {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
    }

    override fun lastOnCreate() {

    }

    override fun initData() {
        intent.apply {
            phone = getStringExtra(KEY_PHONE)
            code = getIntExtra(KEY_ACTION_CODE, -1)
        }
    }

    override fun initViews() {
        loading = LoadingDialog(this)
        ap_btn_ok.text =
            if (code == CODE_USER_LOGIN) "登 录" else if (code == CODE_USER_SIGNUP) "注 册" else ""
        if (code == CODE_USER_SIGNUP) {
            switchEditTextInputType()
            ap_tv_tip.text = "密码不少于8位且包含字母和数字"
        } else {
            ap_tv_tip.text = ""
        }
    }

    override fun initListeners() {
        ap_iv_eye.setOnClickListener {
            switchEditTextInputType()
        }
        iv_back.setOnClickListener { onBackPressed() }
        ap_et_password.addTextChangedListener {
            loginViewMode.checkPasswordValid(it.toString())
        }

        ap_btn_ok.setOnClickListener {
            loading.show()
            loginViewMode.passwordAction(phone, ap_et_password.editableText.toString(), code, {
                // 跳转个人页
                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    ).apply {
                        putExtra(
                            MainActivity.KEY_MENU_POSITION,
                            MainActivity.USER_POSITION
                        )
                    })
            }, { _, msg -> Utils.toast(this, msg) }) { loading.dismiss() }
        }

    }

    private fun switchEditTextInputType() {
        if (canSee) {
            //设置为不可见状态
            ap_iv_eye.setImageResource(R.mipmap.icon_can_see)
            ap_et_password.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        } else {
            //设置为可见状态
            ap_iv_eye.setImageResource(R.mipmap.icon_cannot_see)
            ap_et_password.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        }
        canSee = !canSee
        ap_et_password.setSelection(ap_et_password.editableText.length)
    }

    override fun initObservers() {
        loginViewMode.passwordValid.observe(this, Observer {
            ap_btn_ok.isEnabled = it
        })
    }

}