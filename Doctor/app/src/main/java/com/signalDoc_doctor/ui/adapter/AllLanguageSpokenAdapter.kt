/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_doctor.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.AllLanguageAdapterDesignBinding
import com.signalDoc_doctor.model.AllLanguageData
import com.signalDoc_doctor.ui.fragment.SignupLoginPhase.SignUpTwoFragment

class AllLanguageSpokenAdapter(var arrayList: ArrayList<AllLanguageData>, var signUpTwoFragment: SignUpTwoFragment) : BaseAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AllLanguageAdapterDesignBinding>(LayoutInflater.from(parent.context), R.layout.all_language_adapter_design, parent, false)
        return BaseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        val binding = viewHolder.binding as AllLanguageAdapterDesignBinding
        binding.titleTV.setText(arrayList[position].value)
        binding.titleTV.isChecked = arrayList[position].isSelected
        if (position + 1 == arrayList.size) {
            signUpTwoFragment.apiHitAllLanguahe()
        }

        binding.titleTV.setOnClickListener {
            signUpTwoFragment.selectedItem(position)
        }
    }


}