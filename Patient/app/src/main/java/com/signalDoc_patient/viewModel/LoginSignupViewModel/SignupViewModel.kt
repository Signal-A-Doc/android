/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_patient.viewModel.LoginSignupViewModel

import android.app.DatePickerDialog
import android.os.Build
import android.text.InputType
import androidx.appcompat.widget.AppCompatEditText
import com.hbb20.CountryCodePicker

import com.signalDoc_patient.R
import com.signalDoc_patient.model.CategoriesData
import com.signalDoc_patient.model.SignupData
import com.signalDoc_patient.ui.extensions.checkString
import com.signalDoc_patient.ui.extensions.getLength
import com.signalDoc_patient.ui.extensions.isBlank
import com.signalDoc_patient.utils.Const
import com.signalDoc_patient.viewModel.commonViewModel.BaseViewModel
import com.toxsl.restfulClient.api.Api2MultipartByte
import com.toxsl.restfulClient.api.Api3Params
import org.json.JSONObject
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SignupViewModel : BaseViewModel() {
    private var currentDate: Calendar? = null
    private var selectedDate: Calendar? = null

    fun isValidate(emailET: AppCompatEditText, passET: AppCompatEditText, cnfrmPassET: AppCompatEditText): Boolean {


        when {
            emailET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_email_address))
            !baseActivity.isValidMail(emailET.checkString()) -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_valid_email_address))
            passET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_pass))
            !baseActivity.isValidPassword(passET.checkString()) -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_valid_password))
            passET.getLength() < Const.PASSWORD_LENGHT -> baseActivity.showErrorToast(baseActivity.getString(R.string.pass_should_be_8_charcters))
            cnfrmPassET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_confirm_password))
            !cnfrmPassET.checkString().equals(passET.checkString()) -> baseActivity.showErrorToast(baseActivity.getString(R.string.confirmpass_does_not_match_with_your_password))
            else -> {
                return true
            }
        }

        return false
    }

    fun isInfoValid(profileFile: File?, firstNameET: AppCompatEditText, lastNameET: AppCompatEditText, phoneNumberET: AppCompatEditText, dateOfBirthET: AppCompatEditText, countryET: CountryCodePicker, addressET: AppCompatEditText): Boolean {
        when {
            profileFile == null -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_upload_profile_image))
            firstNameET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_first_name))
            lastNameET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_last_name))
            phoneNumberET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_contact_number))
            phoneNumberET.getLength() < Const.PHONE_LENGHT -> baseActivity.showErrorToast(baseActivity.getString(R.string.contact_number_5_numbers))
            dateOfBirthET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_date_birth))
            addressET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_address))
            else -> {
                return true
            }
        }
        return false

    }

    fun getCalenderInstance() {
        currentDate = Calendar.getInstance()
        selectedDate = Calendar.getInstance()
        currentDate!!.add(Calendar.MINUTE, 1)

    }

    fun showDatePicker(dateOfBirthET: AppCompatEditText?) {
        val today = Date()
        val c = Calendar.getInstance()
        c.time = today
        val datePickerDialog = DatePickerDialog(baseActivity, R.style.animateDialog, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            selectedDate!!.set(year, monthOfYear, dayOfMonth)
            dateOfBirthET!!.setText(SimpleDateFormat("dd-MM-yyyy", Locale.US).format(selectedDate!!.time))
        }, selectedDate!!.get(Calendar.YEAR), selectedDate!!.get(Calendar.MONTH), selectedDate!!.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
        datePickerDialog.setTitle("")

    }

    fun setFirstLetterInCaps(firstNameET: AppCompatEditText?, lastNameET: AppCompatEditText?, addressET: AppCompatEditText?) {
        firstNameET!!.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
        lastNameET!!.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
        addressET!!.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)

    }

    fun isMedicalInfoValid(bloodGroupET: AppCompatEditText?, weightET: AppCompatEditText?, heightET: AppCompatEditText?, selectedCats: ArrayList<CategoriesData>): Boolean {
        when {
            bloodGroupET!!.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_select_blood_type))
            weightET!!.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_weight))
            heightET!!.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_height))
            selectedCats.isNullOrEmpty() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_select_medical_conditions))
            else -> {
                return true
            }
        }
        return false

    }

    fun hitSignupApi(data: SignupData) {
        val params = Api2MultipartByte()
        params.put("User[first_name]", data.firstName)
        params.put("User[country]", data.country)
        params.put("User[address]", data.address)
        params.put("User[profile_file]", data.profileFile)
        params.put("User[last_name]", data.lastName)
        params.put("User[contact_no]", data.contact_number)
        params.put("User[email]", data.email)
        params.put("User[password]", data.password)
        params.put("User[date_of_birth]", baseActivity.changeDateFormat(data.date_of_birth, "dd-MM-yyyy", "yyyy-MM-dd"))
        params.put("PatientMedicalInformation[blood_group]", data.blood_type)
        params.put("PatientMedicalInformation[weight]", data.weight)
        params.put("PatientMedicalInformation[height]", data.height)
        params.put("PatientSymptom[categories]", data.categoryIds)
        val call = api.api_patient_signup(params.getRequestBody())
        restFullClient.sendRequest(call, this)


    }

    override fun onSyncSuccess(responseCode: Int, responseMessage: String, responseUrl: String, response: String?) {
        super.onSyncSuccess(responseCode, responseMessage, responseUrl, response)
        try {
            callBackInterface.onSuccess(responseCode, responseMessage, responseUrl, JSONObject(response!!))

        } catch (e: Exception) {
            baseActivity.handelException(e)
        }
    }

    fun getCategoriesList() {
        val call = api.api_getCategoriesList()
        restFullClient.sendRequest(call, this)

    }

    fun isLoginValidate(emailET: AppCompatEditText?, passwordET: AppCompatEditText?): Boolean {
        when {
            emailET!!.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_email_address))
            passwordET!!.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_pass))
            else -> {
                return true
            }
        }
        return false
    }

    fun hitLoginApi(email: String, password: String) {
        val params = Api3Params()
        params.put("LoginForm[username]", email)
        params.put("LoginForm[password]", password)
        params.put("LoginForm[device_token]", baseActivity.uniqueDeviceId)
        params.put("LoginForm[device_type]", Const.DEVICE_TYPE)
        params.put("LoginForm[device_name]", Build.MANUFACTURER + "/" + Build.MODEL)
        params.put("LoginForm[role_id]", Const.ROLE_PATIENT)
        val call = api.apiLogin(params.getServerHashMap())
        restFullClient.sendRequest(call, this)


    }

    fun isDoctorProfileVerify(statusET: AppCompatEditText, folioNumberET: AppCompatEditText, summaryET: AppCompatEditText): Boolean {
        when {
            statusET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_select_professioanl_status))
            folioNumberET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_folio_number))
            summaryET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_profile_sumery))
            else -> {
                return true
            }
        }

        return false

    }
}