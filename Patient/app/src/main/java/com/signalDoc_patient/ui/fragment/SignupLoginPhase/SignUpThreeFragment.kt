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


import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.DialogCategoriesListBinding
import com.signalDoc_patient.databinding.FragmentSignUpThreeBinding
import com.signalDoc_patient.model.CategoriesData
import com.signalDoc_patient.model.ProfileData
import com.signalDoc_patient.model.SignupData
import com.signalDoc_patient.ui.activity.LoginSignUpActivity
import com.signalDoc_patient.ui.adapter.CategoriesListAdapter
import com.signalDoc_patient.ui.adapter.MedicalConditionAdapter
import com.signalDoc_patient.ui.extensions.checkString
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.ui.fragment.CardPhase.AddCardFragment
import com.signalDoc_patient.utils.Const
import com.signalDoc_patient.utils.OnRemoveListener
import com.signalDoc_patient.utils.SpacingItemDecoration
import com.signalDoc_patient.viewModel.LoginSignupViewModel.SignupViewModel
import kotlinx.android.synthetic.main.fragment_sign_up.alreadyHaveTV
import kotlinx.android.synthetic.main.fragment_sign_up_three.*
import org.json.JSONException
import org.json.JSONObject


class SignUpThreeFragment : BaseFragment() {

    private var binding: FragmentSignUpThreeBinding? = null
    private var dialogCatListBinding: DialogCategoriesListBinding? = null
    private var medicalConditionAdapter: MedicalConditionAdapter? = null
    private var data: SignupData? = null
    private var catListAdapter: CategoriesListAdapter? = null
    private lateinit var signupViewModel: SignupViewModel
    private var blood_type = Const.O_NEGATIVE
    private var datas: ArrayList<CategoriesData> = arrayListOf()
    private var mPopupWindow: PopupWindow? = null
    private val selectedCats = ArrayList<CategoriesData>()
    private var catIds = ""
    private var count = 0
    private var popupView: View? = null

    private val onRemoveListener = OnRemoveListener { position ->
        selectedCats.removeAt(position)
        medicalConditionAdapter!!.notifyItemRemoved(position)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable("data")
        }
        signupViewModel = ViewModelProviders.of(this)[SignupViewModel::class.java]
        signupViewModel.setData(baseActivity!!, restFullClient!!, api!!, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (baseActivity as LoginSignUpActivity).setToolbar(true)
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_three, container, false)
        }
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        binding!!.continueBT.setOnClickListener(this)
        binding!!.alreadyHaveTV.setOnClickListener(this)
        binding!!.bloodGroupET.setOnClickListener(this)
        binding!!.anyMedicalConditionET.setOnClickListener(this)
        val chipsLayoutManager = ChipsLayoutManager.newBuilder(baseActivity)
                .setChildGravity(Gravity.NO_GRAVITY)
                .setScrollingEnabled(true)
                .setGravityResolver { position -> Gravity.NO_GRAVITY }
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .build()
        val spacingItemDecoration = SpacingItemDecoration(baseActivity!!.resources.getDimensionPixelOffset(R.dimen.item_space),
                baseActivity!!.resources.getDimensionPixelOffset(R.dimen.item_space))
        if (binding!!.medicalConditionRV.itemDecorationCount == 0) {
            binding!!.medicalConditionRV.addItemDecoration(spacingItemDecoration)
        }
        binding!!.medicalConditionRV.layoutManager = chipsLayoutManager
        binding!!.medicalConditionRV.recycledViewPool.setMaxRecycledViews(0, 10)
        val accountText = baseActivity!!.getString(R.string.already_have_an_account_sign_in) + " " + baseActivity!!.getString(R.string.signIn)
        alreadyHaveTV.setText(Html.fromHtml(accountText), TextView.BufferType.SPANNABLE)
    }

    private fun setMedicalConditionAdapter() {
        if (medicalConditionAdapter != null) {
            medicalConditionAdapter!!.notifyDataSetChanged()
        } else {
            medicalConditionAdapter = MedicalConditionAdapter(baseActivity!!, selectedCats, onRemoveListener)
            binding!!.medicalConditionRV.adapter = medicalConditionAdapter
        }
    }

    override fun onClick(v: View) {
        this.popupView = v
        when (v.id) {
            R.id.continueBT -> {
                if (signupViewModel.isMedicalInfoValid(bloodGroupET, weightET, heightET,selectedCats)) {
                    createUser()
                }
            }
            R.id.alreadyHaveTV -> baseActivity!!.replaceFragment(LoginFragment(), R.id.login_frame)
            R.id.bloodGroupET -> showBloodGroupDialog()
            R.id.anyMedicalConditionET -> signupViewModel.getCategoriesList()


        }
    }

    private fun createUser() {
        data!!.blood_type = blood_type
        data!!.weight = weightET.checkString()
        data!!.height = heightET.checkString()
        data!!.categoryIds = catIds
        signupViewModel.hitSignupApi(data!!)

    }

    private fun showBloodGroupDialog() {
        val bloodGroupArray = baseActivity!!.resources.getStringArray(R.array.blood_group)
        val builder = AlertDialog.Builder(baseActivity!!)
        builder.setItems(bloodGroupArray) { dialog, which ->
            blood_type = which
            binding!!.bloodGroupET.setText(bloodGroupArray[which])

        }
        builder.create().show()


    }

    override fun onSuccess(responseCode: Int, responseMessage: String, responseUrl: String, jsonObject: JSONObject) {
        super.onSuccess(responseCode, responseMessage, responseUrl, jsonObject)
        try {
            if (responseUrl.equals(Const.API_PATIENT_SIGNUP)) {
                if (responseCode == Const.STATUS_OK) {
                    if (jsonObject.has("message")) {
                        baseActivity!!.showSuccessToast(jsonObject.getString("message"))
                    }
                    if (jsonObject.has("detail")) {
                        val data = Gson().fromJson(jsonObject.getJSONObject("detail").toString(), ProfileData::class.java)
                        store!!.saveProfileDataInPrefStore(data)
                    }
                    baseActivity!!.replaceFragment(AddCardFragment(), R.id.login_frame)
                }
            } else if (responseUrl.equals(Const.API_GET_CATEGORIES)) {
                datas.clear()
                val list = Gson().fromJson<ArrayList<CategoriesData>>(jsonObject.getJSONArray("list").toString(), object : TypeToken<ArrayList<CategoriesData>>() {}.type)
                datas.addAll(list)
                showCategoryList()
            }

        } catch (e: JSONException) {
            baseActivity!!.printJsonException(e)
        }
    }

    private fun showCategoryList() {
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

}
