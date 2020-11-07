package com.khapp.suji.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.khapp.suji.R
import com.khapp.suji.utils.InjectorUtils
import com.khapp.suji.viewmodel.TransactionViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    lateinit var adapter: TransactionAdapter
    private val transactionViewModel:TransactionViewModel by activityViewModels {
        InjectorUtils.provideTransactionViewModelFactory()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

    }

    private fun initObserver() {
        transactionViewModel.transactionList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
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
        initObserver()
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