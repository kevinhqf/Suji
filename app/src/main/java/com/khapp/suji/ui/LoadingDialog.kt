package com.khapp.suji.ui

import android.app.Dialog
import android.content.Context
import com.khapp.suji.R

class LoadingDialog(context: Context) : Dialog(context, R.style.Dialog) {

    init {
        setContentView(R.layout.loading)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }




}