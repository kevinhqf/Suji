package com.khapp.suji.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.PagedList
import com.khapp.suji.Constance
import com.khapp.suji.data.AnalysisTimeUnit
import com.khapp.suji.data.NoteType
import com.khapp.suji.data.MoneyOverview
import com.khapp.suji.database.entity.TransactionInfo
import com.khapp.suji.repository.TransactionRepository
import com.khapp.suji.ui.HistogramView
import com.khapp.suji.utils.DateUtils
import com.khapp.suji.view.comm.BaseViewModel

class TransactionViewModel(private val repository: TransactionRepository) : BaseViewModel() {

    //首页
    private val userId = MutableLiveData<Long>()
    val transactionList = switchMap(map(userId) {
        repository.loadTransactionsByUid(it)
    }) { it }
    val todayTransactions = switchMap(map(userId) {
        repository.getTodayTransaction(it)
    }) { it }
    val todayOverview = MutableLiveData<MoneyOverview>()


    //统计页
    val analysisTransactions = MutableLiveData<List<TransactionInfo>>()
    val analysisOverview = MutableLiveData<MoneyOverview>()

    // 统计图数据
    val analysisGraphData = MutableLiveData<ArrayList<HistogramView.Histogram.HistogramData>>()
    var analysisTimeUnit: AnalysisTimeUnit = AnalysisTimeUnit.THIS_WEEK

    //统计页账单列表
    val statisticsType = MutableLiveData<NoteType>(NoteType.INCOME)

    /**
     * 切换统计页的时间单位
     */
    fun switchAnalysisTimeUnit(unit: AnalysisTimeUnit) {
        analysisTimeUnit = unit
        launchIO {
            val at = repository.getAnalysisTransaction(Constance.userId, unit)
            analysisTransactions.postValue(at)
            analysisTimeUnitMoney(at)
        }
    }

    /**
     * 切换统计页的收入支出分类
     */
    fun switchStatisticsType() {
        statisticsType.value?.let {
            if (it == NoteType.INCOME)
                statisticsType.postValue(NoteType.EXPENSE)
            else if (it == NoteType.EXPENSE)
                statisticsType.postValue(NoteType.INCOME)
        }
    }

    /**
     * 获取当前统计分类的类型
     */
    fun getStatisticsNoteType(): NoteType {
        return statisticsType.value!!
    }

    /**
     * 分析账单并生成图表数据
     */
    fun analysisTimeUnitMoney(list: List<TransactionInfo>) {
        analysisOverview.postValue(calcMoneyOverviewBy(list))
        val graphData = ArrayList<HistogramView.Histogram.HistogramData>()
        val graphTimeUnit = DateUtils.getGraphTimeUnitArr(analysisTimeUnit)
        graphTimeUnit.forEach { gtu ->
            val filter =
                list.filter { it.createTime >= gtu.time.start && it.createTime <= gtu.time.end }
            val moneyOverview = calcMoneyOverviewBy(filter)
            graphData.add(
                HistogramView.Histogram.HistogramData(
                    gtu.title,
                    arrayListOf(moneyOverview.income, moneyOverview.expense)
                )
            )
        }
        analysisGraphData.postValue(graphData)
    }


    fun calcTodayMoney(list: List<TransactionInfo>) {
        todayOverview.postValue(calcMoneyOverviewBy(list))
    }

    /**
     * 计算统计账单列表
     */
    private fun calcMoneyOverviewBy(list: List<TransactionInfo>): MoneyOverview {
        val incomeValue =
            list.filter { it.dataTypeValue == NoteType.INCOME.value }?.fold(0f) { acc, info ->
                acc + info.value
            }
        val expenseValue =
            list.filter { it.dataTypeValue == NoteType.EXPENSE.value }?.fold(0f) { acc, info ->
                acc + info.value
            }
        return MoneyOverview(
            incomeValue,
            expenseValue,
            incomeValue - expenseValue
        )
    }

    /**
     * 切换用户id
     */
    fun switchUser(uid: Long) {
        userId.postValue(uid)
    }


}