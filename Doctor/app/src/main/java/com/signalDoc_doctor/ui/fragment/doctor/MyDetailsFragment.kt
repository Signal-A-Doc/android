/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.ui.fragment.doctor


import android.Manifest
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.FragmentMyDetailsBinding
import com.signalDoc_doctor.model.ProfileData
import com.signalDoc_doctor.ui.activity.BaseActivity
import com.signalDoc_doctor.ui.activity.MainActivity
import com.signalDoc_doctor.ui.extensions.isBlank
import com.signalDoc_doctor.ui.fragment.BaseFragment
import com.signalDoc_doctor.utils.ClickHandler
import com.signalDoc_doctor.utils.Const
import com.signalDoc_doctor.viewModel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_basic_information.view.*
import kotlinx.android.synthetic.main.fragment_my_details.*
import kotlinx.android.synthetic.main.fragment_sign_up_two.*
import org.json.JSONException
import org.json.JSONObject
import java.io.File

class MyDetailsFragment : BaseFragment(), ClickHandler, BaseActivity.PermCallback {
    private var binding: FragmentMyDetailsBinding? = null
    private var profileData: ProfileData? = null
    private lateinit var profileViewModel: ProfileViewModel
    private var profileFile: File? = null
    private var selectedDOB = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel = ViewModelProviders.of(this)[ProfileViewModel::class.java]
        profileViewModel.setData(baseActivity!!, restFullClient!!, api!!, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_details, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        profileViewModel.getUserProfile()
    }

    private fun initUi() {
        binding!!.handleClick = this
    }

    override fun onSuccess(responseCode: Int, responseMessage: String, responseUrl: String, jsonObject: JSONObject) {
        super.onSuccess(responseCode, responseMessage, responseUrl, jsonObject)
        try {
            if (responseUrl == Const.API_MY_PROFILE) {
                if (responseCode == Const.STATUS_OK) {
                    if (jsonObject.has("detail")) {
                        profileData = Gson().fromJson(jsonObject.getJSONObject("detail").toString(), ProfileData::class.java)
                        setData()
                    }
                }
            } else if (responseUrl.equals(Const.API_UPDATE_PROFILE)) {
                if (responseCode == Const.STATUS_OK) {
                    if (jsonObject.has("detail")) {
                        val data = Gson().fromJson(jsonObject.getJSONObject("detail").toString(), ProfileData::class.java)
                        store!!.saveProfileDataInPrefStore(data)
                        (baseActivity as MainActivity).updateDrawer()
                        baseActivity!!.showSuccessToast(getString(R.string.profile_updated_successfully))
                    }

                }
            }
        } catch (e: JSONException) {
            baseActivity!!.printJsonException(e)
        }
    }

    private fun setData() {
        if (profileData != null) {
            if (!profileData!!.profileFile.isNullOrEmpty()) {
                baseActivity!!.loadImage(baseActivity!!, profileData!!.profileFile!!, R.mipmap.ic_defaultuser, profileIV)
            } else {
                profileIV.setImageResource(R.mipmap.ic_defaultuser)
            }

            dobPTV.setText(baseActivity!!.changeDateFormat(profileData!!.dateOfBirth, "yyyy-MM-dd", "dd-MM-yyyy"))
            addressPTV.setText(profileData!!.address)
            countryPTV.setText(profileData!!.country)
            numPTV.setText(profileData!!.contactNo)
            emailPTV.setText(profileData!!.email)
            nameProfileTV.setText(profileData!!.first_name)
            lastnamePTV.setText(profileData!!.last_name)
            if (!profileData!!.dateOfBirth.isNullOrEmpty()) {
                selectedDOB = profileData!!.dateOfBirth!!
            }

        }


    }

