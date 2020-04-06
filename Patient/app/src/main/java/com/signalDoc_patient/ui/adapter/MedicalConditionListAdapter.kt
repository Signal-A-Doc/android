package com.signalDoc_patient.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.AdapterChildSymptomsBinding
import com.signalDoc_patient.model.CategoriesData
import com.signalDoc_patient.ui.activity.BaseActivity
import com.signalDoc_patient.ui.fragment.MedicalConditionListFragment
import com.signalDoc_patient.utils.OnRemoveListener

class MedicalConditionListAdapter(var baseActivity: BaseActivity, var datas: ArrayList<CategoriesData>, val fragment: MedicalConditionListFragment) : BaseAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AdapterChildSymptomsBinding>(LayoutInflater.from(parent.context), R.layout.adapter_child_symptoms, parent, false)
        return BaseViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return datas.size

    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        val binding = viewHolder.binding as AdapterChildSymptomsBinding
        binding.subTitleTV.text = datas[viewHolder.adapterPosition].title
        binding.subTitleCB.isChecked = datas[viewHolder.adapterPosition].ischeked
        if (position + 1 == datas.size) {
            fragment.getMedicalConditionsList()
        }
        binding.subTitleCB.setOnCheckedChangeListener { buttonView, isChecked ->
            datas[viewHolder.adapterPosition].ischeked = isChecked

        }

    }
}