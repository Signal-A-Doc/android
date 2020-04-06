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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager

import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentAppointmentBookedBinding
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.ui.fragment.AppointmentsFragment


class AppointmentBookedFragment : BaseFragment() {
    private var binding: FragmentAppointmentBookedBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.available_appointments), false, false)

        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appointment_booked, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        binding!!.viewBT.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.viewBT -> {
                baseActivity!!.supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                baseActivity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container, AppointmentsFragment())
                        .addToBackStack(null)
                        .commit()
            }

        }
    }


}
