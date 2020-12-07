package com.khapp.suji.view.settings

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.khapp.suji.Constance
import com.khapp.suji.R
import com.khapp.suji.preset.Currency
import com.khapp.suji.utils.ScreenUtils
import kotlinx.android.synthetic.main.dialog_button_action.*
import kotlinx.android.synthetic.main.dialog_select_currency.*

class SelectCurrencyDialog(context: Context) : BottomSheetDialog(context, R.style.Dialog) {
    lateinit var currencyAdapter: CurrencyAdapter
    var currency = Constance.config.currency

    init {
        setContentView(R.layout.dialog_select_currency)
        behavior.setPeekHeight(ScreenUtils.getHeight(context), true)
        dismissWithAnimation = true
        initViews()
        initListeners()
    }

    private fun initListeners() {
        dialog_close.setOnClickListener {
            cancel()
        }
        currencyAdapter.onSelectCurrencyListener = object :CurrencyAdapter.OnSelectCurrencyListener{
            override fun onSelect(currency: Currency) {
                this@SelectCurrencyDialog.currency = currency
            }

        }
    }

    private fun initViews() {
        currencyAdapter = CurrencyAdapter(context)
        dsc_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dsc_rv.adapter = currencyAdapter
    }

    fun setOKListener(onCurrencyChange: (Currency) -> Unit): SelectCurrencyDialog {
        dialog_ok.setOnClickListener {
            if (currency != Constance.config.currency) {
                onCurrencyChange(currency)
                cancel()
            }
        }
        return this
    }

}