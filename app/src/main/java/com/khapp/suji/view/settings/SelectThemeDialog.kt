package com.khapp.suji.view.settings

import android.content.Context
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.khapp.suji.Config
import com.khapp.suji.R
import com.khapp.suji.preset.Theme
import com.khapp.suji.utils.ScreenUtils
import kotlinx.android.synthetic.main.dialog_button_action.*
import kotlinx.android.synthetic.main.dialog_select_theme.*

class SelectThemeDialog(context: Context) : BottomSheetDialog(context, R.style.Dialog) {
    var theme: Theme = Config.theme

    init {
        setContentView(R.layout.dialog_select_theme)
        behavior.setPeekHeight(ScreenUtils.getHeight(context), true)
        dismissWithAnimation = true
        initViews()
        initListeners()
    }

    private fun initListeners() {
        dst_item_light.setOnClickListener { selectItem(it, Theme.LIGHT) }
        dst_item_dark.setOnClickListener { selectItem(it, Theme.DARK) }
        dst_item_auto.setOnClickListener { selectItem(it, Theme.AUTO) }
        dialog_close.setOnClickListener { cancel() }

    }

    private fun initViews() {
        val view = when (theme) {
            Theme.LIGHT -> dst_item_light
            Theme.DARK -> dst_item_dark
            Theme.AUTO -> dst_item_auto
        }
        selectItem(view, theme)
    }

    private fun selectItem(view: View, theme: Theme) {
        clearItemState()
        view.setBackgroundResource(R.drawable.bg_dialog_setting_item_selected)
        this.theme = theme
    }

    fun setOKListener(onThemeChange: (Theme) -> Unit): SelectThemeDialog {
        dialog_ok.setOnClickListener {
            if (theme != Config.theme) {
                onThemeChange(theme)
                cancel()
            }
        }
        return this
    }


    private fun clearItemState() {
        dst_item_light.background = null
        dst_item_dark.background = null
        dst_item_auto.background = null
    }
}