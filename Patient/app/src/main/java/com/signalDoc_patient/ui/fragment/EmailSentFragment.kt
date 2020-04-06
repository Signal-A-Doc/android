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
import com.signalDoc_patient.databinding.FragmentEmailSentBinding
import com.signalDoc_patient.ui.fragment.SignupLoginPhase.LoginFragment


class EmailSentFragment : BaseFragment(){


    private var binding: FragmentEmailSentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (binding==null) {
            binding = DataBindingUtil.inflate(inflater,R.layout.fragment_email_sent, container, false)
        }
        return binding!!.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi();
    }

    private fun initUi() {
        binding!!.backBT.setOnClickListener(this)

    }


    override fun onClick(view: View) {
        when(view.id)
        {
            R.id.backBT-> gotoResetPassFragment()

        }

    }

    private fun gotoResetPassFragment() {
        baseActivity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.login_frame, LoginFragment())
                .addToBackStack(null)
                .commit()
    }


}
