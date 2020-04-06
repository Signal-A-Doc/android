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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentWhichMedicationHealthBinding
import com.signalDoc_patient.model.HealthProfileData
import com.signalDoc_patient.model.QuestionListData
import com.signalDoc_patient.model.ReviewHealthData
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.adapter.MedicationQuestionListAdapter
import com.signalDoc_patient.ui.extensions.handleException
import com.signalDoc_patient.ui.extensions.replaceFragWithArgs
import com.signalDoc_patient.ui.fragment.patient.AnyDrugHealthFragment
import com.signalDoc_patient.utils.Const
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class WhichMedicationHealthFragment : BaseFragment() {

    private var binding: FragmentWhichMedicationHealthBinding? = null
    private var arrayList = ArrayList<QuestionListData>()
    private var adapter: MedicationQuestionListAdapter? = null
    private var isSingle = false
    private var pageCount = 0
    private var data: HealthProfileData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable("data")
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(true, baseActivity!!.getString(R.string.health_profile), false)

        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_which_medication_health, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        apiGetQuestion()
    }

    private fun initUI() {
        binding!!.medicationRV.layoutManager = LinearLayoutManager(baseActivity!!)
        binding!!.saveBT.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.saveBT -> {
                //   baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.under_delvelopment))

                val bundle = Bundle()
                if (arrayList.size > 0) {
                    getQuestionsJsonArray()
                    data!!.medicationsArray = getQuestionsJsonArray()
                    for (i in 0 until data!!.medicationsArray!!.length()) {
                        val healthdata = Gson().fromJson<ReviewHealthData>(data!!.medicationsArray!![i].toString(), ReviewHealthData::class.java)
                        data!!.medicationsArrayList.add(healthdata)

                    }
                }


                bundle.putParcelable("data", data)
                baseActivity!!.replaceFragWithArgs(AnyDrugHealthFragment(), R.id.frame_container, bundle)

            }
        }
    }

    private fun getQuestionsJsonArray(): JSONArray {
        val jsonArray = JSONArray()

        for (i in 0 until arrayList.size) {
            if (!arrayList[i].ansTitle.isNullOrEmpty()) {
                val jsonObject = JSONObject()
                try {
                    jsonObject.put("medicine_name", arrayList[i].title)
                    jsonObject.put("how_long", arrayList[i].ansTitle)
                    jsonArray.put(jsonObject)
                } catch (e: JSONException) {
                    handleException(e)
                }


            }

        }

        return jsonArray

    }

    fun apiGetQuestion() {
        if (!isSingle) {
            isSingle = true
            val call = api!!.apiGetQuestion(2, pageCount)
            restFullClient!!.sendRequest(call, this)
        }
    }

    override fun onSyncSuccess(responseCode: Int, responseMessage: String, responseUrl: String, response: String?) {
        super.onSyncSuccess(responseCode, responseMessage, responseUrl, response)

        try {
            val jsonObject = JSONObject(response!!)
            when {
                responseUrl == Const.API_GET_QUESTIONS_LIST -> {
                    if (responseCode == Const.STATUS_OK) {

                        pageCount++
                        val _metaObject = jsonObject.getJSONObject("_meta")
                        val totalCount = _metaObject.getInt("pageCount")
                        isSingle = pageCount >= totalCount
                        val jsonArray = jsonObject.getJSONArray("list")
                        for (i in 0 until jsonArray.length()) {
                            val objectS = jsonArray.getJSONObject(i)
                            val data = Gson().fromJson<QuestionListData>(objectS.toString(), QuestionListData::class.java)
                            arrayList.add(data)
                        }
                        setAdapter()
                    }
                }
            }

        } catch (e: Exception) {
            handleException(e)
        }
    }

    private fun setAdapter() {
        if (adapter == null) {
            adapter = MedicationQuestionListAdapter(arrayList, this)
            binding!!.medicationRV.adapter = adapter
        } else {
            adapter!!.notifyDataSetChanged()
        }
    }

}
