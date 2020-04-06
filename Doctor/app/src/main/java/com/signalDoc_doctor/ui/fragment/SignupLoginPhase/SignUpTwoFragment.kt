/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.ui.fragment.SignupLoginPhase


import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.signalDoc_doctor.BuildConfig

import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.FragmentSignUpTwoBinding
import com.signalDoc_doctor.databinding.MultiLanguageSelectDialogDesignBinding
import com.signalDoc_doctor.model.AllLanguageData
import com.signalDoc_doctor.model.SignupData
import com.signalDoc_doctor.ui.activity.BaseActivity
import com.signalDoc_doctor.ui.activity.LoginSignUpActivity
import com.signalDoc_doctor.ui.adapter.AllLanguageSpokenAdapter
import com.signalDoc_doctor.ui.adapter.DoctorLanguageSpokenAdapter
import com.signalDoc_doctor.ui.extensions.handleException
import com.signalDoc_doctor.ui.extensions.replaceFragment
import com.signalDoc_doctor.ui.fragment.BaseFragment
import com.signalDoc_doctor.utils.Const
import com.signalDoc_doctor.viewModel.patientViewModel.SignupViewModel

import kotlinx.android.synthetic.main.fragment_basic_information.view.*
import kotlinx.android.synthetic.main.fragment_sign_up.alreadyHaveTV
import kotlinx.android.synthetic.main.fragment_sign_up.termsOfTV
import kotlinx.android.synthetic.main.fragment_sign_up_two.*
import org.json.JSONObject
import java.io.File
import java.lang.Exception


class SignUpTwoFragment : BaseFragment(), BaseActivity.PermCallback {
    private var binding: FragmentSignUpTwoBinding? = null
    private var data: SignupData? = null
    private lateinit var signupViewModel: SignupViewModel
    private var profileFile: File? = null
    private var gender = Const.MALE
    private var arrayListSelected = ArrayList<String>()
    private var arrayListId = ArrayList<String>()
    private var adapter: DoctorLanguageSpokenAdapter? = null
    private var adapterLanguage: AllLanguageSpokenAdapter? = null
    private var arrayList = ArrayList<AllLanguageData>()
    private var isSingle = false
    private var pageCount = 0

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
        binding!!.languageSpokenRV.layoutManager = LinearLayoutManager(baseActivity!!, RecyclerView.HORIZONTAL, false)
        initUI()
    }

    private fun initUI() {
        signupViewModel.setFirstLetterInCaps(firstNameET, lastNameET, addressET)
        if (BuildConfig.DEBUG) {
            firstNameET.setText("Riya")
            lastNameET.setText("Mahajan")
            countryET.defaultCountryName
            addressET.setText("Mohali phase 8")
            phoneNumberET.setText("65643634")
        }
        binding!!.continueBT.setOnClickListener(this)
        binding!!.alreadyHaveTV.setOnClickListener(this)
        binding!!.dateOfBirthET.setOnClickListener(this)
        binding!!.maleRB.setOnClickListener(this)
        binding!!.femaleRB.setOnClickListener(this)
        binding!!.languageSpokenET.setOnClickListener(this)
        binding!!.abcIV.uploadTV.setOnClickListener(this)
        signupViewModel.setSpanbleText(termsOfTV, alreadyHaveTV)



        binding!!.countryET.showFullName(true)
        binding!!.countryET.showNameCode(false)
        binding!!.countryET.setShowPhoneCode(false)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.continueBT -> {
                if (signupViewModel.isInfoValid(profileFile, firstNameET, lastNameET, phoneNumberET, dateOfBirthET, countryET, addressET, arrayListSelected.size)) {
                    signupViewModel.gotoThirdStepOfSignup(data!!, firstNameET, lastNameET, profileFile, phoneNumberET, dateOfBirthET, countryET, addressET, gender, arrayListId.joinToString())
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

            R.id.languageSpokenET -> {
                arrayList.clear()
                pageCount = 0
                isSingle = false
                adapterLanguage = null
                apiHitAllLanguahe()
            }

        }
    }

    fun apiHitAllLanguahe() {
        if (!isSingle) {
            isSingle = true
            val call = api!!.apiGetAllLanguage(pageCount)
            restFullClient!!.sendRequest(call, this)
        }
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

            val fileSizeInBytes = profileFile!!.length()
            val fileSizeInKB = fileSizeInBytes / 1024
            val fileSizeInMB = fileSizeInKB / 1024

            if (fileSizeInMB < 1) {
                baseActivity!!.loadUriImage(baseActivity!!, uri, R.mipmap.ic_defaultuser, abcIV.profileIV)
            } else {
                baseActivity!!.showErrorToast(getString(R.string.imageValidation))
            }

        }

    }


    override fun onSyncSuccess(responseCode: Int, responseMessage: String, responseUrl: String, response: String?) {
        super.onSyncSuccess(responseCode, responseMessage, responseUrl, response)
        try {
            val jsonObject = JSONObject(response!!)
            if (responseUrl == Const.API_GET_ALL_LANGUAGE_LIST) {
                if (responseCode == Const.STATUS_OK) {
                    pageCount++
                    val totalPage = jsonObject.getJSONObject("_meta").getInt("pageCount")
                    isSingle = pageCount >= totalPage
                    val jsonArray = jsonObject.getJSONArray("list")
                    for (i in 0 until jsonArray.length()) {
                        val object1 = jsonArray.getJSONObject(i)
                        val data = Gson().fromJson<AllLanguageData>(object1.toString(), AllLanguageData::class.java)
                        arrayList.add(data)
                    }
                    if (pageCount == 1) {
                        openLanguageDialog()
                    } else {
                        adapterLanguage?.notifyDataSetChanged()
                    }
                }
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private fun openLanguageDialog() {

        val builder = AlertDialog.Builder(baseActivity!!)
        builder.setTitle(getString(R.string.language_list))
        val binding = DataBindingUtil.inflate<MultiLanguageSelectDialogDesignBinding>(LayoutInflater.from(baseActivity), R.layout.multi_language_select_dialog_design, null, false)
        builder.setView(binding.root)
        binding.languageRV.layoutManager = LinearLayoutManager(baseActivity!!)
        adapterLanguage = AllLanguageSpokenAdapter(arrayList, this)
        binding.languageRV.adapter = adapterLanguage
        builder.setPositiveButton(R.string.ok) { dialog, which ->
            dialog.dismiss()
            arrayListSelected.clear()
            arrayListId.clear()
            adapter = null
            for (v in 0 until arrayList.size) {
                if (arrayList[v].isSelected) {
                    arrayListId.add(arrayList[v].id.toString())
                    arrayListSelected.add(arrayList[v].value)
                }
            }
            setAdapterLanguage()
        }
        builder.show()


    }


    private fun setAdapterLanguage() {
        if (adapter == null) {
            adapter = DoctorLanguageSpokenAdapter(arrayListSelected)
            binding!!.languageSpokenRV.adapter = adapter
        } else {
            adapter!!.notifyDataSetChanged()
        }
    }

    fun selectedItem(position: Int) {
        arrayList[position].isSelected = !arrayList[position].isSelected
        adapterLanguage?.notifyDataSetChanged()
    }

}
