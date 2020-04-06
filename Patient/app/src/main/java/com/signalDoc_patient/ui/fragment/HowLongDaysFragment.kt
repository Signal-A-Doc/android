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
import com.signalDoc_patient.databinding.FragmentHowLongDaysBinding
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.fragment.AppointmentInfoThreeFragment
import com.signalDoc_patient.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_how_long_days.*


class HowLongDaysFragment : BaseFragment() {

    private var binding: FragmentHowLongDaysBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.appointment_info),true)

        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_how_long_days, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        rangeSeekbar.max = 12f
        rangeSeekbar.min = 1f
        binding!!.continueBT.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.continueBT -> goToAppointmentInfoThree()
        }
    }

    private fun goToAppointmentInfoThree() {
        baseActivity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, AppointmentInfoThreeFragment())
                .addToBackStack(null)
                .commit()
    }


}
