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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.FragmentAppointmentBinding
import com.signalDoc_doctor.ui.activity.MainActivity
import com.signalDoc_doctor.ui.adapter.ViewPagerAdapter
import com.signalDoc_doctor.ui.fragment.BaseFragment
import com.signalDoc_doctor.utils.Const


class AppointmentFragment : BaseFragment() {
    private var binding: FragmentAppointmentBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.appointments))
        (baseActivity as MainActivity).selectTab(Const.APPOITMENTS)
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appointment, container, false)
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
        pagerAdapter.addFragment(PendingAppointmentFragment(), baseActivity!!.getString(R.string.pending))
        pagerAdapter.addFragment(UpcomingFragment(), baseActivity!!.getString(R.string.upcoming))
        pagerAdapter.addFragment(PastAppointmentFragment(), baseActivity!!.getString(R.string.past))
        binding!!.viewPager.setAdapter(pagerAdapter)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding!!.viewPager.removeAllViews()
    }
}
