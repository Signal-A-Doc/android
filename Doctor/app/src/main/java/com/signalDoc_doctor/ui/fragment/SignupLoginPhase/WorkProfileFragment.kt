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
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.DialogCategoriesListBinding
import com.signalDoc_doctor.databinding.FragmentWorkProfileBinding
import com.signalDoc_doctor.model.CategoriesData
import com.signalDoc_doctor.model.ProfessionalData
import com.signalDoc_doctor.model.ProfileData
import com.signalDoc_doctor.model.SignupData
import com.signalDoc_doctor.ui.activity.BaseActivity
import com.signalDoc_doctor.ui.activity.LoginSignUpActivity
import com.signalDoc_doctor.ui.adapter.CategoriesListAdapter
import com.signalDoc_doctor.ui.adapter.MedicalConditionAdapter
import com.signalDoc_doctor.ui.extensions.checkString
import com.signalDoc_doctor.ui.extensions.replaceFragment
import com.signalDoc_doctor.ui.fragment.BaseFragment
import com.signalDoc_doctor.ui.fragment.doctor.BankDetailFragment
import com.signalDoc_doctor.utils.ClickHandler
import com.signalDoc_doctor.utils.Const
import com.signalDoc_doctor.utils.OnRemoveListener
import com.signalDoc_doctor.viewModel.patientViewModel.SignupViewModel
import kotlinx.android.synthetic.main.fragment_work_profile.*
import org.json.JSONObject
import java.io.File


