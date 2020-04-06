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
import com.signalDoc_patient.databinding.AdapterHomeNewsBinding
import com.signalDoc_patient.ui.activity.BaseActivity

class HomeNewsAdapter(var baseActivity: BaseActivity?) : BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AdapterHomeNewsBinding>(LayoutInflater.from(parent.context), R.layout.adapter_home_news, parent, false)
        return BaseViewHolder(binding)


    }

    override fun getItemCount(): Int {
        return 4

    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        val binding = viewHolder.binding as AdapterHomeNewsBinding
        binding.root.setOnClickListener {
            baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.under_delvelopment))

        }

    }
}