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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentAnyMedicalHealthBinding
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.ui.fragment.ReviewHealthFragment
import com.signalDoc_patient.ui.fragment.WhichMedicationHealthFragment


class AnyMedicalHealthFragment : BaseFragment() {
    private var binding: FragmentAnyMedicalHealthBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_any_medical_health, container, false)

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
        when (v.id) {
            R.id.yesCV -> {
                baseActivity!!.replaceFragment(WhichMedicationHealthFragment(), R.id.frame_container)
            }

            R.id.noCV -> {
                baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.under_delvelopment))

                //baseActivity!!.replaceFragment(ReviewHealthFragment(),R.id.frame_container)
            }

        }
    }


}
