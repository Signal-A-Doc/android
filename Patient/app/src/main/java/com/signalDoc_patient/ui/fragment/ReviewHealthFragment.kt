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
import androidx.recyclerview.widget.RecyclerView
import com.signalDoc_patient.BuildConfig

import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentReviewHealthBinding
import com.signalDoc_patient.model.HealthProfileData
import com.signalDoc_patient.model.ReviewHealthData
import com.signalDoc_patient.model.SelectSymptomsData
import com.signalDoc_patient.model.SymptomsListData
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.adapter.ReviewHealthAdapter
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.utils.Const
import com.toxsl.restfulClient.api.Api3Params
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class ReviewHealthFragment : BaseFragment() {
    private var binding: FragmentReviewHealthBinding? = null
    private var selectedHashMap = HashMap<Int, ArrayList<SelectSymptomsData>>()
    private val symptomsDetailList = ArrayList<SymptomsListData>()
    private var data: HealthProfileData? = null
    private var adapter: ReviewHealthAdapter? = null
    private var medicalConditionsList: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable("data")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.health_profile), false)
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_review_health, container, false)

        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.saveBT.setOnClickListener(this)

        initUI()
    }

    private fun initUI() {
        if (data!!.symptomsList.size > 0) {
            handleSymptomsData()
        }
        if (data!!.medicationsArrayList.size > 0) {
            binding!!.medicationTV.visibility = View.GONE
            binding!!.medicationsRV.visibility = View.VISIBLE
            setAdpter(binding!!.medicationsRV, data!!.medicationsArrayList, data!!.drugList, medicalConditionsList, Const.APPOITMENTS)
        } else {
            binding!!.medicationTV.visibility = View.VISIBLE
            binding!!.medicationsRV.visibility = View.GONE
        }
        if (data!!.drugList.size > 0) {
            binding!!.drugTV.visibility = View.GONE
            binding!!.drugRV.visibility = View.VISIBLE
            setAdpter(binding!!.drugRV, data!!.medicationsArrayList, data!!.drugList, medicalConditionsList, Const.MEDICAL)
        } else {
            binding!!.drugTV.visibility = View.VISIBLE
            binding!!.drugRV.visibility = View.GONE
        }
        if (data!!.medicalConditionList.size > 0) {
            binding!!.medicalTV.visibility = View.GONE
            binding!!.medicationsRV.visibility = View.VISIBLE
            for (i in 0 until data!!.medicalConditionList.size) {
                if (data!!.medicalConditionList[i].ischeked) {
                    medicalConditionsList.add(data!!.medicalConditionList[i].title)
                }
            }
            setAdpter(binding!!.medicalConditionsRV, data!!.medicationsArrayList, data!!.drugList, medicalConditionsList, Const.ACCOUNT)
        } else {
            binding!!.medicalTV.visibility = View.VISIBLE
            binding!!.medicationsRV.visibility = View.GONE
        }

        binding!!.saveBT.setOnClickListener(this)
    }

    private fun setAdpter(recyclerView: RecyclerView, medicationsArrayList: ArrayList<ReviewHealthData>?, drugList: ArrayList<String>?, medicalList: ArrayList<String>?, type: Int) {
        adapter = ReviewHealthAdapter(baseActivity!!, medicationsArrayList!!, drugList!!, medicalList!!, type)
        recyclerView.adapter = adapter

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.saveBT -> {
                 baseActivity!!.showErrorToast("Under development!!")
                //  hitSaveHealthProfileApi()
               // baseActivity!!.replaceFragment(AvailaibleDoctorFragment(), R.id.frame_container)
            }
        }
    }

    private fun hitSaveHealthProfileApi() {
        var drugTitles = ""
        if (data!!.drugList.size > 0) {
            for (i in 0 until data!!.drugList.size) {
                if (i == 0) {
                    drugTitles = "${data!!.drugList.get(i)},"
                } else {
                    drugTitles += "${data!!.drugList.get(i)},"

                }
            }

            if (drugTitles.isNotEmpty()) {
                drugTitles = drugTitles.substring(0, drugTitles.length - 1)
            }

        }
        val params = Api3Params()
        if (data!!.medicationsArray != null) {
            params.put("PatientMedicineHistory[medicineInfo]", data!!.medicationsArray!!)
        }
        if (drugTitles.isNotEmpty()) {
            params.put("PatientAllergies[allergies]", drugTitles)
        }
        if (getSymptomsJson() != null) {
            params.put("PatientIllness[illness]", getSymptomsJson()!!)
        }

    }

    private fun getSymptomsJson(): JSONArray? {
        val it = selectedHashMap.entries.iterator()

        val tempArr = JSONArray()
        try {
            while (it.hasNext()) {
                val pair = it.next() as Map.Entry<*, *>
                val `object` = JSONObject()
                `object`.put("symptom_id", pair.key)
                val productDetailJsonArray = convertArrayListToJsonArray(pair.value as ArrayList<SelectSymptomsData>)

                `object`.put("key", productDetailJsonArray)
                tempArr.put(`object`)
            }

        } catch (e: JSONException) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
            return null
        }

        return tempArr
    }

    private fun handleSymptomsData() {
        symptomsDetailList.clear()
        for (i in 0 until data!!.symptomsList.size) {
            val symptomData = SymptomsListData()
            symptomData.id = data!!.symptomsList[i].id
            symptomData.title = data!!.symptomsList[i].title
            symptomsDetailList.add(symptomData)
            if (data!!.symptomsList[i].symptoms != null) {
                for (j in 0 until data!!.symptomsList[i].symptoms!!.size) {
                    if (data!!.symptomsList[i].symptoms!![j].is_checked) {
                        val selectSymptomsArrayList = ArrayList<SelectSymptomsData>()
                        val symptomsData = SelectSymptomsData()
                        symptomsData.setSub_symptom_id(data!!.symptomsList[i].symptoms!![j].id.toString())
                        symptomsData.setSub_symptom_title(data!!.symptomsList[i].symptoms!![j].title)
                        selectSymptomsArrayList.add(symptomsData)
                        if (selectedHashMap.containsKey(data!!.symptomsList[i].id)) {
                            selectSymptomsArrayList.addAll(selectedHashMap[data!!.symptomsList[i].id]!!)
                        }
                        selectedHashMap.put(data!!.symptomsList[i].id!!, selectSymptomsArrayList)
                    }
                }
            }
        }
    }

    fun convertArrayListToJsonArray(symptomsList: ArrayList<SelectSymptomsData>): JSONArray? {
        try {
            val tempArr = JSONArray()
            for (i in 0 until symptomsList.size) {
                val detailData = symptomsList[i]
                val `object` = JSONObject()
                `object`.put("id", detailData.getSub_symptom_id())
                tempArr.put(`object`)
            }
            return tempArr
        } catch (e: JSONException) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
            return null
        }

    }

}
