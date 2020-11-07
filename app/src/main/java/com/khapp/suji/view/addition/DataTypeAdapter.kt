package com.khapp.suji.view.addition

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.khapp.suji.R
import com.khapp.suji.database.entity.DataType
import com.khapp.suji.view.comm.RVHolder
import kotlinx.android.synthetic.main.item_type.view.*

class DataTypeAdapter(private val context: Context) : RecyclerView.Adapter<RVHolder>() {
    var data: List<DataType> = ArrayList()
    var onDataTypeSelectListener: OnDataTypeSelectListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVHolder {
        return RVHolder(LayoutInflater.from(context).inflate(R.layout.item_type, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RVHolder, position: Int) {
        holder.itemView.apply {
            Glide.with(context).load(data[position].icon).into(it_iv_icon)
            it_tv_title.text = data[position].name
            setOnClickListener {
                onDataTypeSelectListener?.onSelect(data[position])
            }
        }


    }

    interface OnDataTypeSelectListener {
        fun onSelect(data: DataType)
    }
}