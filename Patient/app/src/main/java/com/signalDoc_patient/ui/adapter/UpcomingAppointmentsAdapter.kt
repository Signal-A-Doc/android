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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.AdapterUpcomingAppointmentsBinding
import com.signalDoc_patient.model.AppoitnmemtsListData
import com.signalDoc_patient.ui.activity.BaseActivity
import com.signalDoc_patient.ui.extensions.handleException
import com.signalDoc_patient.ui.fragment.UpcomingAppointmentFragment
import com.signalDoc_patient.utils.Const
import java.io.FileNotFoundException

class UpcomingAppointmentsAdapter(var baseActivity: BaseActivity, var datas: ArrayList<AppoitnmemtsListData>, var upcomingAppointmentFragment: UpcomingAppointmentFragment) : BaseAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AdapterUpcomingAppointmentsBinding>(LayoutInflater.from(parent.context), R.layout.adapter_upcoming_appointments, parent, false)
        return BaseViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        val binding = viewHolder.binding as AdapterUpcomingAppointmentsBinding
        val data = datas[viewHolder.adapterPosition]
        binding.dayTV.text = baseActivity.changeDateFormat(data.availabilityTime!!.date, "yyyy-MM-dd", "dd")
        binding.timeTV.text = baseActivity.changeDateFormat(data.availabilityTime!!.start_time, "HH:mm:ss", "hh:mm a")
        binding.monthTV.text = baseActivity.changeDateFormat(data.availabilityTime!!.date, "yyyy-MM-dd", "MMM")
        try {
            if (!data.availabilityMode!!.image_file.isEmpty()) {
                Glide.with(baseActivity).load(data.availabilityMode!!.image_file).apply(RequestOptions().placeholder(R.mipmap.ic_user_s).error(R.mipmap.ic_user_s)).into(binding.callIconIV)
            } else {
                binding.callIconIV.setImageResource(R.mipmap.ic_user_s)
            }

        } catch (e: FileNotFoundException) {
            handleException(e)
        }

        binding.onsulationTypeTV.text = data.availabilityMode!!.title
        binding.doctorName.isSelected = true
        binding.doctorName.setText(data.doctor!!.fullName)
        when (data.state_id) {
            Const.STATE_PENDING -> binding.stateTv.text = baseActivity.getString(R.string.pending)
        }

        binding.root.setOnClickListener {
            onItemClick()
        }


    }


}