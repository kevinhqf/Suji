package com.khapp.suji.view.addition

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.khapp.suji.R
import com.khapp.suji.utils.IconSet
import com.khapp.suji.utils.ScreenUtils
import com.khapp.suji.utils.Utils
import kotlinx.android.synthetic.main.dialog_select_icon.*

class SelectIconDialog(context: Context) : BottomSheetDialog(context, R.style.Dialog) {
    lateinit var adapter: IconAdapter
    var selectedIconId: Int = -1

    init {
        setContentView(R.layout.dialog_select_icon)
        behavior.setPeekHeight(ScreenUtils.getHeight(context), true)
        dismissWithAnimation = true
        initViews()
        initListeners()
    }

    private fun initListeners() {
        dsi_iv_close.setOnClickListener {
            cancel()
        }
        adapter.selectIconListener = object : IconAdapter.OnSelectIconListener {
            override fun onSelect(iconId: Int) {
                selectedIconId = iconId

            }

        }
    }

    private fun initViews() {
        adapter = IconAdapter(context, IconSet.getIconSet())
        dsi_rv_icons.layoutManager = GridLayoutManager(context, 3)
        dsi_rv_icons.adapter = adapter
    }


    fun setOKListener(onOK: (Int) -> Unit): SelectIconDialog {
        dsi_tv_ok.setOnClickListener {
            if (selectedIconId != -1) {
                onOK.invoke(selectedIconId)
                cancel()
            }else{
                Utils.toast(context,"请选择图标")
            }
        }
        return this
    }


}