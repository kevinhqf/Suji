package com.khapp.suji.view.addition

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.khapp.suji.R
import com.khapp.suji.data.DataType
import com.khapp.suji.data.NoteType
import com.khapp.suji.utils.IconSet
import com.khapp.suji.utils.ScreenUtils
import com.khapp.suji.utils.Utils
import kotlinx.android.synthetic.main.dialog_add_type.*
import kotlinx.android.synthetic.main.dialog_button_action.*

class AddTypeDialog(context: Context) : BottomSheetDialog(context, R.style.Dialog) {
    var tvExpense: TextView? = null
    var ivExpense: ImageView? = null
    var tvIncome: TextView? = null
    var ivIncome: ImageView? = null
    var switcher: View? = null
    var dataType = NoteType.EXPENSE
    var dataName: String = ""
    var dataIconUrl: String = ""

    init {
        setContentView(R.layout.dialog_add_type)
        behavior.setPeekHeight(ScreenUtils.getHeight(context), true)
        dismissWithAnimation = true
        initViews()
        initListeners()
    }

    private fun initViews() {
        tvExpense = findViewById(R.id.dat_tv_expense)
        tvIncome = findViewById(R.id.dat_tv_income)
        ivExpense = findViewById(R.id.dat_iv_expense)
        ivIncome = findViewById(R.id.dat_iv_income)
        switcher = findViewById(R.id.dat_switcher)
    }

    fun setOKListener(onOK: (DataType) -> Unit): AddTypeDialog {
        dialog_ok.setOnClickListener {
            dataName = dat_et_name.text.toString()
            when {
                dataName.isEmpty() -> {
                    Utils.toast(context, context.getString(R.string.add_type_name_tip))
                }
                dataIconUrl.isEmpty() -> {
                    Utils.toast(context, context.getString(R.string.add_type_icon_tip))
                }
                else -> {
                    onOK(DataType(dataName, dataType, dataIconUrl))
                    cancel()
                }
            }
        }
        return this
    }

    private fun initListeners() {
        dat_expense.setOnClickListener {
            switchType(dataType, NoteType.EXPENSE)
            dataType = NoteType.EXPENSE
        }
        dat_income.setOnClickListener {
            switchType(dataType, NoteType.INCOME)
            dataType = NoteType.INCOME

        }
        dat_iv_pic.setOnClickListener {
            val dialog = SelectIconDialog(context, IconSet.presetIcons)
            dialog.setOKListener {
                dataIconUrl = it.getUrl()
                Glide.with(context).load(dataIconUrl).into(dat_iv_pic)

            }.show()
        }

        dialog_close.setOnClickListener {
            cancel()
        }

    }

    private fun switchType(now: NoteType, to: NoteType) {
        if (now == to) {
            return
        } else {
            switcher?.let {
                val x = (it.width + it.marginStart + it.marginEnd).toFloat()
                if (now == NoteType.EXPENSE) {
                    //left to right
                    it.animate().translationX(x).setDuration(200).start()

                    tvExpense?.setTextColor(Color.parseColor("#A9A8AC"))
                    ivExpense?.setImageResource(R.mipmap.icon_arrow_up_gray)
                    tvIncome?.setTextColor(Color.parseColor("#FF5D5D"))
                    ivIncome?.setImageResource(R.mipmap.icon_arrow_red)
                } else {
                    // right to left
                    it.animate().translationX(0f).setDuration(200).start()

                    tvExpense?.setTextColor(Color.parseColor("#5EDA72"))
                    ivExpense?.setImageResource(R.mipmap.icon_arrow_green)
                    tvIncome?.setTextColor(Color.parseColor("#A9A8AC"))
                    ivIncome?.setImageResource(R.mipmap.icon_arrow_down_gray)
                }
            }

        }
    }


}