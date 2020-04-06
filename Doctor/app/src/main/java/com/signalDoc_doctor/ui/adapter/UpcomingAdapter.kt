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
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.AdapterUpcomingAppointmentHomeBinding
import com.signalDoc_doctor.model.UpcomingAppointmentData
import com.signalDoc_doctor.ui.fragment.doctor.HomeDoctorFragment
import com.signalDoc_doctor.utils.Const

class UpcomingAdapter(var arrayList: ArrayList<UpcomingAppointmentData>, var homeDoctorFragment: HomeDoctorFragment? = null) : BaseAdapter() {

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(viewHolder, position)
        val context = viewHolder.itemView.context
        val binding = viewHolder.binding as AdapterUpcomingAppointmentHomeBinding
        binding.nameTV.setText(arrayList[position].createdBy.fullName)
        binding.ageGenderTV.text = if (arrayList[position].createdBy.gender == Const.MALE) context.getString(R.string.male) else context.getString(R.string.female) + " | " + arrayList[position].createdBy.age
        binding.videoConsultTV.text = arrayList[position].availabilityMode.title
        if (arrayList[position].createdBy.symptoms.size > 0)
            binding.allergicTV.setText(arrayList[position].createdBy.symptoms[0].title)

        binding.waitTimeTV.visibility = View.GONE
        binding.timeTV.visibility = View.GONE
        binding.signupBT.setText(context.getString(R.string.accept))
        if (position + 1 == arrayList.size) {
            homeDoctorFragment?.apiHitpending()
        }
        binding.signupBT.setOnClickListener {
            homeDoctorFragment!!.showAcceptDialog(viewHolder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AdapterUpcomingAppointmentHomeBinding>(LayoutInflater.from(parent.context), R.layout.adapter_upcoming_appointment_home, parent, false)

        return BaseViewHolder(binding)
    }
}