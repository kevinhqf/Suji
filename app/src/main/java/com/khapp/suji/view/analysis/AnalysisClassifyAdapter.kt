package com.khapp.suji.view.analysis

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.khapp.suji.R
import com.khapp.suji.data.NoteType
import com.khapp.suji.database.entity.TransactionInfo
import com.khapp.suji.utils.Utils
import com.khapp.suji.view.comm.RVHolder
import kotlinx.android.synthetic.main.item_analysis_classify.view.*

class AnalysisClassifyAdapter(private val context: Context, var type: NoteType = NoteType.EXPENSE) :
    RecyclerView.Adapter<RVHolder>() {

    var transactionList: List<TransactionInfo>?=null
    private var showData:List<StatisticsTransaction>?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVHolder {
        return RVHolder(
            LayoutInflater.from(context).inflate(R.layout.item_analysis_classify, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RVHolder, position: Int) {
        val item = showData!![position]
        holder.itemView.apply {
            Glide.with(context).load(item.icon).into(iac_iv_icon)
            iac_tv_name.text = item.name
            iac_tv_value.text = Utils.getFormatMoneyStr(item.value)
        }
    }

    override fun getItemCount(): Int {
        return showData?.size ?: 0
    }

    fun switchType(type: NoteType) {
        this.type = type
        transactionList?.let { statistics(it) }
    }
    fun statistics(list: List<TransactionInfo>){
        this.transactionList = list
        val map = HashMap<Int,StatisticsTransaction>()
        list.filter { it.dataTypeValue==type.value }.forEach {
            var statisticsTransaction = map[it.dataTypeId]
            if(statisticsTransaction==null){
                statisticsTransaction = StatisticsTransaction(it.dataTypeName,it.dataTypeIcon,it.value)
            }else{
                statisticsTransaction.value+=it.value
            }
            map[it.dataTypeId] = statisticsTransaction
        }
        showData = map.map {
            it.value
        }
        notifyDataSetChanged()
    }
    //todo icon修改为url
    data class StatisticsTransaction(val name:String,val icon:String,var value:Float)
}