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
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson

import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.FragmentWorkProfileEditBinding
import com.signalDoc_doctor.model.CategoriesData
import com.signalDoc_doctor.model.ProfessionalData
import com.signalDoc_doctor.model.ProfileData
import com.signalDoc_doctor.ui.activity.BaseActivity
import com.signalDoc_doctor.ui.activity.MainActivity
import com.signalDoc_doctor.ui.adapter.MedicalConditionAdapter
import com.signalDoc_doctor.ui.extensions.checkString
import com.signalDoc_doctor.ui.extensions.isBlank
import com.signalDoc_doctor.ui.fragment.BaseFragment
import com.signalDoc_doctor.utils.ClickHandler
import com.signalDoc_doctor.utils.Const
import com.signalDoc_doctor.utils.Const.MDCN_CERTIFICATE_NO
import com.signalDoc_doctor.utils.OnRemoveListener
import com.signalDoc_doctor.viewModel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_basic_information.view.*
import kotlinx.android.synthetic.main.fragment_sign_up_two.*
import kotlinx.android.synthetic.main.fragment_work_profile_edit.*
import org.json.JSONException
import org.json.JSONObject
import java.io.File

class WorkProfileEditFragment : BaseFragment(), ClickHandler, BaseActivity.PermCallback {

    private var binding: FragmentWorkProfileEditBinding? = null
    private lateinit var profileViewModel: ProfileViewModel
    private var profileData: ProfileData? = null
    private var medicalConditionAdapter: MedicalConditionAdapter? = null
    private val selectedCats = ArrayList<CategoriesData>()
    private var sendToServerCats = ArrayList<CategoriesData>()
    private var issueDate = ""
    private var expDate = ""
    private var professionalImunity = MDCN_CERTIFICATE_NO
    private var professionalStatus = 0
    private val arrayListProfessional = ArrayList<ProfessionalData>()
    private var whichDataSelected = 0
    private var certificateFile: File? = null
    private var certificateAnnualFile: File? = null
    private var title: StringBuilder = StringBuilder()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel = ViewModelProviders.of(this)[ProfileViewModel::class.java]
        profileViewModel.setData(baseActivity!!, restFullClient!!, api!!, this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_work_profile_edit, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {

        binding!!.handleClick = this
        profileViewModel.getUserProfile()
    }

    override fun onHandleClick(vararg data: Any) {
        if (data.isNotEmpty()) {
            val view = data[0] as View
            when (view.id) {
                R.id.editTV -> {
                    profileViewModel.showEditDialog(profileData!!.workInformation!!.professionalStatus, Const.PROFESSIONAL_STATUS, baseActivity!!.getString(R.string.professional_status))

                }
                R.id.folioEditTV -> {
                    profileViewModel.showEditDialog(profileData!!.workInformation!!.mDCNFolioNumber.toString(), Const.FOLIO_NUMBER, baseActivity!!.getString(R.string.mdcn_folio_number))


                }
                R.id.regisTV -> {
                    profileViewModel.showEditDialog(profileData!!.workInformation!!.registerationNumber, Const.REGISTRATION_NUMBER, baseActivity!!.getString(R.string.registration_number))


                }
                R.id.issueTV -> {
                    if (profileData!!.workInformation!!.issuedDate != null) {
                        profileViewModel.showEditDialog(profileData!!.workInformation!!.issuedDate!!, Const.ISSUE_DATE_PROFILE, baseActivity!!.getString(R.string.issue_date))
                    } else {
                        profileViewModel.showEditDialog("", Const.ISSUE_DATE_PROFILE, baseActivity!!.getString(R.string.issue_date))

                    }

                }
                R.id.expTV -> {
                    if (profileData!!.workInformation!!.expiryDate != null) {
                        profileViewModel.showEditDialog(profileData!!.workInformation!!.expiryDate!!, Const.EXPIRY_DATE_PROFILE, baseActivity!!.getString(R.string.expiry_date))
                    } else {
                        profileViewModel.showEditDialog("", Const.EXPIRY_DATE_PROFILE, baseActivity!!.getString(R.string.expiry_date))

                    }


                }
                R.id.profileTV -> {
                    profileViewModel.showEditDialog(profileData!!.workInformation!!.profileSummary, Const.PROFILE_SUMMERY, baseActivity!!.getString(R.string.profile_summary))

                }

                R.id.yesRB -> {
                    setProfessionalIndemnity(Const.MDCN_CERTIFICATE_YES, true, false, ContextCompat.getDrawable(baseActivity!!, R.drawable.blue_border_box), ContextCompat.getDrawable(baseActivity!!, R.drawable.grey_border_box))

                }
                R.id.noRB -> {
                    setProfessionalIndemnity(MDCN_CERTIFICATE_NO, false, true, ContextCompat.getDrawable(baseActivity!!, R.drawable.grey_border_box), ContextCompat.getDrawable(baseActivity!!, R.drawable.blue_border_box))
                }
                R.id.loginBT -> {
                    if (isValidate()) {
                        profileViewModel.apiHitUpdateWorkProfile(whichDataSelected.toString(), professionalImunity.toString(), binding!!.mdcnNumET.text.toString().trim(), binding!!.regNumET.text.toString().trim(), dateET.checkString(), dateExpiryET.checkString(), summaryET.checkString(), certificateFile, certificateAnnualFile, sendToServerCats)

                    }
                }

                R.id.uploadTV -> {
                    if (baseActivity!!.checkPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), Const.IMAGE_CODE, this)) {
                        chooseImagePicker(Const.IMAGE_CODE, false, true, true)
                    }
                }
                R.id.uploadAnnualTV -> {
                    if (baseActivity!!.checkPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), Const.IMAGE_CODE_LIC, this)) {
                        chooseImagePicker(Const.IMAGE_CODE_LIC, false, true, true)
                    }
                }
                R.id.addSpecilizationTV -> {
                    profileViewModel.showEditDialog(title.toString(), Const.AREA_SPECILIZATION, baseActivity!!.getString(R.string.area_of_speciization))

                }

            }
        }
    }

    private fun isValidate(): Boolean {
        when {
            doctorET.isBlank() -> baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.pls_select_professioanl_status))
            mdcnNumET.isBlank() -> baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.pls_enter_folio_number))
            regNumET.isBlank() -> baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.please_enter_registration_number))
            summaryET.isBlank() -> baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.pls_enter_profile_sumery))
            else -> {
                return true
            }
        }
        return false
    }

    private fun setProfessionalIndemnity(mdcnCertificateYes: Int, yesBoolean: Boolean, noBoolean: Boolean, yesDrawable: Drawable?, noDrawable: Drawable?) {
        professionalImunity = mdcnCertificateYes
        binding!!.yesRB.isChecked = yesBoolean
        binding!!.noRB.isChecked = noBoolean
        binding!!.yesRB.background = yesDrawable
        binding!!.noRB.background = noDrawable

    }


    override fun onSuccess(responseCode: Int, responseMessage: String, responseUrl: String, jsonObject: JSONObject) {
        super.onSuccess(responseCode, responseMessage, responseUrl, jsonObject)
        try {
            if (responseUrl.equals(Const.API_MY_PROFILE)) {
                if (responseCode == Const.STATUS_OK) {
                    if (jsonObject.has("detail")) {
                        profileData = Gson().fromJson(jsonObject.getJSONObject("detail").toString(), ProfileData::class.java)
                        setData()
                    }
                }
            } else if (responseUrl.equals(Const.API_UPDATE_PROFILE)) {
                if (responseCode == Const.STATUS_OK) {
                    if (jsonObject.has("message")) {
                        baseActivity!!.showSuccessToast(jsonObject.getString("message"))
                    }
                    if (jsonObject.has("detail")) {
                        val data = Gson().fromJson(jsonObject.getJSONObject("detail").toString(), ProfileData::class.java)
                        store!!.saveProfileDataInPrefStore(data)
                        (baseActivity as MainActivity).updateDrawer()
                    }

                }
            } else if (responseUrl.equals(Const.API_PROFESSIONAL_STATUS_LIST)) {
                if (responseCode == Const.STATUS_OK) {
                    arrayListProfessional.clear()
                    val jsonArrayList = jsonObject.getJSONArray("list")
                    for (i in 0 until jsonArrayList.length()) {
                        val jsonObject1 = jsonArrayList.getJSONObject(i)
                        val data = Gson().fromJson(jsonObject1.toString(), ProfessionalData::class.java)
                        arrayListProfessional.add(data)
                    }

                    openProfessionalDialog()

                }
            }
        } catch (e: JSONException) {
            baseActivity!!.printJsonException(e)

        }
    }


    @SuppressLint("SetTextI18n")
    private fun setData() {
        if (profileData != null) {
            if (profileData!!.workInformation!!.professionalIndemnity.toInt() == Const.MDCN_CERTIFICATE_YES) {
                professionalImunity = Const.MDCN_CERTIFICATE_YES
                setProfessionalIndemnity(professionalImunity, true, false, ContextCompat.getDrawable(baseActivity!!, R.drawable.blue_border_box), ContextCompat.getDrawable(baseActivity!!, R.drawable.grey_border_box))

            } else {
                professionalImunity = MDCN_CERTIFICATE_NO
                setProfessionalIndemnity(professionalImunity, true, false, ContextCompat.getDrawable(baseActivity!!, R.drawable.blue_border_box), ContextCompat.getDrawable(baseActivity!!, R.drawable.grey_border_box))

            }
            whichDataSelected = profileData!!.workInformation!!.professional_status
            professionalStatus = profileData!!.workInformation!!.professional_status
            doctorET.setText(profileData!!.workInformation!!.professionalStatus)
            mdcnNumET.setText("" + profileData!!.workInformation!!.mDCNFolioNumber)
            regNumET.setText(profileData!!.workInformation!!.registerationNumber)
            summaryET.setText(profileData!!.workInformation!!.profileSummary)
            dateET.setText(baseActivity!!.changeDateFormat(profileData!!.workInformation!!.issuedDate, "yyyy-MM-dd", "dd-MM-yyyy"))
            dateExpiryET.setText(baseActivity!!.changeDateFormat(profileData!!.workInformation!!.expiryDate, "yyyy-MM-dd", "dd-MM-yyyy"))
            if (!profileData!!.workInformation!!.issuedDate.isNullOrEmpty()) {
                issueDate = profileData!!.workInformation!!.issuedDate!!
            }
            if (!profileData!!.workInformation!!.expiryDate.isNullOrEmpty()) {
                expDate = profileData!!.workInformation!!.expiryDate!!
            }
            if (!profileData!!.workInformation!!.professional_indemnity_certificate.isNullOrEmpty()) {
                baseActivity!!.loadRoundImage(baseActivity!!, profileData!!.workInformation!!.professional_indemnity_certificate!!, R.mipmap.ic_certificate, profCertificateIV)
            } else {
                profCertificateIV.setImageResource(R.mipmap.ic_certificate)
            }
            if (!profileData!!.workInformation!!.MDCN_certificate.isNullOrEmpty()) {
                baseActivity!!.loadRoundImage(baseActivity!!, profileData!!.workInformation!!.MDCN_certificate!!, R.mipmap.ic_certificate, annProfCertificateIV)


            } else {
                profCertificateIV.setImageResource(R.mipmap.ic_certificate)

            }

            if (!profileData!!.symptoms.isNullOrEmpty()) {
                for (i in 0 until profileData!!.symptoms!!.size) {
                    if (i == 0) {
                        title = StringBuilder(profileData!!.symptoms!!.get(i).title + "," + " ")
                    } else {
                        title.append(profileData!!.symptoms!!.get(i).title).append("," + " ")
                    }
                    selectedCats.add(profileData!!.symptoms!!.get(i))
                }
                if (title.length > 0) {
                    title = StringBuilder(title.substring(0, title.length - 2))
                }


            }
            setMedicalConditionAdapter(selectedCats)
        }

    }

    private fun setMedicalConditionAdapter(list: ArrayList<CategoriesData>) {
        this.sendToServerCats = list
        if (medicalConditionAdapter != null) {
            medicalConditionAdapter!!.notifyDataSetChanged()
        } else {
            medicalConditionAdapter = MedicalConditionAdapter(baseActivity!!, list, onRemoveListener)
            binding!!.specificationRV.adapter = medicalConditionAdapter
        }
    }


    private val onRemoveListener = OnRemoveListener { position ->
        selectedCats.removeAt(position)
        medicalConditionAdapter!!.notifyItemRemoved(position)
    }

    fun whatData(data: String?, type: Int) {
        when (type) {
            Const.PROFESSIONAL_STATUS -> {
                doctorET.setText(data)
            }
            Const.ISSUE_DATE_PROFILE -> {
                issueDate = data!!
                if (dateExpiryET.text.toString().trim().isNotEmpty()) {
                    expDate = ""
                    dateExpiryET.text!!.clear()
                }
                dateET.setText(baseActivity!!.changeDateFormat(data, "yyyy-MM-dd", "dd-MM-yyyy"))
            }
            Const.EXPIRY_DATE_PROFILE -> {
                expDate = data!!
                dateExpiryET.setText(baseActivity!!.changeDateFormat(data, "yyyy-MM-dd", "dd-MM-yyyy"))
            }
            Const.FOLIO_NUMBER -> {
                binding!!.mdcnNumET.setText(data)
            }
            Const.REGISTRATION_NUMBER -> {
                binding!!.regNumET.setText(data)
            }
            Const.PROFILE_SUMMERY -> {
                binding!!.summaryET.setText(data)
            }

        }
    }

    private fun openProfessionalDialog() {
        val array = arrayOfNulls<String>(arrayListProfessional.size)
        arrayListProfessional.forEachIndexed { index, data ->
            array[index] = data.title
        }


        val builder = AlertDialog.Builder(baseActivity!!)
        builder.setTitle(baseActivity!!.getString(R.string.pls_choose_professional_status))
        builder.setItems(array, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                binding!!.doctorET.setText(array[which])
                whichDataSelected = arrayListProfessional[which].id
                dialog!!.dismiss()
            }
        })
        builder.show()
    }

    override fun permGranted(resultCode: Int) {
        if (resultCode == Const.IMAGE_CODE) {
            chooseImagePicker(Const.IMAGE_CODE, false, true, true)
        } else if (resultCode == Const.IMAGE_CODE_LIC) {
            chooseImagePicker(Const.IMAGE_CODE_LIC, false, true, true)
        }

    }

    override fun permDenied(resultCode: Int) {
    }

    override fun onImageSelected(uri: Uri?, requestCode: Int) {
        super.onImageSelected(uri, requestCode)
        if (requestCode == Const.IMAGE_CODE) {
            certificateFile = File(uri!!.path!!)
            baseActivity!!.loadUriImage(baseActivity!!, uri, R.mipmap.ic_defaultuser, profCertificateIV)
        } else if (requestCode == Const.IMAGE_CODE_LIC) {
            certificateAnnualFile = File(uri!!.path!!)
            baseActivity!!.loadUriImage(baseActivity!!, uri, R.mipmap.ic_defaultuser, annProfCertificateIV)
        }

    }

    fun whatSpecilizationData(list: ArrayList<CategoriesData>?) {
        medicalConditionAdapter = null
        setMedicalConditionAdapter(list!!)

    }

}
