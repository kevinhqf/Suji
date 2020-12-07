package com.khapp.suji.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.khapp.suji.Constance
import com.khapp.suji.R
import com.khapp.suji.data.NoteType
import com.khapp.suji.data.TransactionDetail
import com.khapp.suji.database.entity.TransactionInfo
import com.khapp.suji.utils.Utils
import com.khapp.suji.view.comm.RVHolder
import kotlinx.android.synthetic.main.item_transaction.view.*

class TransactionAdapter(val context: Context) :
    PagedListAdapter<TransactionInfo, RVHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TransactionInfo>() {
            override fun areItemsTheSame(
                oldItem: TransactionInfo,
                newItem: TransactionInfo
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TransactionInfo,
                newItem: TransactionInfo
            ): Boolean {
                return oldItem == newItem
            }

        }
    }


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
                it.it_time.text = Utils.formatTransactionTime(item.createTime)
                if (item.dataTypeValue == NoteType.INCOME.value) {
                    it.it_value.text =
                        "${Constance.config.currency.sign}${Utils.formatMoneyValue(item.value)}"
                } else {
                    it.it_value.text =
                        "-${Constance.config.currency.sign}${Utils.formatMoneyValue(item.value)}"
                }
            }


        }
    }
}

