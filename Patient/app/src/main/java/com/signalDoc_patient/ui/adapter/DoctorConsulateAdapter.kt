package com.signalDoc_patient.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.ConsultationAdapterDesignBinding
import com.signalDoc_patient.model.ConsulatitionData
import com.signalDoc_patient.utils.Const
import mykotlintest.app.ui.fragment.AppointmentInfoFourFragment

class DoctorConsulateAdapter(var arrayList: ArrayList<ConsulatitionData>, var availableAppointmentFragment: AppointmentInfoFourFragment) : BaseAdapter() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<ConsultationAdapterDesignBinding>(LayoutInflater.from(parent.context), R.layout.consultation_adapter_design, parent, false)
        return BaseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        val context = viewHolder.itemView.context
        val binding = viewHolder.binding as ConsultationAdapterDesignBinding
        binding.callTV.setText(arrayList[position].title)
        if (arrayList[position].imageFile != null && arrayList[position].imageFile!!.isNotEmpty()) {
            Glide.with(context).load(arrayList[position].imageFile).into(binding.callIconIV)
        } else {
            Glide.with(context).clear(binding.callIconIV)
        }
        binding.root.setOnClickListener {
            onItemClick(Const.OPEN_PAYMENT,position)
        }

        if (position + 1 == arrayList.size) {
            availableAppointmentFragment.getAllAvailableModes()
        }
    }


}