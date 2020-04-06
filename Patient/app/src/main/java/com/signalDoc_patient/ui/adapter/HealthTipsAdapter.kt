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
import com.signalDoc_patient.databinding.AdapterHealthTipBinding

import com.signalDoc_patient.ui.activity.BaseActivity
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.HealthTipsDetailFragment


class HealthTipsAdapter(var baseActivity: BaseActivity?):BaseAdapter() {
    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(viewHolder, position)
        val binding = viewHolder.binding as  AdapterHealthTipBinding

        binding.root.setOnClickListener {
            baseActivity!!.replaceFragment(HealthTipsDetailFragment(),R.id.frame_container)
        }



    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AdapterHealthTipBinding>(LayoutInflater.from(parent.context), R.layout.adapter_health_tip, parent, false)
        return BaseViewHolder(binding)




    }
}