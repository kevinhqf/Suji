package com.khapp.suji.view.settings

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.util.Util
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.khapp.suji.R
import com.khapp.suji.data.UserResponse
import com.khapp.suji.ui.LoadingDialog
import com.khapp.suji.utils.IconSet
import com.khapp.suji.utils.ScreenUtils
import com.khapp.suji.utils.Utils
import com.khapp.suji.view.addition.SelectIconDialog
import com.khapp.suji.viewmodel.MainViewModel
import com.khapp.suji.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.dialog_profile.*

class UserProfileDialog(context: Context, private val viewModel: UserViewModel) :
    BottomSheetDialog(context, R.style.Dialog) {
    private var selectIconDialog: SelectIconDialog? = null
    var user: UserResponse? = null
    var avatarPath: String = ""
    var loadingDialog: LoadingDialog? = null

    init {
        setContentView(R.layout.dialog_profile)
        behavior.setPeekHeight(ScreenUtils.getHeight(context), true)
        dismissWithAnimation = true

        initListeners()

    }


    private fun initListeners() {
        dp_btn_next.setOnClickListener {
            val email = dp_et_email.editableText.toString()
            val name = dp_et_name.editableText.toString()
            if (viewModel.checkName(name) { Utils.toast(context, context.getString(R.string.user_name_tip)) }
                && viewModel.checkEmail(email) { Utils.toast(context, context.getString(R.string.email_tip)) }) {
                user?.let {
                    if (loadingDialog == null)
                        loadingDialog = LoadingDialog(context)
                    loadingDialog?.show()
                    viewModel.changeUserProfile(
                        it.id,
                        it.token,
                        name,
                        email,
                        avatarPath,
                        success = {
                            Utils.toast(context,context.getString(R.string.save_success))
                            cancel()
                        },
                        error = { code, msg ->
                            Utils.toast(context, "$code:$msg")
                        }) {
                        loadingDialog?.dismiss()
                    }
                }
            }

        }
        dp_change_avatar.setOnClickListener {
            if (selectIconDialog == null) {
                selectIconDialog = SelectIconDialog(context, IconSet.avatarsIcons)
            }
            selectIconDialog?.setOKListener {
                avatarPath = it.path
                Glide.with(context).load(it.getUrl())
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .error(R.mipmap.icon_user)
                    .placeholder(R.mipmap.icon_user)
                    .into(dp_iv_avatar)
            }?.show()
        }
    }


    private fun initViews() {
        viewModel.user.value?.let {
            user = it
            Glide.with(context).load(it.getAvatarUrl())
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .error(R.mipmap.icon_user)
                .placeholder(R.mipmap.icon_user)
                .into(dp_iv_avatar)
            dp_et_phone.setText(it.phone)
            dp_et_name.setText(it.name)
            dp_et_email.setText(it.email)
        }
    }

    override fun show() {
        super.show()
        viewModel.loadAvatars()
        initViews()
    }
}