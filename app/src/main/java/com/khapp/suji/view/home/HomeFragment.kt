package com.khapp.suji.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khapp.suji.MainActivity
import com.khapp.suji.R
import com.khapp.suji.utils.InjectorUtils
import com.khapp.suji.utils.Utils
import com.khapp.suji.view.comm.OnMainPageScrollListener
import com.khapp.suji.viewmodel.TransactionViewModel
import com.khapp.suji.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.overview_home.view.*


class HomeFragment : Fragment() {

    lateinit var adapter: TransactionAdapter
    var scrollListener: OnMainPageScrollListener? = null
    private val transactionViewModel: TransactionViewModel by activityViewModels {
        InjectorUtils.provideTransactionViewModelFactory()
    }

    private val userViewModel: UserViewModel by activityViewModels {
        InjectorUtils.provideUserViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

    }

    private fun initObserver(root: View) {
        userViewModel.user.observe(viewLifecycleOwner, Observer {
            transactionViewModel.switchUser(it.id)
        })
        transactionViewModel.transactionList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            root.postDelayed({
                root.fh_rv.scrollToPosition(0)
            }, 500)
        })

        transactionViewModel.todayTransactions.observe(viewLifecycleOwner, Observer {
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
        initListener(root)
        initObserver(root)
        return root
    }

    private fun initListener(root: View) {
        root.fh_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                super.onScrolled(recyclerView, dx, dy)
                if (dy > MainActivity.SCROLL_DISTANCE) {
                    //上滑
                    scrollListener?.onScrollUp()
                } else if (dy < -MainActivity.SCROLL_DISTANCE) {
                    //下滑
                    scrollListener?.onScrollDown()
                }
            }
        })

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