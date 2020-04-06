package com.signalDoc_patient.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.AdapterChildSymptomsBinding
import com.signalDoc_patient.model.ReviewHealthData
import com.signalDoc_patient.ui.activity.BaseActivity
import com.signalDoc_patient.utils.Const

class ReviewHealthAdapter(var baseActivity: BaseActivity, var datas: ArrayList<ReviewHealthData>, var drugList: ArrayList<String>, var medicalList: ArrayList<String>, var type: Int) : BaseAdapter() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AdapterChildSymptomsBinding>(LayoutInflater.from(parent.context), R.layout.adapter_child_symptoms, parent, false)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val binding = holder.binding as AdapterChildSymptomsBinding
        if (type == Const.APPOITMENTS) {
            binding.subTitleTV.text = datas[holder.adapterPosition].medicine_name
        } else if (type == Const.MEDICAL) {
            binding.subTitleTV.text = drugList[holder.adapterPosition]
        } else if (type == Const.ACCOUNT) {
            binding.subTitleTV.text = medicalList[holder.adapterPosition]
        }
        binding.subTitleCB.visibility = View.GONE
        binding.crossIV.visibility = View.VISIBLE

    }

    override fun getItemCount(): Int {
        return datas.size
    }

}