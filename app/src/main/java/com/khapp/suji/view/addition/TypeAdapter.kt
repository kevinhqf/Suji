package com.khapp.suji.view.addition

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khapp.suji.R
import com.khapp.suji.view.comm.RVHolder

class TypeAdapter(val context:Context) : RecyclerView.Adapter<RVHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVHolder {
        return RVHolder(LayoutInflater.from(context).inflate(R.layout.item_type,parent,false))
    }

    override fun getItemCount(): Int {
       return 10
    }

    override fun onBindViewHolder(holder: RVHolder, position: Int) {

    }
}