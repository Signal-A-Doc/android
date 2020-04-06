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
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout

import com.signalDoc_patient.R
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.ui.adapter.ViewPagerAdapter
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.signalDoc_patient.databinding.FragmentAppointmentsBinding
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.adapter.BaseAdapter
import com.signalDoc_patient.ui.adapter.UpcomingAppointmentsAdapter
import com.signalDoc_patient.ui.fragment.PastAppointmentFragment
import com.signalDoc_patient.ui.fragment.UpcomingAppointmentFragment
import com.signalDoc_patient.utils.Const


class AppointmentsFragment : BaseFragment() {
    private var binding: FragmentAppointmentsBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.appointments),true)
        (baseActivity as MainActivity).selectTab(Const.APPOITMENTS)
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appointments, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        binding!!.tabLayout.setupWithViewPager(binding!!.viewPager);
        setUpViewPager()

    }


    private fun setUpViewPager() {
        val pagerAdapter = ViewPagerAdapter(childFragmentManager)
        pagerAdapter.addFragment(UpcomingAppointmentFragment(), baseActivity!!.getString(R.string.upcoming))
        pagerAdapter.addFragment(PastAppointmentFragment(), baseActivity!!.getString(R.string.past))
        binding!!.viewPager.setAdapter(pagerAdapter)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding!!.viewPager.removeAllViews()
    }
}
