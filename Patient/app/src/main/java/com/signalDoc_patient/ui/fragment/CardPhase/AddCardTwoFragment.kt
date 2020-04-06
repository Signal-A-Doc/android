/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_patient.ui.fragment.CardPhase


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentAddCardTwoBinding
import com.signalDoc_patient.ui.activity.LoginSignUpActivity
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.ui.fragment.SignupLoginPhase.CongratulationFragment


class AddCardTwoFragment : BaseFragment() {
    private var binding:FragmentAddCardTwoBinding?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as LoginSignUpActivity).setToolbar(true)
        if(binding==null) {
            binding= DataBindingUtil.inflate(inflater,R.layout.fragment_add_card_two,container,false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.addCardBT.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.addCardBT->baseActivity!!.replaceFragment(CongratulationFragment(),R.id.login_frame)


        }
    }

}



