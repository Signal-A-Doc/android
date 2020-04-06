/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_doctor.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.DoctorLanguageSpokenDesignBinding
import com.signalDoc_doctor.databinding.DoctorTimeSlotsDesignBinding
import com.signalDoc_doctor.utils.Const

class DoctorTimeSlotsAdapter(var arrayList: ArrayList<String>) : BaseAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<DoctorTimeSlotsDesignBinding>(LayoutInflater.from(parent.context), R.layout.doctor_time_slots_design, parent, false)
        return BaseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        val binding = viewHolder.binding as DoctorTimeSlotsDesignBinding
        binding.languageTitleTV.setText(arrayList[position])
        binding.crossIV.setOnClickListener {
            onItemClick(Const.REMOVE_TIME,position)
        }
    }


}