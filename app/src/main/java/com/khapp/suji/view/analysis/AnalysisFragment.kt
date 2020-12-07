package com.khapp.suji.view.analysis

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.khapp.suji.MainActivity
import com.khapp.suji.R
import com.khapp.suji.data.AnalysisTimeUnit
import com.khapp.suji.data.NoteType
import com.khapp.suji.utils.InjectorUtils
import com.khapp.suji.utils.Utils
import com.khapp.suji.view.comm.OnMainPageScrollListener
import com.khapp.suji.viewmodel.TransactionViewModel
import kotlinx.android.synthetic.main.fragment_analysis.*
import kotlinx.android.synthetic.main.fragment_analysis.view.*

class AnalysisFragment : Fragment() {

    lateinit var classifyAdapter: AnalysisClassifyAdapter
    var scrollListener: OnMainPageScrollListener? = null
    private val transactionViewModel: TransactionViewModel by activityViewModels {
        InjectorUtils.provideTransactionViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_analysis, container, false)
        initData(root)
        initViews(root)
        initListener(root)
        initObserver(root)
        return root
    }

    private fun initViews(root: View) {
        root.apply {
            classifyAdapter = AnalysisClassifyAdapter(requireContext())
            fa_rv_classify.layoutManager = LinearLayoutManager(requireContext())
            fa_rv_classify.adapter = classifyAdapter
            fa_rv_classify.isNestedScrollingEnabled = false
        }
    }

    private fun initData(root: View) {
        changeSelectTabState(root.fa_tv_time1, root)
        transactionViewModel.switchAnalysisTimeUnit(requireContext(), AnalysisTimeUnit.THIS_WEEK)

    }

    private fun initListener(root: View) {
        root.apply {
            //切换收入支出分类卡片
            fa_tv_expense_classify.setOnClickListener {
                transactionViewModel.switchStatisticsType()
            }
            fa_tv_income_classify.setOnClickListener {
                transactionViewModel.switchStatisticsType()
            }

            fa_tv_time1.setOnClickListener {
                changeSelectTabState(it as TextView, root)
                transactionViewModel.switchAnalysisTimeUnit(
                    requireContext(),
                    AnalysisTimeUnit.THIS_WEEK
                )
                fa_tv_title.text = context.getString(R.string.time_unit1_statistics)
                fa_tv_income_total_label.text = context.getString(R.string.time_unit1_income)
                fa_tv_expense_total_label.text = context.getString(R.string.time_unit1_expense)
            }
            fa_tv_time2.setOnClickListener {
                changeSelectTabState(it as TextView, root)
                transactionViewModel.switchAnalysisTimeUnit(
                    requireContext(),
                    AnalysisTimeUnit.THIS_MONTH
                )
                fa_tv_title.text = context.getString(R.string.time_unit2_statistics)
                fa_tv_income_total_label.text = context.getString(R.string.time_unit2_income)
                fa_tv_expense_total_label.text = context.getString(R.string.time_unit2_expense)
            }
            fa_tv_time3.setOnClickListener {
                changeSelectTabState(it as TextView, root)
                transactionViewModel.switchAnalysisTimeUnit(
                    requireContext(),
                    AnalysisTimeUnit.LATELY_HALF_YEAR
                )
                fa_tv_title.text = context.getString(R.string.time_unit3_statistics)
                fa_tv_income_total_label.text = context.getString(R.string.time_unit3_income)
                fa_tv_expense_total_label.text = context.getString(R.string.time_unit3_expense)
            }
            //滑动监听
            fa_scrollView.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
                if (scrollY - oldScrollY > MainActivity.SCROLL_DISTANCE) {
                    // 向上滑动
                    scrollListener?.onScrollUp()
                }

                if (scrollY - oldScrollY < -MainActivity.SCROLL_DISTANCE) {
                    // 向下滑动
                    scrollListener?.onScrollDown()
                }
            }
        }
    }

    private fun initObserver(root: View) {
        transactionViewModel.analysisOverview.observe(viewLifecycleOwner, Observer {
            root.apply {
                fa_tv_income_total_value.text = it.income.toString()
                fa_tv_expense_total_value.text = it.expense.toString()
                fa_tv_balance.text = Utils.getFormatMoneyStr(it.total)
            }
        })
        transactionViewModel.analysisTransactions.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                fa_card_statistics_classify.visibility = View.VISIBLE
                transactionViewModel.analysisTimeUnitMoney(requireContext(), it)
                if (it.none { item -> item.dataTypeValue == transactionViewModel.getStatisticsNoteType().value }) {
                    transactionViewModel.switchStatisticsType()
                } else {
                    classifyAdapter.statistics(it)
                }
            } else {
                fa_card_statistics_classify.visibility = View.INVISIBLE
            }
        })
        transactionViewModel.analysisGraphData.observe(viewLifecycleOwner, Observer {
            if (it != null)
                root.fa_hv_data.setData(it)
        })

        transactionViewModel.statisticsType.observe(viewLifecycleOwner, Observer {
            //切换分类类型
            val tv = if (it == NoteType.INCOME) fa_tv_income_classify else fa_tv_expense_classify
            changeSelectClassifyTextState(tv, root)
            classifyAdapter.switchType(it)
        })

    }

    private fun clearTabState(root: View) {
        root.fa_tv_time1.background = null
        root.fa_tv_time1.setTextColor(Color.WHITE)
        root.fa_tv_time2.background = null
        root.fa_tv_time2.setTextColor(Color.WHITE)
        root.fa_tv_time3.background = null
        root.fa_tv_time3.setTextColor(Color.WHITE)
    }

    private fun clearClassifyTextState(root: View) {
        root.apply {
            fa_tv_expense_classify.setTextColor(Color.parseColor("#FF757575"))
            fa_tv_expense_classify.background = null
            fa_tv_income_classify.setTextColor(Color.parseColor("#FF757575"))
            fa_tv_income_classify.background = null
        }
    }

    private fun changeSelectClassifyTextState(view: TextView, root: View) {
        clearClassifyTextState(root)
        view.setTextColor(Color.parseColor("#03A9F4"))
        view.setBackgroundResource(R.drawable.bg_text_selected)
    }

    private fun changeSelectTabState(view: TextView, root: View) {
        clearTabState(root)
        view.setTextColor(Color.parseColor("#D9000000"))
        view.setBackgroundResource(R.drawable.bg_page_tab_selected)
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            AnalysisFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}