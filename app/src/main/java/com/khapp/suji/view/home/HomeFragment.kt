package com.khapp.suji.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.util.Util
import com.khapp.suji.R
import com.khapp.suji.data.NoteType
import com.khapp.suji.utils.InjectorUtils
import com.khapp.suji.utils.Utils
import com.khapp.suji.viewmodel.TransactionViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.overview_home.view.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    lateinit var adapter: TransactionAdapter
    private val transactionViewModel: TransactionViewModel by activityViewModels {
        InjectorUtils.provideTransactionViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

    }

    private fun initObserver(root: View) {

        transactionViewModel.transactionList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            root.postDelayed({
                root.fh_rv.scrollToPosition(0)
            }, 500)
        })

        transactionViewModel.todayTransaction.observe(viewLifecycleOwner, Observer {
            root.oh_tv_tip.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
            transactionViewModel.calcTodayMoney(it)
        })
        transactionViewModel.todayOverview.observe(viewLifecycleOwner, Observer {
            root.oh_tv_income.text = Utils.getFormatMoneyStr(it.income)
            root.oh_tv_expense.text = Utils.getFormatMoneyStr(it.expense)
            root.oh_tv_total.text = Utils.getFormatMoneyStr(it.total)
        })

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        adapter = TransactionAdapter(requireContext())
        root.fh_rv.adapter = adapter
        root.fh_rv.layoutManager = LinearLayoutManager(requireContext())
        initObserver(root)
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}