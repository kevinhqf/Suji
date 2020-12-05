package com.khapp.suji.view.addition

import android.content.Context
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.khapp.suji.Constance
import com.khapp.suji.R
import com.khapp.suji.data.NoteType
import com.khapp.suji.database.entity.DataType
import com.khapp.suji.utils.ScreenUtils
import com.khapp.suji.utils.Utils
import com.khapp.suji.viewmodel.AdditionViewModel
import kotlinx.android.synthetic.main.dialog_addition.*
import kotlinx.android.synthetic.main.widget_numpad.*

class AdditionDialog(context: Context) : BottomSheetDialog(context, R.style.Dialog) {
    private val typeAdapter: DataTypeAdapter
    var moneyValue: String = ""
    var moneySign: String = ""

    init {
        setContentView(R.layout.dialog_addition)
        typeAdapter = DataTypeAdapter(context)
        da_rv_type.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        da_rv_type.adapter = typeAdapter
        behavior.setPeekHeight(ScreenUtils.getHeight(context), true)
        dismissWithAnimation = true


    }

    fun updateDataTypeList(data: List<DataType>) {
        typeAdapter.data = data
        typeAdapter.notifyDataSetChanged()
    }

    fun updateMoneyValue(value: String) {
        moneyValue = Utils.formatNumpadMoney(value)
        da_tv_value.text = moneySign + "￥" + moneyValue

    }

    fun updateMoneySign(type: Int) {
        moneySign = if (type == NoteType.EXPENSE.value) "-" else ""
        da_tv_value.text = moneySign + "￥" + moneyValue
    }


    fun updateIcon(res: String) {
        Glide.with(context).load(res).into(da_iv_icon)
    }
    fun updateIcon(res: Int) {
        Glide.with(context).load(res).into(da_iv_icon)
    }

    //todo 添加类型无显示
    fun initListeners(vm: AdditionViewModel) {
        typeAdapter.onDataTypeSelectListener = object : DataTypeAdapter.OnDataTypeSelectListener {
            override fun onSelect(data: DataType) {
                vm.setNewDataType(data)
            }
        }
        da_iv_addType.setOnClickListener {
            val addTypeDialog = AddTypeDialog(context)
            addTypeDialog.setOKListener {

                val dataType = DataType(
                    name = it.name,
                    icon = it.icon,
                    type = it.type.value,
                    uid = Constance.userId
                )
                vm.addDataType(dataType, typeAdapter.hasSameContent(dataType))

            }.show()
        }

        numpad_1.setOnClickListener {
            vm.numpadAction("1")
        }

        numpad_2.setOnClickListener {
            vm.numpadAction("2")
        }

        numpad_3.setOnClickListener {
            vm.numpadAction("3")
        }

        numpad_4.setOnClickListener {
            vm.numpadAction("4")
        }

        numpad_5.setOnClickListener {
            vm.numpadAction("5")
        }

        numpad_6?.setOnClickListener {
            vm.numpadAction("6")
        }

        numpad_7.setOnClickListener {
            vm.numpadAction("7")
        }

        numpad_8.setOnClickListener {
            vm.numpadAction("8")
        }

        numpad_9.setOnClickListener {
            vm.numpadAction("9")
        }
        numpad_0.setOnClickListener {
            vm.numpadAction("0")
        }
        numpad_dot.setOnClickListener {
            vm.numpadAction(".")
        }
        numpad_del.setOnClickListener {
            vm.numpadAction("del")
        }
        numpad_ok.setOnClickListener {
            vm.addTransaction(
                { Utils.toast(context, "请选择记账类型") },
                { Utils.toast(context, "请输入记账金额") },
                { cancel() })

        }

    }


}