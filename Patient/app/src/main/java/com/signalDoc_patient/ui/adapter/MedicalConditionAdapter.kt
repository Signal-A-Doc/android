package com.signalDoc_patient.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.MedicalConditionAdapterBinding
import com.signalDoc_patient.model.CategoriesData
import com.signalDoc_patient.ui.activity.BaseActivity
import com.signalDoc_patient.utils.OnRemoveListener

class MedicalConditionAdapter (var baseActivity: BaseActivity, var datas: ArrayList<CategoriesData>, val onRemoveListener: OnRemoveListener) : BaseAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<MedicalConditionAdapterBinding>(LayoutInflater.from(parent.context), R.layout.medical_condition_adapter, parent, false)
        return BaseViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return datas.size

    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        val binding = viewHolder.binding as MedicalConditionAdapterBinding
        binding.titleTV.text = datas[viewHolder.adapterPosition].title
        binding.removeIV.setOnClickListener {
            if (viewHolder.adapterPosition != -1) {
                onRemoveListener.onItemRemoved(viewHolder.adapterPosition)
            } else {
                baseActivity.showToast(baseActivity.getString(R.string.unable_to_reciver))
            }
        }
    }


}