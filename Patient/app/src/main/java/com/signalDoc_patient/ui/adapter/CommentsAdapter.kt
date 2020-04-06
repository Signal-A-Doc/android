package com.signalDoc_patient.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.AdapterCommentsBinding

class CommentsAdapter : BaseAdapter() {

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