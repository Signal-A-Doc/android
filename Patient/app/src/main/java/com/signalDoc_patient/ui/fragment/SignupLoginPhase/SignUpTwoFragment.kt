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


import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hbb20.CountryCodePicker
import com.signalDoc_patient.BuildConfig

import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentSignUpTwoBinding
import com.signalDoc_patient.model.SignupData
import com.signalDoc_patient.ui.activity.BaseActivity
import com.signalDoc_patient.ui.activity.LoginSignUpActivity
import com.signalDoc_patient.ui.extensions.checkString
import com.signalDoc_patient.ui.extensions.replaceFragWithArgs
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.utils.Const
import com.signalDoc_patient.viewModel.LoginSignupViewModel.SignupViewModel
import kotlinx.android.synthetic.main.fragment_basic_information.view.*
import kotlinx.android.synthetic.main.fragment_sign_up.alreadyHaveTV
import kotlinx.android.synthetic.main.fragment_sign_up.termsOfTV
import kotlinx.android.synthetic.main.fragment_sign_up_two.*
import java.io.File


class SignUpTwoFragment : BaseFragment(), BaseActivity.PermCallback {
    private var binding: FragmentSignUpTwoBinding? = null
    private var data: SignupData? = null
    private lateinit var signupViewModel: SignupViewModel
    private var profileFile: File? = null
    private var gender = Const.MALE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable("data")
        }
        signupViewModel = ViewModelProviders.of(this)[SignupViewModel::class.java]
        signupViewModel.setData(baseActivity!!, restFullClient!!, api!!, this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as LoginSignUpActivity).setToolbar(true)
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_two, container, false)
        }
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        signupViewModel.setFirstLetterInCaps(firstNameET, lastNameET, addressET)
        if (BuildConfig.DEBUG) {
            firstNameET.setText("Riya")
            lastNameET.setText("Mahajan")
            countryET.defaultCountryCode
            addressET.setText("Mohali phase 8")
            phoneNumberET.setText("65643634")
        }

        binding!!.continueBT.setOnClickListener(this)
        binding!!.alreadyHaveTV.setOnClickListener(this)
        binding!!.dateOfBirthET.setOnClickListener(this)
        binding!!.maleRB.setOnClickListener(this)
        binding!!.femaleRB.setOnClickListener(this)
        binding!!.abcIV.uploadTV.setOnClickListener(this)



        binding!!.countryET.showFullName(true)
        binding!!.countryET.showNameCode(false)
        binding!!.countryET.setShowPhoneCode(false)


        val text = baseActivity!!.getString(R.string.terms_privacy)
        termsOfTV.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE)
        val accountText = baseActivity!!.getString(R.string.already_have_an_account_sign_in) + " " + baseActivity!!.getString(R.string.signIn)
        alreadyHaveTV.setText(Html.fromHtml(accountText), TextView.BufferType.SPANNABLE)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.continueBT -> {
                if (signupViewModel.isInfoValid(profileFile, firstNameET, lastNameET, phoneNumberET, dateOfBirthET, countryET, addressET)) {
                    gotoThirdStepOfSignup()
                }

            }
            R.id.alreadyHaveTV -> baseActivity!!.replaceFragment(LoginFragment(), R.id.login_frame)
            R.id.uploadTV -> {
                if (baseActivity!!.checkPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), Const.IMAGE_CODE, this)) {
                    chooseImagePicker(Const.IMAGE_CODE, false, true, true)
                }

            }
            R.id.dateOfBirthET -> {
                signupViewModel.getCalenderInstance()
                signupViewModel.showDatePicker(dateOfBirthET)
            }
            R.id.femaleRB -> {
                gender = Const.FEMALE
                femaleRB.setBackgroundResource(R.drawable.blue_border_box)
                maleRB.setBackgroundResource(R.drawable.grey_border_box)
                femaleRB.isChecked = true
                maleRB.isChecked = false
            }
            R.id.maleRB -> {
                gender = Const.MALE
                maleRB.isChecked = true
                femaleRB.isChecked = false
                femaleRB.setBackgroundResource(R.drawable.grey_border_box)
                maleRB.setBackgroundResource(R.drawable.blue_border_box)
            }


        }
    }

    private fun gotoThirdStepOfSignup() {
        data!!.firstName = firstNameET.checkString()
        data!!.lastName = lastNameET.checkString()
        data!!.profileFile = profileFile
        data!!.contact_number = phoneNumberET.checkString()
        data!!.date_of_birth = dateOfBirthET.checkString()
        data!!.country = countryET.selectedCountryName
        data!!.address = addressET.checkString()
        data!!.gender = gender
        val bundle = Bundle()
        bundle.putParcelable("data", data)
        baseActivity!!.replaceFragWithArgs(SignUpThreeFragment(), R.id.login_frame, bundle)
    }

    override fun permGranted(resultCode: Int) {
        if (resultCode == Const.IMAGE_CODE) {
            chooseImagePicker(Const.IMAGE_CODE, false, true, true)
        }
    }

    override fun permDenied(resultCode: Int) {
    }

    override fun onImageSelected(uri: Uri?, requestCode: Int) {
        super.onImageSelected(uri, requestCode)

        if (requestCode == Const.IMAGE_CODE) {
            profileFile = File(uri!!.path!!)

            val fileSizeInBytes=profileFile!!.length()
            val fileSizeInKB = fileSizeInBytes / 1024
            val fileSizeInMB = fileSizeInKB/1024



            if (fileSizeInMB < 1) {

                Glide.with(baseActivity!!).load(uri).apply(RequestOptions().placeholder(R.mipmap.ic_defaultuser).error(R.mipmap.ic_defaultuser)).into(abcIV.profileIV)

            }
            else{
                baseActivity!!.showErrorToast(getString(R.string.imageValidation))
            }
        }

    }

}


