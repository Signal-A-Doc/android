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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.AdapterCardBinding
import com.signalDoc_patient.databinding.AdapterDocumentsBinding
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.PaymentPrimaryCardFragment

class DocumentsAdapter:BaseAdapter() {

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(viewHolder, position)
        val binding = viewHolder.binding as AdapterDocumentsBinding


    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AdapterDocumentsBinding>(LayoutInflater.from(parent.context),R.layout.adapter_documents , parent, false)
        return BaseViewHolder(binding)




    }
}