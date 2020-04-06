/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_patient.ui.fragment.patient


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentCurrentlyTakingHealthBinding
import com.signalDoc_patient.model.HealthProfileData
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.extensions.replaceFragWithArgs
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.WhichMedicationHealthFragment
import com.signalDoc_patient.ui.fragment.BaseFragment


class CurrentlyTakingHealthFragment : BaseFragment() {
    private var binding: FragmentCurrentlyTakingHealthBinding? = null
    private var data: HealthProfileData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable("data")
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.health_profile), false)

        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_currently_taking_health, container, false)

        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.yesTV.setOnClickListener(this)
        binding!!.yesCV.setOnClickListener(this)
        binding!!.noTV.setOnClickListener(this)
        binding!!.noCV.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.yesTV, R.id.yesCV ->
                gotoWhichMedicalHealthFrag()
            R.id.noTV, R.id.noCV ->
                gotoAnyDrugHealthFrag()
        }
    }

    private fun gotoWhichMedicalHealthFrag() {
        val bundle = Bundle()
        bundle.putParcelable("data", data)
        baseActivity!!.replaceFragWithArgs(WhichMedicationHealthFragment(), R.id.frame_container, bundle)

    }

    private fun gotoAnyDrugHealthFrag() {
        val bundle = Bundle()
        bundle.putParcelable("data", data)
        baseActivity!!.replaceFragWithArgs(AnyDrugHealthFragment(), R.id.frame_container, bundle)

    }
}