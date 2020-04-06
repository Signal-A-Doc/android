/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.ui.fragment.SignupLoginPhase


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.FragmentCongratulationBinding
import com.signalDoc_doctor.ui.activity.LoginSignUpActivity
import com.signalDoc_doctor.ui.activity.MainActivity
import com.signalDoc_doctor.ui.extensions.replaceFragment
import com.signalDoc_doctor.ui.fragment.BaseFragment


class CongratulationFragment : BaseFragment() {
    private var binding: FragmentCongratulationBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as LoginSignUpActivity).setToolbar(false)
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_congratulation, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.startedBT.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.startedBT -> baseActivity!!.replaceFragment(LoginFragment(), R.id.login_frame)
        }
    }

    private fun gotoMainActivity() {
        startActivity(Intent(baseActivity, MainActivity::class.java))
        baseActivity!!.finish()
    }


}
