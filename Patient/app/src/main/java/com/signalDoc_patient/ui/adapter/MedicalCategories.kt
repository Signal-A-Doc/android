/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_patient.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.AdapterMedicalCategoriesBinding
import com.signalDoc_patient.model.CategoriesData
import com.signalDoc_patient.ui.activity.BaseActivity
import com.signalDoc_patient.ui.extensions.replaceFragWithArgs
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.MedicalProfessionTwoFragment

class MedicalCategories(var baseActivity: BaseActivity?, var datas: ArrayList<CategoriesData>) : BaseAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AdapterMedicalCategoriesBinding>(LayoutInflater.from(parent.context), R.layout.adapter_medical_categories, parent, false)
        return BaseViewHolder(binding)


    }

    override fun getItemCount(): Int {
        return datas.size

    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        val binding = viewHolder.binding as AdapterMedicalCategoriesBinding
        binding.genralPrediction.text = datas[viewHolder.adapterPosition].title
        baseActivity!!.loadImage(baseActivity!!, datas[viewHolder.adapterPosition].image_file, R.mipmap.ic_general, binding.imageIV)
        binding.root.setOnClickListener() {
            val bundle = Bundle()
            bundle.putInt("id", datas[viewHolder.adapterPosition].id)
            bundle.putString("title", datas[viewHolder.adapterPosition].title)
            baseActivity!!.replaceFragWithArgs(MedicalProfessionTwoFragment(), R.id.frame_container, bundle)

        }


    }
}