    override fun onHandleClick(vararg data: Any) {
        if (data.isNotEmpty()) {
            val view = data[0] as View
            when (view.id) {
                R.id.editFirstName -> {
                    profileViewModel.showEditDialog(profileData!!.first_name, Const.FIRST_NAME, baseActivity!!.getString(R.string.first_name))
                }
                R.id.editLastName -> {
                    profileViewModel.showEditDialog(profileData!!.last_name, Const.LAST_NAME, baseActivity!!.getString(R.string.last_name))
                }
                R.id.editBirth -> {
                    if (profileData!!.dateOfBirth != null) {
                        profileViewModel.showEditDialog(profileData!!.dateOfBirth!!, Const.DAT_OF_BIRTH, baseActivity!!.getString(R.string.date_of_birth))
                    } else {
                        profileViewModel.showEditDialog("", Const.DAT_OF_BIRTH, baseActivity!!.getString(R.string.date_of_birth))

                    }
                }
                R.id.editAddress -> {
                    profileViewModel.showEditDialog(profileData!!.address!!, Const.ADDRESS, baseActivity!!.getString(R.string.address))
                }
                R.id.editCountry -> {
                    profileViewModel.showEditDialog(profileData!!.country!!, Const.COUNTRY, baseActivity!!.getString(R.string.country))
                }
                R.id.editPhone -> {
                    profileViewModel.showEditDialog(profileData!!.contactNo!!, Const.PHONE, baseActivity!!.getString(R.string.phone_number))
                }
                R.id.changeTV -> {
                    if (baseActivity!!.checkPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), Const.IMAGE_CODE, this)) {
                        chooseImagePicker(Const.IMAGE_CODE, false, true, true)
                    }
                }
                R.id.reSheduleBT -> {
                    if (isValid()) {
                        profileViewModel.apiHitUpdateProfile(binding!!.nameProfileTV.text.toString(),
                                binding!!.lastnamePTV.text.toString(), binding!!.numPTV.text.toString(),
                                binding!!.emailPTV.text.toString(), binding!!.countryPTV.text.toString(),
                                binding!!.addressPTV.text.toString(),
                                selectedDOB,
                                profileFile)
                    }
                }
            }
        }
    }

    private fun isValid(): Boolean {
        when {
            binding!!.nameProfileTV.isBlank() -> baseActivity!!.showErrorToast(getString(R.string.please_enter_name))
            binding!!.lastnamePTV.isBlank() -> baseActivity!!.showErrorToast(getString(R.string.please_enter_last_name))
            binding!!.dobPTV.isBlank() -> baseActivity!!.showErrorToast(getString(R.string.please_enter_dob))
            binding!!.addressPTV.isBlank() -> baseActivity!!.showErrorToast(getString(R.string.please_enter_address))
            binding!!.countryPTV.isBlank() -> baseActivity!!.showErrorToast(getString(R.string.please_enter_country))
            binding!!.numPTV.isBlank() -> baseActivity!!.showErrorToast(getString(R.string.please_enter_mobile_number))






            else -> return true

        }
        return false
    }

    override fun permGranted(resultCode: Int) {
        if (resultCode == Const.IMAGE_CODE) {
            chooseImagePicker(Const.IMAGE_CODE, false, true, true)
        }

    }

    override fun permDenied(resultCode: Int) {
    }

    fun whatData(data: String, type: Int) {
        when (type) {
            Const.FIRST_NAME -> {
                binding!!.nameProfileTV.setText(data)
            }
            Const.LAST_NAME -> {
                binding!!.lastnamePTV.setText(data)
            }
            Const.DAT_OF_BIRTH -> {
                selectedDOB = data
                dobPTV.setText(baseActivity!!.changeDateFormat(data, "yyyy-MM-dd", "dd-MM-yyyy"))
            }
            Const.ADDRESS -> {
                binding!!.addressPTV.setText(data)
            }
            Const.COUNTRY -> {
                binding!!.countryPTV.setText(data)
            }
            Const.PHONE -> {
                binding!!.numPTV.setText(data)
            }
        }

    }

    override fun onImageSelected(uri: Uri?, requestCode: Int) {
        super.onImageSelected(uri, requestCode)
        if (requestCode == Const.IMAGE_CODE) {



            val size=File(uri!!.path!!)

            val fileSizeInBytes = size!!.length()
            val fileSizeInKB = fileSizeInBytes / 1024
            val fileSizeInMB = fileSizeInKB / 1024

            if (fileSizeInMB < 1) {

                profileFile = File(uri!!.path!!)
                baseActivity!!.loadUriImage(baseActivity!!, uri, R.mipmap.ic_defaultuser, profileIV)
            }
            else{
                baseActivity!!.showErrorToast(getString(R.string.imageValidation))
            }



        }

    }

}
