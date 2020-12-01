package com.khapp.suji.view.addition

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.khapp.suji.R
import com.khapp.suji.data.Resources
import com.khapp.suji.view.comm.RVHolder
import kotlinx.android.synthetic.main.item_icon.view.*

class IconAdapter(val context: Context) :
    RecyclerView.Adapter<RVHolder>() {
    var data: List<Resources> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    var selectIconListener: OnSelectIconListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVHolder {
        return RVHolder(LayoutInflater.from(context).inflate(R.layout.item_icon, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RVHolder, position: Int) {
        holder.itemView.setOnClickListener {


        }
        holder.itemView.setOnFocusChangeListener { view, b ->
            if (b) {
                selectIconListener?.onSelect(data[position].getUrl())
                holder.itemView.ii_select_bg.visibility = View.VISIBLE
            } else {
                holder.itemView.ii_select_bg.visibility = View.INVISIBLE
            }
        }
        Glide.with(context).load(data[position].getUrl()).into(holder.itemView.ii_iv_icon)

    }

    interface OnSelectIconListener {
        fun onSelect(iconUrl: String)
    }
}