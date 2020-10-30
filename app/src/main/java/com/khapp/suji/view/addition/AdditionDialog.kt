package com.khapp.suji.view.addition

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.khapp.suji.R
import com.khapp.suji.utils.ScreenUtils
import com.khapp.suji.utils.Utils
import com.khapp.suji.viewmodel.AdditionViewModel

class AdditionDialog(context: Context) : BottomSheetDialog(context,R.style.Dialog) {
    private val typeAdapter:TypeAdapter
    init {
        setContentView(R.layout.dialog_addition)
        typeAdapter = TypeAdapter(context)
        findViewById<RecyclerView>(R.id.da_rv_type)?.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter = typeAdapter
        }

        behavior.setPeekHeight(ScreenUtils.getHeight(context),true)
    }


    fun updateMoney(value:String){
        findViewById<TextView>(R.id.da_tv_value)?.apply {
            text = "￥"+Utils.formatNumpadMoney(value)
        }
    }



     fun initListeners(vm:AdditionViewModel) {
        findViewById<TextView>(R.id.numpad_1)?.apply {
            setOnClickListener {
                vm.numpadAction("1")
            }
        }
        findViewById<TextView>(R.id.numpad_2)?.apply {
            setOnClickListener {
                vm.numpadAction("2")
            }
        }
        findViewById<TextView>(R.id.numpad_3)?.apply {
            setOnClickListener {
                vm.numpadAction("3")
            }
        }
        findViewById<TextView>(R.id.numpad_4)?.apply {
            setOnClickListener {
                vm.numpadAction("4")
            }
        }
        findViewById<TextView>(R.id.numpad_5)?.apply {
            setOnClickListener {
                vm.numpadAction("5")
            }
        }
        findViewById<TextView>(R.id.numpad_6)?.apply {
            setOnClickListener {
                vm.numpadAction("6")
            }
        }
        findViewById<TextView>(R.id.numpad_7)?.apply {
            setOnClickListener {
                vm.numpadAction("7")
            }
        }
        findViewById<TextView>(R.id.numpad_8)?.apply {
            setOnClickListener {
                vm.numpadAction("8")
            }
        }
        findViewById<TextView>(R.id.numpad_9)?.apply {
            setOnClickListener {
                vm.numpadAction("9")
            }
        }
        findViewById<TextView>(R.id.numpad_0)?.apply {
            setOnClickListener {
                vm.numpadAction("0")
            }
        }
        findViewById<TextView>(R.id.numpad_dot)?.apply {
            setOnClickListener {
                vm.numpadAction(".")
            }
        }
        findViewById<ImageView>(R.id.numpad_del)?.apply {
            setOnClickListener {
                vm.numpadAction("del")
            }
        }

    }


}