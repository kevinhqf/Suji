package com.khapp.suji.view.login

import android.content.Intent
import android.text.InputType
import android.view.Window
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.khapp.suji.*
import com.khapp.suji.ui.LoadingDialog
import com.khapp.suji.utils.InjectorUtils
import com.khapp.suji.view.comm.BaseActivity
import com.khapp.suji.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_password.*
import kotlinx.android.synthetic.main.widget_back.*

class PasswordActivity : BaseActivity(R.layout.activity_password) {

    companion object {
        const val KEY_PHONE = "phone"
        const val KEY_ACTION_CODE = "action_code"
    }

    var phone: String = ""
    var code: Int = -1
    private val userViewMode: UserViewModel by viewModels {
        InjectorUtils.provideUserViewModelFactory()
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
            if (code == CODE_USER_LOGIN) getString(R.string.login_text) else if (code == CODE_USER_SIGNUP) getString(
                            R.string.signup_text) else ""
        if (code == CODE_USER_SIGNUP) {
            switchEditTextInputType()
        }
        ap_tv_tip.text = getString(R.string.password_tip)
        ap_et_password.requestFocus()
    }

    override fun initListeners() {
        ap_iv_eye.setOnClickListener {
            switchEditTextInputType()
        }
        iv_back.setOnClickListener { onBackPressed() }
        ap_et_password.addTextChangedListener {
            userViewMode.checkPasswordValid(it.toString())
        }

        ap_btn_ok.setOnClickListener {
            loading.show()
            userViewMode.passwordAction(phone, ap_et_password.editableText.toString(), code, {
                // 跳转个人页
                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    ).apply {
                        putExtra(
                            MainActivity.KEY_MENU_POSITION,
                            MainActivity.SETTINGS_POSITION
                        )
                    })
            }, { code, msg ->
                if (code == CODE_ERROR_USER_WRONG_PASSWORD) {
                    setTip(getString(R.string.wrong_password_tip))
                } else {
                    setTip(msg)
                }
            }) { loading.dismiss() }
        }

    }

    private fun setTip(tip: String) {
        ap_tv_tip.text = tip
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
        userViewMode.passwordValid.observe(this, Observer {
            ap_btn_ok.isEnabled = it
            if (it==false){
                setTip(getString(R.string.password_tip))
            }
        })
    }

}