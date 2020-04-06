package com.signalDoc_patient.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.AdapterChatBinding

class ChatAdapter :BaseAdapter() {

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(viewHolder, position)
        val binding = viewHolder.binding as AdapterChatBinding

    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AdapterChatBinding>(LayoutInflater.from(parent.context), R.layout.adapter_chat, parent, false)
        return BaseViewHolder(binding)


    }

}