package com.signalDoc_patient.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.AdapterCategoriesBinding
import com.signalDoc_patient.model.Symptom
import com.signalDoc_patient.ui.activity.BaseActivity
import com.signalDoc_patient.ui.extensions.replaceFragWithArgs
import com.signalDoc_patient.ui.fragment.MedicalProfessionTwoFragment
import com.signalDoc_patient.utils.Const

class CategoriesAdapter(var baseActivity: BaseActivity?, var datas: ArrayList<Symptom>) : BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AdapterCategoriesBinding>(LayoutInflater.from(parent.context), R.layout.adapter_categories, parent, false)
        return BaseViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val binding = holder.binding as AdapterCategoriesBinding
        val context = holder.itemView.context
        binding.data = datas[holder.adapterPosition]
        if (datas[holder.adapterPosition].image_file != null && !datas[holder.adapterPosition].image_file!!.isEmpty()) {
            Glide.with(context).load(datas[holder.adapterPosition].image_file).into(binding.imageIV)
        } else {
            Glide.with(context).clear(binding.imageIV)
        }

        binding.root.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", datas[holder.adapterPosition].id)
            bundle.putInt("type", Const.FIRST_NAME)
            bundle.putString("title", datas[holder.adapterPosition].title)
            baseActivity!!.replaceFragWithArgs(MedicalProfessionTwoFragment(), R.id.frame_container, bundle)


        }

    }

}