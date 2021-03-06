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

import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentAddCardBinding
import com.signalDoc_patient.ui.activity.LoginSignUpActivity
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.ui.fragment.SignupLoginPhase.LoginFragment


class AddCardFragment : BaseFragment() {
    private var binding: FragmentAddCardBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as LoginSignUpActivity).setToolbar(true)


        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_card, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.addCardBT.setOnClickListener(this)
        binding!!.skipTV.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.addCardBT -> baseActivity!!.replaceFragment(AddCardTwoFragment(), R.id.login_frame)
            R.id.skipTV -> baseActivity!!.replaceFragment(LoginFragment(), R.id.login_frame)
        }
    }


}
