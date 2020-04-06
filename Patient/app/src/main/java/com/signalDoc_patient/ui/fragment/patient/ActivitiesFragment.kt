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
import com.signalDoc_patient.databinding.FragmentActivitiesBinding
import com.signalDoc_patient.ui.adapter.ActivityDataAdapter
import com.signalDoc_patient.ui.fragment.BaseFragment


class ActivitiesFragment : BaseFragment() {
    private var binding:FragmentActivitiesBinding?=null
    private var adapterActivities:ActivityDataAdapter?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if(binding==null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_activities, container, false)
        }
        return binding!!.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {

        setActivitiesAdapter()
    }

    private fun setActivitiesAdapter() {
        adapterActivities=ActivityDataAdapter()
        binding!!.monthRV.adapter=adapterActivities
    }


}