class WorkProfileFragment : BaseFragment(), ClickHandler, BaseActivity.PermCallback {
    private var binding: FragmentWorkProfileBinding? = null
    private lateinit var viewModel: SignupViewModel
    private val arrayListProfessional = ArrayList<ProfessionalData>()
    private var whichDataSelected = 0
    private var certificateFile: File? = null
    private var certificateAnnualFile: File? = null
    private var data: SignupData? = null
    private var professional_indemnity = Const.MDCN_CERTIFICATE_YES
    private var datas: ArrayList<CategoriesData> = arrayListOf()
    private val selectedCats = ArrayList<CategoriesData>()
    private var dialogCatListBinding: DialogCategoriesListBinding? = null
    private var mPopupWindow: PopupWindow? = null
    private var popupView: View? = null
    private var catListAdapter: CategoriesListAdapter? = null
    private var count = 0
    private var catIds = ""
    private var medicalConditionAdapter: MedicalConditionAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable("data")
        }
        viewModel = ViewModelProviders.of(this)[SignupViewModel::class.java]

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as LoginSignUpActivity).setToolbar(true)
        viewModel.setData(baseActivity!!, restFullClient!!, api!!, this)
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_work_profile, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        viewModel.setSpanbleText(binding!!.termsOfTV, binding!!.alreadyHaveTV)
        regNoET!!.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
        summaryET!!.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
        binding!!.handleClick = this
    }


    override fun onHandleClick(vararg data: Any) {
        if (data.isNotEmpty()) {
            val view = data[0] as View
            when (view.id) {
                R.id.loginBT, R.id.buttonCV -> {
                    doDoctorsSignnup()

                }
                R.id.statusET -> {
                    viewModel.apiHitProfessinal()
                }

                R.id.yesRB -> {
                    uploadTV.visibility = View.VISIBLE
                    certificateLL.visibility = View.VISIBLE
                    setProfessionalIndemnity(Const.MDCN_CERTIFICATE_YES, true, false, ContextCompat.getDrawable(baseActivity!!, R.drawable.blue_border_box), ContextCompat.getDrawable(baseActivity!!, R.drawable.grey_border_box))

                }
                R.id.noRB -> {
                    uploadTV.visibility = View.GONE
                    certificateLL.visibility = View.GONE
                    setProfessionalIndemnity(Const.MDCN_CERTIFICATE_NO, false, true, ContextCompat.getDrawable(baseActivity!!, R.drawable.grey_border_box), ContextCompat.getDrawable(baseActivity!!, R.drawable.blue_border_box))
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

                R.id.removePiTV -> {
                    certificateFile = null
                    binding!!.certificateLL.visibility = View.GONE
                }
                R.id.removeAnnualTV -> {
                    certificateAnnualFile = null
                    binding!!.certificateAnnualLL.visibility = View.GONE
                }

                R.id.issueDateTV -> {
                    if (!expiryDateTV.text.toString().trim().isEmpty()) {
                        expiryDateTV.setText("")
                    }
                    viewModel.getCalenderInstance()
                    viewModel.showDatePicker(issueDateTV, Const.ISSUE_DATE)
                }
                R.id.expiryDateTV -> {
                    if (issueDateTV.text.toString().trim().isEmpty()) {
                        baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.pls_select_issu_date_first))
                    } else {
                        viewModel.showDatePicker(expiryDateTV, Const.EXPIRY_DATE)
                        viewModel.getCalenderInstance()
                    }
                }

                R.id.careET -> viewModel.getCategoriesList()
            }
        }
    }

    private fun setProfessionalIndemnity(mdcnCertificateYes: Int, yesBoolean: Boolean, noBoolean: Boolean, yesDrawable: Drawable?, noDrawable: Drawable?) {
        professional_indemnity = mdcnCertificateYes
        binding!!.yesRB.isChecked = yesBoolean
        binding!!.noRB.isChecked = noBoolean
        binding!!.yesRB.background = yesDrawable
        binding!!.noRB.background = noDrawable

    }


    private fun doDoctorsSignnup() {
        if (viewModel.isDoctorProfileVerify(workPlaceET, statusET, folioNumberET, summaryET, professional_indemnity, certificateFile, certificateAnnualFile, regNoET, issueDateTV, expiryDateTV, catIds)) {
            this.data!!.professional_status = whichDataSelected.toString()
            this.data!!.professional_indemnity = professional_indemnity.toString()
            try {
                this.data!!.MDCN_folio_number = folioNumberET.text.toString().trim().toLong()
            } catch (e: NumberFormatException) {
                baseActivity!!.showErrorToast(getString(R.string.please_enter_valid_number))
            }
            this.data!!.categoryIds = catIds
            this.data!!.registerationNumber = regNoET.checkString()
            this.data!!.current_place_of_work = workPlaceET.checkString()
            this.data!!.issued_date = issueDateTV.text.toString().trim()
            this.data!!.expiry_date = expiryDateTV.text.toString().trim()
            this.data!!.profile_summary = summaryET.checkString()
            if (certificateFile != null) {
                this.data!!.professional_indemnity_certificate = certificateFile
            }
            if (certificateAnnualFile != null) {
                this.data!!.MDCN_certificate_File = certificateAnnualFile
            }

            viewModel.hitSignupApi(this.data!!)

        }
    }

    override fun onSuccess(responseCode: Int, responseMessage: String, responseUrl: String, jsonObject: JSONObject) {
        super.onSuccess(responseCode, responseMessage, responseUrl, jsonObject)
        if (responseUrl.equals(Const.API_PROFESSIONAL_STATUS_LIST)) {
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
        } else if (responseUrl.equals(Const.API_DOCTOR_SIGNUP)) {
            if (responseCode == Const.STATUS_OK) {
                if (jsonObject.has("message")) {
                    baseActivity!!.showSuccessToast(jsonObject.getString("message"))
                }
                if (jsonObject.has("detail")) {
                    val data = Gson().fromJson(jsonObject.getJSONObject("detail").toString(), ProfileData::class.java)
                    store!!.saveProfileDataInPrefStore(data)
                }
                baseActivity!!.replaceFragment(BankDetailFragment(), R.id.login_frame)

            }
        } else if (responseUrl.equals(Const.API_AREA_OF_SPECILIZATION)) {
            datas.clear()
            val list = Gson().fromJson<ArrayList<CategoriesData>>(jsonObject.getJSONArray("list").toString(), object : TypeToken<ArrayList<CategoriesData>>() {}.type)
            datas.addAll(list)
            showCategoryList()
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
                binding!!.statusET.setText(array[which])
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
            binding!!.certificateLL.visibility = View.VISIBLE
            certificateFile = File(uri!!.path!!)
            Glide.with(baseActivity!!).load(uri).apply(RequestOptions().placeholder(R.mipmap.ic_documents).error(R.mipmap.ic_documents)).into(binding!!.certificatePiIV)
        } else if (requestCode == Const.IMAGE_CODE_LIC) {
            binding!!.certificateAnnualLL.visibility = View.VISIBLE
            certificateAnnualFile = File(uri!!.path!!)
            Glide.with(baseActivity!!).load(uri).apply(RequestOptions().placeholder(R.mipmap.ic_documents).error(R.mipmap.ic_documents)).into(binding!!.certificateAnnualIV)
        }

    }


    private fun showCategoryList() {
        popupView = binding!!.careET
        selectedCats.clear()
        val inflater = baseActivity!!.applicationContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        dialogCatListBinding = DataBindingUtil.inflate(inflater,
                R.layout.dialog_categories_list, null, false)

        val displayMetrics = DisplayMetrics()
        baseActivity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
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
        }
        if (catIds.length > 0) {
            catIds = catIds.substring(0, catIds.length - 1)
            mPopupWindow!!.dismiss()

        } else {
            baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.select_atleast_one_category))
        }

        setMedicalConditionAdapter()
    }

    private fun setMedicalConditionAdapter() {
        if (medicalConditionAdapter != null) {
            medicalConditionAdapter!!.notifyDataSetChanged()
        } else {
            medicalConditionAdapter = MedicalConditionAdapter(baseActivity!!, selectedCats, onRemoveListener)
            binding!!.specificationRV.adapter = medicalConditionAdapter
        }
    }

    private val onRemoveListener = OnRemoveListener { position ->
        selectedCats.removeAt(position)
        medicalConditionAdapter!!.notifyItemRemoved(position)
    }

}
