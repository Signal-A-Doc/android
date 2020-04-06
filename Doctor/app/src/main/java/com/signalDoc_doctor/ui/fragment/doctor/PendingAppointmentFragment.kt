/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.ui.fragment.doctor


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson

import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.FragmentPendingAppointmentBinding
import com.signalDoc_doctor.ui.activity.MainActivity
import com.signalDoc_doctor.ui.adapter.PendingAdapter
import com.signalDoc_doctor.ui.extensions.handleException
import com.signalDoc_doctor.ui.fragment.BaseFragment
import com.signalDoc_doctor.utils.Const
import com.signalDoc_patient.model.AppoitnmemtsListData
import org.json.JSONObject
import java.lang.Exception

class PendingAppointmentFragment : BaseFragment() {

    private var binding: FragmentPendingAppointmentBinding? = null
    private var adapterPending: PendingAdapter? = null
    private var pageCount = 0
    private var singleHit = false
    private var datas: ArrayList<AppoitnmemtsListData> = arrayListOf()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.appointments))
        (baseActivity as MainActivity).selectTab(Const.APPOITMENTS)
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pending_appointment, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        reset()
        getPendingAppoitments()

    }

    fun getPendingAppoitments() {
        if (!singleHit) {
            singleHit = true
            val call = api!!.apiGetPendingAppointmentList(pageCount)
            restFullClient!!.sendRequest(call, this)
        }
    }

    private fun reset() {
        pageCount = 0
        singleHit = false
        datas.clear()
    }

    private fun setUpcomingAppointmentAdapter() {
        adapterPending = PendingAdapter(baseActivity!!,datas,this)
        binding!!.pendingRV.adapter = adapterPending

    }

    override fun onSyncSuccess(responseCode: Int, responseMessage: String, responseUrl: String, response: String?) {
        super.onSyncSuccess(responseCode, responseMessage, responseUrl, response)
        try {
            val jsonObject = JSONObject(response!!)
            when (responseUrl) {
                Const.API_PENDING_DOCTOR_APPOINTMENTS_LIST -> {
                    if (responseCode == Const.STATUS_OK) {
                        pageCount++
                        val totalCount = jsonObject.getJSONObject("_meta").getInt("pageCount")
                        singleHit = pageCount >= totalCount

                        val jsonArray = jsonObject.getJSONArray("list")
                        for (i in 0 until jsonArray.length()) {
                            val object1 = jsonArray.getJSONObject(i)
                            val data = Gson().fromJson<AppoitnmemtsListData>(object1.toString(), AppoitnmemtsListData::class.java)
                            datas.add(data)
                        }
                        setUpcomingAppointmentAdapter()
                    }
                }
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }

}
