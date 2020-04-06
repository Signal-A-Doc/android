/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.viewModel.patientViewModel

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.hbb20.CountryCodePicker
import com.meetingmaven.app.viewmodel.BaseViewModel.BaseViewModel
import com.signalDoc_doctor.R
import com.signalDoc_doctor.model.SignupData
import com.signalDoc_doctor.ui.extensions.checkString
import com.signalDoc_doctor.ui.extensions.getLength
import com.signalDoc_doctor.ui.extensions.isBlank
import com.signalDoc_doctor.ui.extensions.replaceFragWithArgs
import com.signalDoc_doctor.ui.fragment.SignupLoginPhase.SignUpTwoFragment
import com.signalDoc_doctor.ui.fragment.SignupLoginPhase.WorkProfileFragment
import com.signalDoc_doctor.utils.Const
import com.toxsl.restfulClient.api.Api2MultipartByte
import com.toxsl.restfulClient.api.Api3Params
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class SignupViewModel : BaseViewModel() {
    private var currentDate: Calendar? = null
    private var selectedDate: Calendar? = null
    private var selectedStartDate: Calendar? = Calendar.getInstance()
    private var selectedEndDate: Calendar? = Calendar.getInstance()

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

    fun isInfoValid(profileFile: File?, firstNameET: AppCompatEditText, lastNameET: AppCompatEditText, phoneNumberET: AppCompatEditText, dateOfBirthET: AppCompatEditText, countryET: CountryCodePicker, addressET: AppCompatEditText, languageSelected: Int): Boolean {
        when {
            profileFile == null -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_upload_profile_image))
            firstNameET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_first_name))
            lastNameET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_last_name))
            phoneNumberET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_contact_number))
            phoneNumberET.getLength() < Const.PHONE_LENGHT -> baseActivity.showErrorToast(baseActivity.getString(R.string.contact_number_5_numbers))
            dateOfBirthET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_date_birth))
            addressET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_address))
            languageSelected == 0 -> baseActivity.showErrorToast(baseActivity.getString(R.string.please_enter_language_you_speak))
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

    fun showDatePicker(dateOfBirthET: AppCompatTextView?, type: Int) {
        val today = Date()
        val c = Calendar.getInstance()
        c.time = today
        val datePickerDialog = DatePickerDialog(baseActivity, R.style.animateDialog, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            selectedDate!!.set(year, monthOfYear, dayOfMonth)
            if (type == Const.ISSUE_DATE) {
                selectedStartDate!!.set(year, monthOfYear, dayOfMonth)
            } else if (type == Const.EXPIRY_DATE) {
                selectedEndDate!!.set(year, monthOfYear, dayOfMonth)
            }

            dateOfBirthET!!.setText(SimpleDateFormat("dd-MM-yyyy").format(selectedDate!!.time))
        }, selectedDate!!.get(Calendar.YEAR), selectedDate!!.get(Calendar.MONTH), selectedDate!!.get(Calendar.DAY_OF_MONTH))
        if (type == Const.EXPIRY_DATE) {
            datePickerDialog.datePicker.minDate = selectedStartDate!!.timeInMillis + 1000
        }
        datePickerDialog.show()
        datePickerDialog.setTitle("")

    }

    fun setFirstLetterInCaps(firstNameET: AppCompatEditText?, lastNameET: AppCompatEditText?, addressET: AppCompatEditText?) {
        firstNameET!!.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
        lastNameET!!.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
        addressET!!.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)

    }

    fun isMedicalInfoValid(bloodGroupET: AppCompatEditText?, weightET: AppCompatEditText?, heightET: AppCompatEditText?): Boolean {
        when {
            bloodGroupET!!.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_select_blood_type))
            weightET!!.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_weight))
            heightET!!.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_height))
            else -> {
                return true
            }
        }
        return false

    }

    fun hitSignupApi(data: SignupData) {
        val params = Api2MultipartByte()
        params.put("User[first_name]", data.firstName)
        params.put("User[last_name]", data.lastName)
        params.put("User[contact_no]", data.contact_number)
        params.put("User[email]", data.email)
        params.put("User[password]", data.password)
        params.put("User[country]", data.country)
        params.put("User[address]", data.address)
        params.put("User[current_place_of_work]", data.current_place_of_work)
        params.put("User[profile_file]", data.profileFile)
        params.put("User[date_of_birth]", baseActivity.changeDateFormat(data.date_of_birth, "dd-MM-yyyy", "yyyy-MM-dd"))

        params.put("Workprofile[professional_status]", data.professional_status)
        params.put("Workprofile[professional_indemnity]", data.professional_indemnity)
        params.put("Workprofile[MDCN_folio_number]", data.MDCN_folio_number.toInt())
        if (data.MDCN_certificate_File != null) {
            params.put("Workprofile[MDCN_certificate]", data.MDCN_certificate_File)
        }
        if (data.professional_indemnity_certificate != null) {
            params.put("Workprofile[professional_indemnity_certificate]", data.professional_indemnity_certificate)
        }

        params.put("Workprofile[registeration_number]", data.registerationNumber)
        params.put("Workprofile[issued_date]", baseActivity.changeDateFormat(data.issued_date, "dd-MM-yyyy", "yyyy-MM-dd"))
        params.put("Workprofile[expiry_date]", baseActivity.changeDateFormat(data.expiry_date, "dd-MM-yyyy", "yyyy-MM-dd"))
        params.put("Workprofile[profile_summary]", data.profile_summary)
        params.put("Doctorspecialization[categories]", data.categoryIds)
        params.put("DoctorLanguage[language_id]", data.language_selected)

        val call = api.api_doctor_signup(params.getRequestBody())
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
        val call = api.api_getSpeciliationList()
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
        params.put("LoginForm[role_id]", Const.ROLE_DOCTOR)
        val call = api.apiLogin(params.getServerHashMap())
        restFullClient.sendRequest(call, this)
    }

    fun isDoctorProfileVerify(workPlaceET: AppCompatEditText, statusET: AppCompatEditText, folioNumberET: AppCompatEditText, summaryET: AppCompatEditText, professional_indemnity: Int, certificateFile: File?, certificateAnnualFile: File?, regNoET: AppCompatEditText, issueDateTV: AppCompatTextView, expiryDateTV: AppCompatTextView, careET: String): Boolean {
        when {
            statusET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_select_professioanl_status))
            workPlaceET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_current_place_work))
            professional_indemnity == Const.MDCN_CERTIFICATE_YES && certificateFile == null -> baseActivity.showErrorToast(baseActivity.getString(R.string.please_upload_pi_certificate))
            folioNumberET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_folio_number))
            certificateAnnualFile == null -> baseActivity.showErrorToast(baseActivity.getString(R.string.please_upload_annual_practicing_license))
            regNoET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.please_enter_registration_number))
            issueDateTV.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.please_enter_issue_date))
            expiryDateTV.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.please_enter_expire_date))
            selectedStartDate!!.after(selectedEndDate) -> baseActivity.showErrorToast(baseActivity.getString(R.string.please_enter_valid_date))
            careET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.please_enter_area_of_specialization))
            summaryET.isBlank() -> baseActivity.showErrorToast(baseActivity.getString(R.string.pls_enter_profile_sumery))
            else -> {
                return true
            }
        }

        return false

    }


    fun apiHitProfessinal() {
        val call = api.apiDocSkill()
        restFullClient.sendRequest(call, this)
    }

    fun setSpanbleText(termsOfTV: AppCompatTextView?, alreadyHaveTV: AppCompatTextView?) {
        val text = baseActivity.getString(R.string.terms_privacy)
        termsOfTV!!.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE)

        val accountText = baseActivity.getString(R.string.already_have_an_account_sign_in) + " " + baseActivity.getString(R.string.signIn)
        alreadyHaveTV!!.setText(Html.fromHtml(accountText), TextView.BufferType.SPANNABLE)

    }

    fun hanldePasswordLenght(passET: AppCompatEditText?, tooWeakView: View?, passStatusTV: AppCompatTextView?, goodView: View?, strongView: View?) {
        passET!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                if (charSequence.length <= Const.WEAK_LENGHT) {
                    tooWeakView!!.setBackgroundResource(R.drawable.password_strength_bar_filled)
                    passStatusTV!!.text = baseActivity.getString(R.string.too_weak)
                    goodView!!.setBackgroundResource(R.drawable.password_strength_bar)
                    strongView!!.setBackgroundResource(R.drawable.password_strength_bar)
                } else if (charSequence.length > Const.WEAK_LENGHT && charSequence.length < Const.STRONG_LENGHT) {
                    tooWeakView!!.setBackgroundResource(R.drawable.paasword_medium_bar)
                    passStatusTV!!.text = baseActivity.getString(R.string.normal)
                    goodView!!.setBackgroundResource(R.drawable.paasword_medium_bar)
                    strongView!!.setBackgroundResource(R.drawable.password_strength_bar)
                } else if (!baseActivity.isValidPassword(passET.checkString())) {
                    tooWeakView!!.setBackgroundResource(R.drawable.paasword_medium_bar)
                    passStatusTV!!.text = baseActivity.getString(R.string.normal)
                    goodView!!.setBackgroundResource(R.drawable.paasword_medium_bar)
                    strongView!!.setBackgroundResource(R.drawable.password_strength_bar)
                } else {
                    tooWeakView!!.setBackgroundResource(R.drawable.password_strong_bar)
                    passStatusTV!!.text = baseActivity.getString(R.string.strong)
                    goodView!!.setBackgroundResource(R.drawable.password_strong_bar)
                    strongView!!.setBackgroundResource(R.drawable.password_strong_bar)
                }
            }


            override fun afterTextChanged(s: Editable) {

            }
        })

    }

    fun gotoSecondStepOfSignup(emailET: AppCompatEditText?, passET: AppCompatEditText?) {
        val data = SignupData()
        val bundle = Bundle()
        data.email = emailET!!.checkString()
        data.password = passET!!.checkString()
        bundle.putParcelable("data", data)
        baseActivity.replaceFragWithArgs(SignUpTwoFragment(), R.id.login_frame, bundle)

    }

    fun gotoThirdStepOfSignup(data: SignupData, firstNameET: AppCompatEditText?, lastNameET: AppCompatEditText?, profileFile: File?, phoneNumberET: AppCompatEditText?, dateOfBirthET: AppCompatEditText?, countryET: CountryCodePicker?, addressET: AppCompatEditText?, gender: Int, language_selected: String) {
        data.firstName = firstNameET!!.checkString()
        data.lastName = lastNameET!!.checkString()
        data.profileFile = profileFile
        data.contact_number = phoneNumberET!!.checkString()
        data.date_of_birth = dateOfBirthET!!.checkString()
        data.address = addressET!!.checkString()
        data.gender = gender
        if (countryET!!.selectedCountryName != null) {
            data.country = countryET.selectedCountryName
        } else {
            data.country = countryET.defaultCountryCode
        }
        data.language_selected = language_selected
        val bundle = Bundle()
        bundle.putParcelable("data", data)
        baseActivity.replaceFragWithArgs(WorkProfileFragment(), R.id.login_frame, bundle)

    }
}