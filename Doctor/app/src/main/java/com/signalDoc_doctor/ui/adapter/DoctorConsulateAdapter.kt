package com.signalDoc_doctor.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.ConsultationAdapterDesignBinding
import com.signalDoc_doctor.model.ConsulatitionData
import com.signalDoc_doctor.ui.fragment.doctor.HomeDoctorFragment
import com.signalDoc_doctor.utils.Const

class DoctorConsulateAdapter(var arrayList: ArrayList<ConsulatitionData>, var availableAppointmentFragment: HomeDoctorFragment) : BaseAdapter() {


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
        binding.phoneTV.setText(arrayList[position].title)
        if (arrayList[position].imageFile != null && arrayList[position].imageFile!!.isNotEmpty()) {
            Glide.with(context).load(arrayList[position].imageFile).into(binding.iconIV)
        } else {
            Glide.with(context).clear(binding.iconIV)
        }
        binding.root.setOnClickListener {
            onItemClick(Const.OPEN_PAYMENT, position)
        }

        if (arrayList[position].isSelected) {
            viewHolder.itemView.background = ContextCompat.getDrawable(context, R.drawable.accent_color_drawable)
            binding.phoneTV.setTextColor(ContextCompat.getColor(context, R.color.White))
        } else {
            viewHolder.itemView.background = null
            binding.phoneTV.setTextColor(ContextCompat.getColor(context, R.color.MediumBlue))
        }

        if (position + 1 == arrayList.size) {
            availableAppointmentFragment.getAllModes()
        }
    }


}