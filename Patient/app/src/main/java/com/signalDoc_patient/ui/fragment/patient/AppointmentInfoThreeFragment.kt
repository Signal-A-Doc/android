/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_patient.ui.fragment.patient


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentAppointmentInfoThreeBinding
import com.signalDoc_patient.model.HealthProfileData
import com.signalDoc_patient.model.ProfileData
import com.signalDoc_patient.model.SymptomsListData
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.adapter.SymptomsListAdapter
import com.signalDoc_patient.ui.extensions.handleJSonException
import com.signalDoc_patient.ui.extensions.replaceFragWithArgs
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.utils.Const
import kotlinx.android.synthetic.main.fragment_appointment_info_three.*
import mykotlintest.app.ui.fragment.AppointmentInfoFourFragment
import org.json.JSONException
import org.json.JSONObject


class AppointmentInfoThreeFragment : BaseFragment() {
    private var binding: FragmentAppointmentInfoThreeBinding? = null
    private var adapter: SymptomsListAdapter? = null
    private var datas: ArrayList<SymptomsListData> = arrayListOf()
    private var pageCount = 0
    private var singleHit = false
    private var category_id = 0
    private var data: HealthProfileData? = null
    private var isOpenFromTop = false
    private var profileData: ProfileData? = null
    private var timeSlotsId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category_id = it.getInt("category_id")
            data = it.getParcelable("data")
            isOpenFromTop = it.getBoolean(Const.IS_OPEN_FROM_TOP_BUTTON)
            profileData = it.getParcelable(Const.PROFILE_DATA)
            timeSlotsId = it.getString(Const.SELECTED_TIME_SLOTS)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.appointment_info), false)

        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appointment_info_three, container, false)
            initUi()
        }
        return binding!!.root
    }

    private fun initUi() {
        binding!!.nextBT.setOnClickListener(this)
        resetCategories()
        getSymptomsList()
    }

    private fun resetCategories() {
        pageCount = 0
        singleHit = false
        datas.clear()

    }

    fun getSymptomsList() {
        if (!singleHit) {
            singleHit = true
            val call = api!!.api_getSymptomsList(pageCount, category_id)
            restFullClient!!.sendRequest(call, this)
        }
    }

    private fun setAdapter() {
        when {
            adapter == null -> {
                adapter = SymptomsListAdapter(baseActivity!!, datas, this)
                listELV.setAdapter(adapter)
            }
            else -> {
                adapter!!.notifyDataSetChanged()
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.nextBT ->
                gotoNextFrag()

        }
    }

    private fun gotoNextFrag() {
        val bundle = Bundle()
        data!!.symptomsList = datas
        bundle.putParcelable("data", data)
        bundle.putBoolean(Const.IS_OPEN_FROM_TOP_BUTTON, isOpenFromTop)
        bundle.putParcelable(Const.PROFILE_DATA, profileData)
        bundle.putString(Const.SELECTED_TIME_SLOTS,timeSlotsId)
        baseActivity!!.replaceFragWithArgs(AppointmentInfoFourFragment(), R.id.frame_container, bundle)
    }

    override fun onSyncSuccess(responseCode: Int, responseMessage: String, responseUrl: String, response: String?) {
        super.onSyncSuccess(responseCode, responseMessage, responseUrl, response)
        try {
            val jsonObject = JSONObject(response!!)
            if (responseUrl.equals(Const.API_GET_SYMPTOMS_LIST)) {
                if (responseCode == Const.STATUS_OK) {
                    pageCount++
                    val totalPage = jsonObject.getJSONObject("_meta").getInt("pageCount")
                    if (totalPage > pageCount) {
                        singleHit = false
                    }
                    val list = jsonObject.getJSONArray("list")
                    if (list.length() > 0) {
                        for (i in 0 until list.length()) {
                            val data = Gson().fromJson<SymptomsListData>(list.getJSONObject(i).toString(), SymptomsListData::class.java)
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
