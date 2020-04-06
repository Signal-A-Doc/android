/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.ui.fragment.SignupLoginPhase


import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.signalDoc_doctor.BuildConfig

import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.FragmentSignUpBinding
import com.signalDoc_doctor.model.SignupData
import com.signalDoc_doctor.ui.activity.LoginSignUpActivity
import com.signalDoc_doctor.ui.extensions.checkString
import com.signalDoc_doctor.ui.extensions.replaceFragWithArgs
import com.signalDoc_doctor.ui.extensions.replaceFragment
import com.signalDoc_doctor.ui.fragment.BaseFragment
import com.signalDoc_doctor.utils.ClickHandler
import com.signalDoc_doctor.utils.Const
import com.signalDoc_doctor.viewModel.patientViewModel.SignupViewModel
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.emailET


class SignUpFragment : BaseFragment(), ClickHandler {
    private var binding: FragmentSignUpBinding? = null
    private lateinit var signupViewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signupViewModel = ViewModelProviders.of(this)[SignupViewModel::class.java]
        signupViewModel.setData(baseActivity!!, restFullClient!!, api!!, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (baseActivity as LoginSignUpActivity).setToolbar(true)
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        }
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        if (BuildConfig.DEBUG) {
            emailET.setText("riya@doctor.com")
            passET.setText("Admin123")
            cnfrmPassET.setText("Admin123")
        }
        binding!!.handleClick = this
        passET.transformationMethod = PasswordTransformationMethod()
        cnfrmPassET.transformationMethod = PasswordTransformationMethod()
        signupViewModel.setSpanbleText(termsOfTV, alreadyHaveTV)
        signupViewModel.hanldePasswordLenght(passET, tooWeakView, passStatusTV, goodView, strongView)


    }

    override fun onHandleClick(vararg data: Any) {
        if (data.isNotEmpty()) {
            val view = data[0] as View
            when (view.id) {
                R.id.continueBT -> {
                    if (signupViewModel.isValidate(emailET, passET, cnfrmPassET)) {
                        signupViewModel.gotoSecondStepOfSignup(emailET, passET)
                    }
                }
                R.id.alreadyHaveTV -> baseActivity!!.replaceFragment(LoginFragment(), R.id.login_frame)

                R.id.showPassIV -> {
                    hidePassIV.visibility = View.VISIBLE
                    showPassIV.visibility = View.GONE
                    passET.transformationMethod = null
                }
                R.id.hidePassIV -> {
                    hidePassIV.visibility = View.GONE
                    showPassIV.visibility = View.VISIBLE
                    passET.transformationMethod = PasswordTransformationMethod()
                }
                R.id.showConfirmIV -> {
                    hideConfirmIV.visibility = View.VISIBLE
                    showConfirmIV.visibility = View.GONE
                    cnfrmPassET.transformationMethod = null
                }
                R.id.hideConfirmIV -> {
                    hideConfirmIV.visibility = View.GONE
                    showConfirmIV.visibility = View.VISIBLE
                    cnfrmPassET.transformationMethod = PasswordTransformationMethod()
                }
            }
        }
    }

}
