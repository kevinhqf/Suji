package com.khapp.suji.view.settings

import android.content.Context
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.khapp.suji.Constance
import com.khapp.suji.R
import com.khapp.suji.preset.Language
import com.khapp.suji.utils.ScreenUtils
import kotlinx.android.synthetic.main.dialog_button_action.*
import kotlinx.android.synthetic.main.dialog_select_language.*

class SelectLanguageDialog(context: Context) : BottomSheetDialog(context, R.style.Dialog) {
    var language: Language = Constance.config.language

    init {
        setContentView(R.layout.dialog_select_language)
        behavior.setPeekHeight(ScreenUtils.getHeight(context), true)
        dismissWithAnimation = true
        initViews()
        initListeners()
    }

    private fun initListeners() {
        dsl_item_english.setOnClickListener { selectItem(it, Language.ENG) }
        dsl_item_chinese.setOnClickListener { selectItem(it, Language.CHN) }
        dialog_close.setOnClickListener { cancel() }
    }

    private fun initViews() {
        val view = when (language) {
            Language.CHN -> dsl_item_chinese
            Language.ENG -> dsl_item_english
        }
        selectItem(view, language)
    }

    private fun selectItem(view: View, language: Language) {
        clearItemState()
        view.setBackgroundResource(R.drawable.bg_dialog_setting_item_selected)
        this.language = language
    }

    private fun clearItemState() {
        dsl_item_chinese.background = null
        dsl_item_english.background = null
    }

    fun setOKListener(onLanguageChange: (Language) -> Unit): SelectLanguageDialog {
        dialog_ok.setOnClickListener {
            if (language != Constance.config.language) {
                onLanguageChange(language)
                cancel()
            }
        }
        return this
    }
}