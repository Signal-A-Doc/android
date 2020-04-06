package com.signalDoc_patient.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.AdapterAvailaibleDoctorsBinding

class AvailaibleDoctorsAdapter : BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AdapterAvailaibleDoctorsBinding>(LayoutInflater.from(parent.context), R.layout.adapter_availaible_doctors, parent, false)
        return BaseViewHolder(binding)


    }

    override fun getItemCount(): Int {
        return 10

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val binding = holder.binding as AdapterAvailaibleDoctorsBinding



        binding.root.setOnClickListener {
            onItemClick()
        }

    }

}