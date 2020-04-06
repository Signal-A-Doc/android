/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_patient.ui.adapter.doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.AdapterMonthDataBinding
import com.signalDoc_patient.ui.adapter.BaseAdapter
import com.signalDoc_patient.ui.adapter.BaseViewHolder


class MonthDataAdapter : BaseAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AdapterMonthDataBinding>(LayoutInflater.from(parent.context), R.layout.adapter_month_data, parent, false)
        return BaseViewHolder(binding)


    }

    override fun getItemCount(): Int {
        return 3

    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        val binding = viewHolder.binding as AdapterMonthDataBinding

    }
}



