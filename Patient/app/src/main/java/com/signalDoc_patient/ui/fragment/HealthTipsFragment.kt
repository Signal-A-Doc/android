/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_patient.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentHealthTipsBinding
import com.signalDoc_patient.ui.adapter.HealthTipsAdapter

class HealthTipsFragment : BaseFragment() {

    private var binding: FragmentHealthTipsBinding?=null
    private var adapterHealthTips:HealthTipsAdapter?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        if(binding==null){
            binding= DataBindingUtil.inflate(inflater,R.layout.fragment_health_tips, container, false)

        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHealthTipsAdapter()
    }

    private fun setHealthTipsAdapter() {
        adapterHealthTips=HealthTipsAdapter(baseActivity)
        binding!!.healthRv.adapter=adapterHealthTips

    }


}



