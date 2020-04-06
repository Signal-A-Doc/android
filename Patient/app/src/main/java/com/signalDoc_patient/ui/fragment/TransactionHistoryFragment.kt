package com.signalDoc_patient.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentTransactionHistoryBinding
import com.signalDoc_patient.ui.adapter.MonthTransactionAdapter
import com.signalDoc_patient.ui.fragment.BaseFragment

class TransactionHistoryFragment : BaseFragment() {

    private var binding: FragmentTransactionHistoryBinding?=null
    private var adapterMonth: MonthTransactionAdapter?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        if(binding==null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction_history, container, false)
        }
        return binding!!.root;

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        setCategoriesAdapter()
    }

    private fun setCategoriesAdapter() {
        adapterMonth= MonthTransactionAdapter()
        binding!!.monthRV.adapter=adapterMonth

    }

}