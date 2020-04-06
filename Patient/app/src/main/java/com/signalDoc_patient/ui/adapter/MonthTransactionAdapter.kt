/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_patient.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.AdapterMonthTransactionBinding

class MonthTransactionAdapter() : BaseAdapter() {
    private var binding: AdapterMonthTransactionBinding? = null
    private var adapterMonthData: MonthDataAdapter? = null

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(viewHolder, position)
        val binding = viewHolder.binding as AdapterMonthTransactionBinding


    }

    private fun setMonthDataAdapter() {
        adapterMonthData = MonthDataAdapter()
        binding!!.monthDataRV.adapter = adapterMonthData

    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AdapterMonthTransactionBinding>(LayoutInflater.from(parent.context), R.layout.adapter_month_transaction, parent, false)

        return BaseViewHolder(binding)

    }

}