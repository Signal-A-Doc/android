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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson

import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentAppointmentInfoThreeBinding
import com.signalDoc_patient.databinding.FragmentMedicalConditionListBinding
import com.signalDoc_patient.model.CategoriesData
import com.signalDoc_patient.model.HealthProfileData
import com.signalDoc_patient.model.SymptomsListData
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.adapter.MedicalConditionListAdapter
import com.signalDoc_patient.ui.adapter.SymptomsListAdapter
import com.signalDoc_patient.ui.extensions.handleJSonException
import com.signalDoc_patient.ui.extensions.replaceFragWithArgs
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.utils.Const
import kotlinx.android.synthetic.main.fragment_appointment_info_three.*
import org.json.JSONException
import org.json.JSONObject


class MedicalConditionListFragment : BaseFragment() {
    private var adapter: MedicalConditionListAdapter? = null
    private var datas: ArrayList<CategoriesData> = arrayListOf()
    private var pageCount = 0
    private var singleHit = false
    private var binding: FragmentMedicalConditionListBinding? = null
    private var data: HealthProfileData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable("data")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.medical_conditions), false)

        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_medical_condition_list, container, false)
            initUi()
        }
        return binding!!.root
    }

    private fun initUi() {
        binding!!.contineBT.setOnClickListener(this)
        resetCategories()
        getMedicalConditionsList()
    }

    fun getMedicalConditionsList() {
        if (!singleHit) {
            singleHit = true
            val call = api!!.api_getMedicalConditionsList(pageCount)
            restFullClient!!.sendRequest(call, this)
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.contineBT -> {
                //   baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.under_delvelopment))
                gotoReviewHealthFrag()
            }
        }
    }

    private fun gotoReviewHealthFrag() {
        var medicalConditionIds = ""
        if (datas.size > 0) {
            for (i in 0 until datas.size) {
                if (datas[i].ischeked) {
                    if (i == 0) {
                        medicalConditionIds = "${datas.get(i).id},"
                    } else {
                        medicalConditionIds += "${datas.get(i).id},"

                    }
                }
            }
            if (medicalConditionIds.isNotEmpty()) {
                medicalConditionIds = medicalConditionIds.substring(0, medicalConditionIds.length - 1)
            }

        }
        val bundle = Bundle()
        data!!.medicalConditionIds = medicalConditionIds
        data!!.medicalConditionList = datas
        bundle.putParcelable("data", data)
        baseActivity!!.replaceFragWithArgs(ReviewHealthFragment(), R.id.frame_container, bundle)
    }

    private fun resetCategories() {
        pageCount = 0
        singleHit = false
        datas.clear()

    }

    private fun setAdapter() {
        when {
            adapter == null -> {
                adapter = MedicalConditionListAdapter(baseActivity!!, datas, this)
                binding!!.listRV.adapter = adapter
            }
            else -> {
                adapter!!.notifyDataSetChanged()
            }
        }
    }

    override fun onSyncSuccess(responseCode: Int, responseMessage: String, responseUrl: String, response: String?) {
        super.onSyncSuccess(responseCode, responseMessage, responseUrl, response)
        try {
            val jsonObject = JSONObject(response!!)
            if (responseUrl.equals(Const.API_GET_MEDICAL_CONDITIONS)) {
                if (responseCode == Const.STATUS_OK) {
                    pageCount++
                    val totalPage = jsonObject.getJSONObject("_meta").getInt("pageCount")
                    if (totalPage > pageCount) {
                        singleHit = false
                    }
                    val list = jsonObject.getJSONArray("list")
                    if (list.length() > 0) {
                        for (i in 0 until list.length()) {
                            val data = Gson().fromJson<CategoriesData>(list.getJSONObject(i).toString(), CategoriesData::class.java)
                            datas.add(data)
                        }
                    }

                } else {
                    singleHit = true
                    if (jsonObject.has("error")) {
                        baseActivity!!.showErrorToast(jsonObject.getString("error"))
                    }
                }
                setAdapter()
            }

        } catch (e: JSONException) {
            handleJSonException(e)
        }
    }
}
