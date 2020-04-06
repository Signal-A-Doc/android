/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.ui.fragment.WelcomePhase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.FragmentWelcomeOneBinding
import com.signalDoc_doctor.ui.activity.LoginSignUpActivity
import com.signalDoc_doctor.ui.extensions.replaceFragment
import com.signalDoc_doctor.ui.fragment.BaseFragment
import com.signalDoc_doctor.ui.fragment.SignupLoginPhase.LoginFragment
import com.signalDoc_doctor.utils.ClickHandler

class FirstInfroFragment : BaseFragment(), ClickHandler {
    private var binding: FragmentWelcomeOneBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (baseActivity as LoginSignUpActivity).setToolbar(false)
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome_one, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        binding!!.handleClick = this


    }

    override fun onHandleClick(vararg data: Any) {
        if (data.isNotEmpty()) {
            val view = data[0] as View
            when (view.id) {
                R.id.signinTV,R.id.startCV, R.id.startBT  -> {
                    baseActivity!!.replaceFragment(LoginFragment(), R.id.login_frame)

                }
            }
        }
    }

}