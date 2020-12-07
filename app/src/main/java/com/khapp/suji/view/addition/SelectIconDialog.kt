package com.khapp.suji.view.addition

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.khapp.suji.R
import com.khapp.suji.data.Resources
import com.khapp.suji.utils.ScreenUtils
import com.khapp.suji.utils.Utils
import kotlinx.android.synthetic.main.dialog_button_action.*
import kotlinx.android.synthetic.main.dialog_select_icon.*

class SelectIconDialog(context: Context,private val icons:List<Resources>) : BottomSheetDialog(context, R.style.Dialog) {
    lateinit var adapter: IconAdapter
    var selectedIcon: Resources? = null

    init {
        setContentView(R.layout.dialog_select_icon)
        behavior.setPeekHeight(ScreenUtils.getHeight(context), true)
        dismissWithAnimation = true
        initViews()
        initListeners()
    }

    private fun initListeners() {
        dialog_close.setOnClickListener {
            cancel()
        }
        adapter.selectIconListener = object : IconAdapter.OnSelectIconListener {
            override fun onSelect(icon: Resources) {
                selectedIcon = icon

            }

        }
    }

    private fun initViews() {
        adapter = IconAdapter(context)
        adapter.data = icons
        dsi_rv_icons.layoutManager = GridLayoutManager(context, 3)
        dsi_rv_icons.adapter = adapter
    }


    fun setOKListener(onOK: (Resources) -> Unit): SelectIconDialog {
        dialog_ok.setOnClickListener {
            if (selectedIcon!=null) {
                onOK.invoke(selectedIcon!!)
                cancel()
            }else{
                Utils.toast(context,context.getString(R.string.select_icon_tip))
            }
        }
        return this
    }


}