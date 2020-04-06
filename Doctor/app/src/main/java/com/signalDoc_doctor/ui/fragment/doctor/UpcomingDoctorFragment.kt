/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.ui.fragment.doctor


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.FragmentUpcomingDoctorBinding
import com.signalDoc_doctor.model.UpcomingAppointmentData
import com.signalDoc_doctor.ui.adapter.UpcomingAdapter
import com.signalDoc_doctor.ui.fragment.BaseFragment


class UpcomingDoctorFragment : BaseFragment() {

    private var binding: FragmentUpcomingDoctorBinding? = null
    private var adapterUpcoming: UpcomingAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upcoming_doctor, container, false)
        }

        return binding!!.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {

        setUpcomingAppointmentAdapter()
    }

    private fun setUpcomingAppointmentAdapter() {
        adapterUpcoming = UpcomingAdapter(ArrayList<UpcomingAppointmentData>())
        binding!!.upcomingRV.adapter = adapterUpcoming
    }


}
