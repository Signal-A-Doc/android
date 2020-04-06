package com.signalDoc_patient.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.AdapterAppointmentAvalBinding
import com.signalDoc_patient.model.TimeSlotsData
import com.signalDoc_patient.ui.activity.BaseActivity
import com.signalDoc_patient.ui.fragment.patient.AvailableAppointmentFragment

class AvalAppointmentAdapter(var arrayList: ArrayList<TimeSlotsData>, var availableAppointmentFragment: AvailableAppointmentFragment? = null,var baseActivity: BaseActivity?=null) : BaseAdapter() {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val binding = holder.binding as AdapterAppointmentAvalBinding

        binding.timeTV.setText(baseActivity!!.changeDateFormat(arrayList[position].startTime,"HH:mm:ss","hh:mm a") + " - " + baseActivity!!.changeDateFormat(arrayList[position].endTime,"HH:mm:ss","hh:mm a"))

        if (arrayList[position].isSelected) {
            binding.checkTV.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_selected_green, 0, 0, 0);
        } else {
            binding.checkTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        holder.itemView.setOnClickListener {
            availableAppointmentFragment?.selectedSlots(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AdapterAppointmentAvalBinding>(LayoutInflater.from(parent.context), R.layout.adapter_appointment_aval, parent, false)
        return BaseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

}