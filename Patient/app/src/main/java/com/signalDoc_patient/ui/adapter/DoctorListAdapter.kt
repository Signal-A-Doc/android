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
import com.signalDoc_patient.BuildConfig
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.AdapterListDoctorBinding
import com.signalDoc_patient.model.ProfileData
import com.signalDoc_patient.ui.activity.BaseActivity
import com.signalDoc_patient.ui.extensions.replaceFragWithArgs
import com.signalDoc_patient.ui.fragment.ProfileDoctorFragment
import java.lang.Exception


class DoctorListAdapter(var baseActivity: BaseActivity?, var datas: ArrayList<ProfileData>, var title: String) : BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AdapterListDoctorBinding>(LayoutInflater.from(parent.context), R.layout.adapter_list_doctor, parent, false)
        return BaseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val binding = holder.binding as AdapterListDoctorBinding
        binding.drTV.isSelected = true
        binding.nameTV.isSelected = true
        if (title.isNotEmpty()) {
            binding.nameTV.text = title
        } else {
            if (datas[holder.adapterPosition].symptoms != null && datas[holder.adapterPosition].symptoms!!.size > 0) {
                binding.nameTV.text = datas[holder.adapterPosition].symptoms!![0].title
            } else {
                binding.nameTV.text = baseActivity!!.getString(R.string.not_set)
            }
        }
        binding.data = datas[holder.adapterPosition]
        try {
            if (datas[holder.adapterPosition].profileFile.isNotEmpty()) {
                baseActivity!!.loadImage(baseActivity!!, datas[holder.adapterPosition].profileFile, R.mipmap.ic_default_profile, binding.imageIV)
            }

        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        }

        binding.root.setOnClickListener() {
            val bundle = Bundle()
            bundle.putParcelable("data", datas[holder.adapterPosition])
            bundle.putString("title", title)
            baseActivity!!.replaceFragWithArgs(ProfileDoctorFragment(), R.id.frame_container, bundle)

        }

    }
}