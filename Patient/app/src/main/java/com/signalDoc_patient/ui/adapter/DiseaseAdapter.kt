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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.AdapterDiseaseBinding
import com.signalDoc_patient.model.CategoriesData
import com.signalDoc_patient.ui.activity.BaseActivity
import com.signalDoc_patient.ui.extensions.replaceFragWithArgs
import com.signalDoc_patient.ui.fragment.patient.AppointmentInfoFragment
import com.signalDoc_patient.ui.fragment.patient.HowLongDaysFragment
import com.signalDoc_patient.utils.Const

class DiseaseAdapter(var baseActivity: BaseActivity?, var datas: ArrayList<CategoriesData>, var seeAll: Boolean, var appointmentInfoFragment: AppointmentInfoFragment) : BaseAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AdapterDiseaseBinding>(LayoutInflater.from(parent.context), R.layout.adapter_disease, parent, false)
        return BaseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (!seeAll) {
            if (datas.size > Const.WEIGHT) {
                return Const.WEIGHT
            } else {
                return datas.size
            }

        } else {
            return datas.size
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val binding = holder.binding as AdapterDiseaseBinding
        binding.data = datas[holder.adapterPosition]
        binding.executePendingBindings()
        binding.titleTV.isSelected = true

        binding.topLL.setOnClickListener {
            appointmentInfoFragment.openNext(position)
        }

    }


}