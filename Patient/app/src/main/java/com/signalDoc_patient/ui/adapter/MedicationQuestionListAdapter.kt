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

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.MedicationAdapterDesignBinding
import com.signalDoc_patient.model.QuestionListData
import com.signalDoc_patient.ui.extensions.checkString
import com.signalDoc_patient.ui.extensions.isBlank
import com.signalDoc_patient.ui.fragment.WhichMedicationHealthFragment
import com.signalDoc_patient.utils.Const
import kotlinx.android.synthetic.main.fragment_sign_up.*

class MedicationQuestionListAdapter(var arrayList: ArrayList<QuestionListData>, var whichMedicationHealthFragment: WhichMedicationHealthFragment) : BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<MedicationAdapterDesignBinding>(LayoutInflater.from(parent.context), R.layout.medication_adapter_design, parent, false)
        return BaseViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        val binding = viewHolder.binding as MedicationAdapterDesignBinding

        binding.medicationOneET.text = arrayList[position].title

        binding.ansET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                if (!binding.ansET.isBlank()) {
                    arrayList[viewHolder.adapterPosition].ansTitle = binding.ansET.checkString()
                }
            }


            override fun afterTextChanged(s: Editable) {

            }
        })

        if (position + 1 == arrayList.size) {
            binding.viewLine.visibility = View.GONE
            whichMedicationHealthFragment.apiGetQuestion()
        }else{
            binding.viewLine.visibility = View.VISIBLE
        }
    }


}