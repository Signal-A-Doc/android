/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.AdapterPendingAppointmentsBinding
import com.signalDoc_doctor.model.UpcomingAppointmentData
import com.signalDoc_doctor.ui.activity.BaseActivity
import com.signalDoc_doctor.ui.extensions.handleException
import com.signalDoc_doctor.ui.fragment.doctor.PastAppointmentFragment
import com.signalDoc_doctor.ui.fragment.doctor.PendingAppointmentFragment
import com.signalDoc_doctor.ui.fragment.doctor.UpcomingFragment
import com.signalDoc_doctor.utils.Const
import com.signalDoc_patient.model.AppoitnmemtsListData
import java.io.FileNotFoundException

class PendingAdapter(var baseActivity: BaseActivity, var datas: ArrayList<AppoitnmemtsListData>, var fragment: Fragment) : BaseAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AdapterPendingAppointmentsBinding>(LayoutInflater.from(parent.context), R.layout.adapter_pending_appointments, parent, false)
        return BaseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val binding = holder.binding as AdapterPendingAppointmentsBinding
        val data = datas[holder.adapterPosition]
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
        if (position + 1 == datas.size) {
            if (fragment is PendingAppointmentFragment) {
                (fragment as PendingAppointmentFragment).getPendingAppoitments()
            } else if (fragment is UpcomingFragment) {
                (fragment as UpcomingFragment).getUpcomingAppoitments()
            } else if (fragment is PastAppointmentFragment) {
                (fragment as PastAppointmentFragment).getPastAppoitments()
            }
        }


    }

}