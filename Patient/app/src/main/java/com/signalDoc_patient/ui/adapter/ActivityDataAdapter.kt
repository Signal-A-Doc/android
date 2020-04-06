package com.signalDoc_patient.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.AdapterActivityDataBinding

class ActivityDataAdapter :BaseAdapter(){
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AdapterActivityDataBinding>(LayoutInflater.from(parent.context), R.layout.adapter_activity_data, parent, false)
        return BaseViewHolder(binding)


    }

    override fun getItemCount(): Int {
        return 7

    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        val binding = viewHolder.binding as AdapterActivityDataBinding

    }

}