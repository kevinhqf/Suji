package com.khapp.suji.view.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.khapp.suji.R
import com.khapp.suji.data.NoteType
import com.khapp.suji.database.entity.TransactionDetail
import com.khapp.suji.view.comm.RVHolder
import kotlinx.android.synthetic.main.item_transaction.view.*
import kotlinx.android.synthetic.main.item_type.view.*

class TransactionAdapter(val context: Context) :
    PagedListAdapter<TransactionDetail, RVHolder>(TransactionDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVHolder {
        return RVHolder(
            LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false)
        )
    }


    override fun onBindViewHolder(holder: RVHolder, position: Int) {
        holder.itemView.let {

            getItem(position)?.let { item ->
                Glide.with(context).load(item.dataTypeIcon).into(it.it_icon)
                it.it_title.text = item.dataTypeName
                it.it_time.text = item.createTime
                if (item.dataTypeValue == NoteType.INCOME.value) {
                    it.it_value.text = "￥${item.value}"
                } else {
                    it.it_value.text = "-￥${item.value}"
                }
            }


        }
    }
}

private class TransactionDiffCallback : DiffUtil.ItemCallback<TransactionDetail>() {
    override fun areItemsTheSame(oldItem: TransactionDetail, newItem: TransactionDetail): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: TransactionDetail,
        newItem: TransactionDetail
    ): Boolean {
        return oldItem == newItem
    }

}