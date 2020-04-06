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
import com.signalDoc_patient.databinding.FragmentCurrentlyTakingHealthBinding
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.AnyDrugHealthFragment
import com.signalDoc_patient.ui.fragment.WhichMedicationHealthFragment
import com.signalDoc_patient.ui.fragment.BaseFragment


class CurrentlyTakingHealthFragment : BaseFragment() {
private var binding:FragmentCurrentlyTakingHealthBinding?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if(binding==null){
            binding= DataBindingUtil.inflate(inflater,R.layout.fragment_currently_taking_health, container, false)

        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.yesTV.setOnClickListener(this)
        binding!!.noTV.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.yesTV->
                baseActivity!!.replaceFragment(WhichMedicationHealthFragment(),R.id.frame_container)
            R.id.noTV->
                baseActivity!!.replaceFragment(AnyDrugHealthFragment(),R.id.frame_container)
        }
    }
}