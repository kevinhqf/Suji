package com.khapp.suji.view.analysis

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.khapp.suji.R
import kotlinx.android.synthetic.main.fragment_analysis.view.*

class AnalysisFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_analysis,container,false)
        initListener(root)
        initObserver(root)
        return root
    }

    private fun initListener(root: View) {
        root.fa_tv_time1.setOnClickListener{
            selectTab(it as TextView,root)
        }
        root.fa_tv_time2.setOnClickListener{
            selectTab(it as TextView,root)
        }
        root.fa_tv_time3.setOnClickListener{
            selectTab(it as TextView,root)
        }
    }

    private fun initObserver(root: View) {

    }

    fun clearTabState(root: View){
        root.fa_tv_time1.isSelected=false
        root.fa_tv_time1.setTextColor(Color.WHITE)
        root.fa_tv_time2.isSelected=false
        root.fa_tv_time2.setTextColor(Color.WHITE)
        root.fa_tv_time3.isSelected=false
        root.fa_tv_time3.setTextColor(Color.WHITE)
    }

    fun selectTab(view:TextView,root:View){
        clearTabState(root)
        view.setTextColor(Color.parseColor("#D9000000"))
        view.isSelected = true
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