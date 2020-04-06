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
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.text.InputType
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.signalDoc_patient.viewModel.commonViewModel.BaseViewModel
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.DialogCategoriesListBinding
import com.signalDoc_patient.databinding.FragmentEditInfoDialogBinding
import com.signalDoc_patient.model.CategoriesData
import com.signalDoc_patient.ui.adapter.CategoriesListAdapter
import com.signalDoc_patient.ui.extensions.handleException
import com.signalDoc_patient.utils.Const
import com.signalDoc_patient.utils.DataAndTypeEditProfile
import com.toxsl.restfulClient.api.Api2MultipartByte
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*

class ProfileViewModel : BaseViewModel() {
    private var profileUpdate = DataAndTypeEditProfile.getInstance()
    private var selectedDate: Calendar? = null
    private var blood_type = 0
    var binding: FragmentEditInfoDialogBinding? = null
    private var mPopupWindow: PopupWindow? = null
    private val selectedCats = ArrayList<CategoriesData>()
    private var dialogCatListBinding: DialogCategoriesListBinding? = null
    private var popupView: View? = null
    private var catListAdapter: CategoriesListAdapter? = null
    private var count = 0
    private var catIds = ""
    private var datas: ArrayList<CategoriesData> = arrayListOf()


    fun showEditDialog(title: String, type: Int, topTitle: String) {
        val dialogBuilder = AlertDialog.Builder(baseActivity)
        binding = DataBindingUtil.inflate(LayoutInflater.from(baseActivity), R.layout.fragment_edit_info_dialog, null, false)
        dialogBuilder.setView(binding!!.root)
        binding!!.editTV.text = topTitle
        binding!!.editHead.text = topTitle
        binding!!.editET.setText(title)


        if (type == Const.DAT_OF_BIRTH) {
            binding!!.editET.isFocusable = false
            binding!!.editET.setOnClickListener {
                showDatePicker(binding!!.editET)
            }
        } else if (type == Const.COUNTRY) {
            binding!!.editETLayout.visibility = View.GONE
            binding!!.editCountryLayout.visibility = View.VISIBLE

            binding!!.editCountryET.isFocusable = false


            binding!!.editCountryET.showFullName(true)
            binding!!.editCountryET.showNameCode(false)
            binding!!.editCountryET.setShowPhoneCode(false)


        } else if (type == Const.COUNTRY) {
            binding!!.editET.isFocusable = false
            binding!!.editET.setOnClickListener {


            }

        } else if (type == Const.BLOOD_TYPE) {
            binding!!.editET.isFocusable = false

            binding!!.editET.setOnClickListener {
                showBloodGroupDialog(binding!!.editET)
            }
        } else if (type == Const.MEDITATIONS) {
            binding!!.editET.isFocusable = false

            binding!!.editET.setOnClickListener {
                getCategoriesList()
            }
        } else {
            binding!!.editET.isFocusable = true
        }

        if (type == Const.PHONE || type == Const.WEIGHT || type == Const.HEIGHT) {
            binding!!.editET.inputType = InputType.TYPE_CLASS_NUMBER
        }

        val width = (baseActivity.resources.displayMetrics.widthPixels * 0.85).toInt()
        val alertDialog = dialogBuilder.create()

        binding!!.okBT.setOnClickListener {
            if (type == Const.DAT_OF_BIRTH) {
                profileUpdate.sendCallBack(SimpleDateFormat("yyyy-MM-dd", Locale.US).format(selectedDate!!.time), type)
            } else if (type == Const.COUNTRY) {
                profileUpdate.sendCallBack(binding!!.editCountryET.selectedCountryName, type)
            } else {
                profileUpdate.sendCallBack(binding!!.editET.text.toString(), type)
            }
            alertDialog.dismiss()
        }
        binding!!.cancelBT.setOnClickListener {
            alertDialog.dismiss()
        }
        binding!!.close.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
        alertDialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    fun getUserProfile() {
        val call = api.apiGetProfile()
        restFullClient.sendRequest(call, this)

    }

    override fun onSyncSuccess(responseCode: Int, responseMessage: String, responseUrl: String, response: String?) {
        super.onSyncSuccess(responseCode, responseMessage, responseUrl, response)
        try {

            if (responseUrl.equals(Const.API_GET_CATEGORIES)) {
                val jsonObject = JSONObject(response!!)
                datas.clear()
                val list = Gson().fromJson<ArrayList<CategoriesData>>(jsonObject.getJSONArray("list").toString(), object : TypeToken<ArrayList<CategoriesData>>() {}.type)
                datas.addAll(list)
                showCategoryList()
            } else {
                callBackInterface.onSuccess(responseCode, responseMessage, responseUrl, JSONObject(response!!))
            }
        } catch (e: JSONException) {
            baseActivity.printJsonException(e)
        }
    }

    fun showDatePicker(dateOfBirthET: AppCompatEditText?) {
        selectedDate = Calendar.getInstance()
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

    fun apiHitUpdateProfile(nameProfileTV: String, lastnamePTV: String, numPTV: String, emailPTV: String, countryPTV: String, addressPTV: String, selectedDOB: String, profileFile: File?) {
        val param = Api2MultipartByte()
        param.put("User[first_name]", nameProfileTV)
        param.put("User[last_name]", lastnamePTV)
        param.put("User[contact_no]", numPTV)
        param.put("User[email]", emailPTV)

        param.put("User[country]", countryPTV)
        param.put("User[address]", addressPTV)
        param.put("User[date_of_birth]", selectedDOB)
        try {
            param.put("User[profile_file]", profileFile)
        } catch (e: FileNotFoundException) {
            handleException(e)
        }
        val call = api.apiUpdateProfile(param.getRequestBody())
        restFullClient.sendRequest(call, this)
    }

    private fun showBloodGroupDialog(edittext: AppCompatEditText) {
        val bloodGroupArray = baseActivity.resources.getStringArray(R.array.blood_group)
        val builder = AlertDialog.Builder(baseActivity)
        builder.setItems(bloodGroupArray) { dialog, which ->
            blood_type = which
            edittext.setText(bloodGroupArray[which])
        }
        builder.create().show()
    }

    fun updateMedication(weight: String, height: String, alergies: String) {
        val param = Api2MultipartByte()
        param.put("PatientMedicalInformation[blood_group]", blood_type)
        param.put(" PatientMedicalInformation[weight]", weight)
        param.put(" PatientMedicalInformation[height]", height)
        param.put("PatientSymptom[categories]", catIds)
        param.put("PatientMedicalInformation[allergies]", alergies)
        val call = api.apiUpdateProfile(param.getRequestBody())
        restFullClient.sendRequest(call, this)
    }

    fun getCategoriesList() {
        val call = api.api_getCategoriesList()
        restFullClient.sendRequest(call, this)

    }


    private fun showCategoryList() {
        popupView = binding!!.editET
        selectedCats.clear()
        val inflater = baseActivity.applicationContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        dialogCatListBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_categories_list, null, false)

        val displayMetrics = DisplayMetrics()
        baseActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        mPopupWindow = PopupWindow(dialogCatListBinding!!.root,
                width - width / 2, 330, true)

        mPopupWindow!!.isOutsideTouchable = true
        mPopupWindow!!.setBackgroundDrawable(BitmapDrawable())
        if (Build.VERSION.SDK_INT > 18) {
            mPopupWindow!!.showAsDropDown(popupView, 0, 0, Gravity.END)
        } else {
            mPopupWindow!!.showAsDropDown(popupView, 0, 0)
        }
        catListAdapter = CategoriesListAdapter(baseActivity, datas)
        dialogCatListBinding!!.listLV.adapter = catListAdapter
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogCatListBinding!!.listLV.isNestedScrollingEnabled = true
        } else
            ViewCompat.setNestedScrollingEnabled(dialogCatListBinding!!.listLV, true)
        dialogCatListBinding!!.listLV.setOnItemClickListener { parent, view, position, id ->
            if (datas[position].ischeked) {
                if (count != 0) {
                    count--
                    for (i in 0 until selectedCats.size) {
                        if (selectedCats[i].id == datas[position].id) {
                            selectedCats.removeAt(i)
                            break
                        }
                    }

                }
            } else {
                selectedCats.add(datas[position])
                count++
            }

            datas[position].ischeked = !datas[position].ischeked
            if (catListAdapter != null) {
                catListAdapter!!.notifyDataSetChanged()
            }

        }
        dialogCatListBinding!!.okBT.setOnClickListener({ view -> setCategories() })

    }


    private fun setCategories() {
        var titles = StringBuilder()
        catIds = ""
        for (i in datas.indices) {
            if (datas.get(i).ischeked) {
                if (i == 0) {
                    titles = StringBuilder(datas.get(i).title + ",")
                    catIds = datas.get(i).id.toString() + ","
                } else {
                    titles.append(datas.get(i).title).append(",")
                    catIds = StringBuilder().append(catIds).append(datas.get(i).id).append(",").toString()
                }
            }
        }

        if (titles.length > 0) {
            titles = StringBuilder(titles.substring(0, titles.length - 1))
            binding!!.editET.setText(titles)
        }
        if (catIds.length > 0) {
            catIds = catIds.substring(0, catIds.length - 1)
            mPopupWindow!!.dismiss()

        } else {
            baseActivity.showErrorToast(baseActivity.getString(R.string.select_atleast_one_category))
        }

    }

}