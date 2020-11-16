package com.khapp.suji.view.analysis

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.khapp.suji.R
import com.khapp.suji.view.comm.OnMainPageScrollListener
import kotlinx.android.synthetic.main.fragment_analysis.view.*

class AnalysisFragment : Fragment() {

    lateinit var classifyAdapter: AnalysisClassifyAdapter
    var scrollListener: OnMainPageScrollListener? = null
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


    }

    private fun initListener(root: View) {
        root.apply {
            fa_tv_expense_classify.setOnClickListener { changeSelectClassifyTextState(it as TextView,root) }
            fa_tv_income_classify.setOnClickListener { changeSelectClassifyTextState(it as TextView,root) }
            fa_tv_time1.setOnClickListener { changeSelectTabState(it as TextView, root) }
            fa_tv_time2.setOnClickListener { changeSelectTabState(it as TextView, root) }
            fa_tv_time3.setOnClickListener { changeSelectTabState(it as TextView, root) }
            fa_scrollView.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
                if (scrollY - oldScrollY > 50) {
                    // 向上滑动
                    scrollListener?.onScrollUp()
                }

                if (scrollY - oldScrollY < -50) {
                    // 向下滑动
                    scrollListener?.onScrollDown()
                }
            }
        }
    }

    private fun initObserver(root: View) {

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