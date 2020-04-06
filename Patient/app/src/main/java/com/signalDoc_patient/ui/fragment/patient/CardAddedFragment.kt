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

import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentCardAddedBinding
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.ui.fragment.PaymentFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_card_added.*


class CardAddedFragment : BaseFragment() {




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        baseActivity!!.navBNV.visibility=View.GONE
        baseActivity!!.toolbar.visibility=View.GONE

        return inflater.inflate(R.layout.fragment_card_added, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        continueBT.setOnClickListener {
            if(baseActivity is MainActivity)
            {
            baseActivity!!.replaceFragment(PaymentFragment(),R.id.frame_container)
        }}
    }


}
