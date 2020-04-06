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
import com.signalDoc_patient.databinding.FragmentHealthProfileBinding
import com.signalDoc_patient.model.HealthProfileData
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.extensions.replaceFragWithArgs
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.patient.CurrentlyTakingHealthFragment


class HealthProfileFragment : BaseFragment() {
    private var binding: FragmentHealthProfileBinding? = null
    private var data: HealthProfileData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable("data")
        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.health_profile), false)
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_health_profile, container, false)

        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.continueBT.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.continueBT ->
                gotoNextFrag()
        }
    }

    private fun gotoNextFrag() {
        val bundle = Bundle()
            bundle.putParcelable("data",data)
            baseActivity!!.replaceFragWithArgs(CurrentlyTakingHealthFragment(), R.id.frame_container, bundle)


    }
}
