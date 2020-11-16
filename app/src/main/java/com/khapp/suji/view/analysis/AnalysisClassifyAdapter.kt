package com.khapp.suji.view.analysis

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khapp.suji.R
import com.khapp.suji.view.comm.RVHolder

class AnalysisClassifyAdapter(val context: Context) : RecyclerView.Adapter<RVHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVHolder {
        return RVHolder(
            LayoutInflater.from(context).inflate(R.layout.item_analysis_classify, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RVHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 50
    }
}