package com.khapp.suji.view.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.khapp.suji.R
import com.khapp.suji.preset.Currency
import com.khapp.suji.view.comm.RVHolder
import kotlinx.android.synthetic.main.item_setting_currency.view.*

class CurrencyAdapter(val context: Context) : RecyclerView.Adapter<RVHolder>() {

    var onSelectCurrencyListener :OnSelectCurrencyListener?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVHolder {
        return RVHolder(LayoutInflater.from(context).inflate(R.layout.item_setting_currency,parent,false))
    }

    override fun onBindViewHolder(holder: RVHolder, position: Int) {
        val currency = Currency.values()[position]
        holder.itemView.apply {
           Glide.with(context).load(currency.icon).into( isc_iv_icon)
            isc_tv_name.text = currency.description
            setOnClickListener {  }
            setOnFocusChangeListener { view, b ->
                if (b){
                    onSelectCurrencyListener?.onSelect(currency)
                    isc_bg.setBackgroundResource(R.drawable.bg_dialog_setting_item_selected)
                }else{
                    isc_bg.background = null
                }
            }
        }
    }

    override fun getItemCount(): Int {
      return Currency.values().size
    }
    interface OnSelectCurrencyListener {
        fun onSelect(currency: Currency)
    }
}