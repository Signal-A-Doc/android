/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_patient.ui.fragment.SignupLoginPhase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentForgotPasswordBinding
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.ui.fragment.EmailSentFragment


class ForgotPasswordFragment : BaseFragment() {
    private var binding: FragmentForgotPasswordBinding? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false)
        }
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding!!.continueBT.setOnClickListener(this)
        binding!!.signInTV.setOnClickListener(this)
        binding!!.backIV.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.continueBT -> gotoEmailSentFragment()
            R.id.signInTV -> gotoLoginFragment()
            R.id.backIV -> gotoLoginFragment()
        }
    }

    private fun gotoLoginFragment() {
        baseActivity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.login_frame, LoginFragment())
                .addToBackStack(null)
                .commit()
    }


    private fun gotoEmailSentFragment() {
        baseActivity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.login_frame, EmailSentFragment())
                .addToBackStack(null)
                .commit()

    }
}
