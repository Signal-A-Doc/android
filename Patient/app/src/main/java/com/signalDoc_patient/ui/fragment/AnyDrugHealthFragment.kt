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
import com.signalDoc_patient.databinding.FragmentAnyDrugBinding
import com.signalDoc_patient.ui.extensions.replaceFragment


class AnyDrugHealthFragment : BaseFragment() {
    private var binding:FragmentAnyDrugBinding?=null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if(binding==null){
            binding= DataBindingUtil.inflate(inflater,R.layout.fragment_any_drug, container, false)

        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding!!.yesCV.setOnClickListener(this)
        binding!!.noCV.setOnClickListener(this)
    }

    override fun onClick(v: View) {

        when(v.id){
            R.id.yesCV->{
                baseActivity!!.replaceFragment(WhichDrugFragment(),R.id.frame_container)
            }
            R.id.noCV->{
                baseActivity!!.replaceFragment(AnyMedicalHealthFragment(),R.id.frame_container)
            }
        }
    }


}
