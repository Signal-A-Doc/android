/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.AdapterCommentsBinding

class CommentsAdapter: BaseAdapter() {

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(viewHolder, position)
        val binding = viewHolder.binding as AdapterCommentsBinding





    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AdapterCommentsBinding>(LayoutInflater.from(parent.context), R.layout.adapter_comments, parent, false)
        return BaseViewHolder(binding)




    }

}