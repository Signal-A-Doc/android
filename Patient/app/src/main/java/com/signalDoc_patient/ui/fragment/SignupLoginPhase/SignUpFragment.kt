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
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.signalDoc_patient.BuildConfig

import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentSignUpBinding
import com.signalDoc_patient.model.SignupData
import com.signalDoc_patient.ui.activity.LoginSignUpActivity
import com.signalDoc_patient.ui.extensions.checkString
import com.signalDoc_patient.ui.extensions.replaceFragWithArgs
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.utils.Const
import com.signalDoc_patient.viewModel.LoginSignupViewModel.SignupViewModel
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.emailET


class SignUpFragment : BaseFragment() {
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
            emailET.setText("riya@signal.com")
            passET.setText("Admin123")
            cnfrmPassET.setText("Admin123")
        }
        passET.transformationMethod = PasswordTransformationMethod()
        cnfrmPassET.transformationMethod = PasswordTransformationMethod()
        binding!!.continueBT.setOnClickListener(this)
        binding!!.alreadyHaveTV.setOnClickListener(this)
        binding!!.showPassIV.setOnClickListener(this)
        binding!!.hidePassIV.setOnClickListener(this)
        binding!!.showConfirmIV.setOnClickListener(this)
        binding!!.hideConfirmIV.setOnClickListener(this)
        val text = baseActivity!!.getString(R.string.terms_privacy)
        termsOfTV.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE)

        val accountText = baseActivity!!.getString(R.string.already_have_an_account_sign_in) + " " + baseActivity!!.getString(R.string.signIn)
        alreadyHaveTV.setText(Html.fromHtml(accountText), TextView.BufferType.SPANNABLE)



        passET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                if (charSequence.length <= Const.WEAK_LENGHT) {
                    tooWeakView.setBackgroundResource(R.drawable.password_strength_bar_filled)
                    passStatusTV.text = baseActivity!!.getString(R.string.too_weak)
                    goodView.setBackgroundResource(R.drawable.password_strength_bar)
                    strongView.setBackgroundResource(R.drawable.password_strength_bar)
                } else if (charSequence.length > Const.WEAK_LENGHT && charSequence.length < Const.STRONG_LENGHT) {
                    tooWeakView.setBackgroundResource(R.drawable.paasword_medium_bar)
                    passStatusTV.text = baseActivity!!.getString(R.string.normal)
                    goodView.setBackgroundResource(R.drawable.paasword_medium_bar)
                    strongView.setBackgroundResource(R.drawable.password_strength_bar)
                } else if (!baseActivity!!.isValidPassword(passET.checkString())) {
                    tooWeakView.setBackgroundResource(R.drawable.paasword_medium_bar)
                    passStatusTV.text = baseActivity!!.getString(R.string.normal)
                    goodView.setBackgroundResource(R.drawable.paasword_medium_bar)
                    strongView.setBackgroundResource(R.drawable.password_strength_bar)
                } else {
                    tooWeakView.setBackgroundResource(R.drawable.password_strong_bar)
                    passStatusTV.text = baseActivity!!.getString(R.string.strong)
                    goodView.setBackgroundResource(R.drawable.password_strong_bar)
                    strongView.setBackgroundResource(R.drawable.password_strong_bar)
                }
            }


            override fun afterTextChanged(s: Editable) {

            }
        })
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.continueBT -> {
                if (signupViewModel.isValidate(emailET, passET, cnfrmPassET)) {
                    gotoSecondStepOfSignup()
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

    private fun gotoSecondStepOfSignup() {
        val data = SignupData()
        val bundle = Bundle()
        data.email = emailET.checkString()
        data.password = passET.checkString()
        bundle.putParcelable("data", data)
        baseActivity!!.replaceFragWithArgs(SignUpTwoFragment(), R.id.login_frame, bundle)
    }


}
