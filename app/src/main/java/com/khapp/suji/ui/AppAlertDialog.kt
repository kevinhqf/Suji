package com.khapp.suji.ui

import android.app.Dialog
import android.content.Context
import com.khapp.suji.R

import kotlinx.android.synthetic.main.dialog_alert.*

class AppAlertDialog(context: Context) : Dialog(context, R.style.Dialog) {

    init {
        setContentView(R.layout.dialog_alert)
        cancel(context.getString(R.string.cancel_text))
    }

    fun message(message: String): AppAlertDialog {
        alert_tv_title.text = message
        return this
    }

    fun ok(text: String, onOK: () -> Unit): AppAlertDialog {
        alert_tv_ok.text = text
        alert_tv_ok.setOnClickListener {
            onOK()
            dismiss()
        }
        return this
    }

    fun cancel(text: String, onCancel: () -> Unit = {}): AppAlertDialog {
        alert_tv_cancel.text = text
        alert_tv_cancel.setOnClickListener {
            onCancel()
            dismiss()
        }
        return this
    }
}