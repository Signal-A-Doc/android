/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_patient.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.signalDoc_patient.BuildConfig

import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentMedicalProfessionBinding
import com.signalDoc_patient.model.CategoriesData
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.adapter.MedicalCategories
import com.signalDoc_patient.utils.Const
import com.signalDoc_patient.viewModel.commonViewModel.HomeScreenViewModel.HomeViewModel
import org.json.JSONException
import org.json.JSONObject


class MedicalProfessionFragment : BaseFragment() {
    private var binding: FragmentMedicalProfessionBinding? = null
    private var adapterCategories: MedicalCategories? = null
    private var pageCount = 0
    private var singleHit = false
    private var datas: ArrayList<CategoriesData> = arrayListOf()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.medicalProfessional), true)
        (baseActivity as MainActivity).selectTab(Const.MEDICAL)
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_medical_profession, container, false)
        }
        return binding!!.root;

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        reset()
        getMedicalProfessioanls()


    }

    private fun setAdapter() {
        adapterCategories = MedicalCategories(baseActivity, datas)
        binding!!.categoriesRV.adapter = adapterCategories

    }

    private fun getMedicalProfessioanls() {
        if (!singleHit) {
            singleHit = true
            val call = api!!.api_getMedicalCategoriesList(pageCount)
            restFullClient!!.sendRequest(call, this)

        }
    }

    private fun reset() {
        pageCount = 0
        singleHit = false
        datas.clear()

    }

    override fun onSyncSuccess(responseCode: Int, responseMessage: String, responseUrl: String, response: String?) {
        super.onSyncSuccess(responseCode, responseMessage, responseUrl, response)
        try {
            if (responseUrl.equals(Const.API_PROFESSIONAL_STATUS_LIST)) {
                val jsonObject = JSONObject(response!!)
                if (jsonObject.has("list")) {
                    pageCount++
                    val totalPage = jsonObject.getJSONObject("_meta").getInt("pageCount")
                    if (totalPage > pageCount) {
                        singleHit = false
                    }
                    val list = Gson().fromJson<ArrayList<CategoriesData>>(jsonObject.getJSONArray("list").toString(), object : TypeToken<ArrayList<CategoriesData>>() {}.type)
                    datas.addAll(list)
                }



                setAdapter()
            }


        } catch (e: JSONException) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        }
    }

}
