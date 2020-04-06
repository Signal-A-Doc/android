/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.ui.fragment.doctor


import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.DialogAcceptAppointmentBinding
import com.signalDoc_doctor.databinding.FragmentEditInfoDialogBinding
import com.signalDoc_doctor.databinding.FragmentHomeDoctorBinding
import com.signalDoc_doctor.model.ConsulatitionData
import com.signalDoc_doctor.model.UpcomingAppointmentData
import com.signalDoc_doctor.ui.activity.MainActivity
import com.signalDoc_doctor.ui.adapter.BaseAdapter
import com.signalDoc_doctor.ui.adapter.DoctorConsulateAdapter
import com.signalDoc_doctor.ui.adapter.UpcomingAdapter
import com.signalDoc_doctor.ui.extensions.handleException
import com.signalDoc_doctor.ui.fragment.BaseFragment
import com.signalDoc_doctor.utils.Const
import com.toxsl.restfulClient.api.Api3Params
import kotlinx.android.synthetic.main.fragment_home_doctor.*
import org.json.JSONObject
import java.lang.Exception


class HomeDoctorFragment : BaseFragment(), BaseAdapter.OnItemClickListener {

    private var binding: FragmentHomeDoctorBinding? = null
    private var adapterUpcoming: UpcomingAdapter? = null
    private var arrayList = ArrayList<ConsulatitionData>()
    private var upcomingArrayList = ArrayList<UpcomingAppointmentData>()
    private var adapter: DoctorConsulateAdapter? = null
    private var isSingleHit = false
    private var pageCount = 0
    private var isSingleHitPending = false
    private var pageCountPending = 0
    private var oldPos = -1
    private var adapterPos = -1
    private var alertDialog: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).selectTab(Const.HOME)
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_doctor, container, false)
        }

        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.modeRV.layoutManager = LinearLayoutManager(baseActivity!!, RecyclerView.HORIZONTAL, false)
        initUi()
        setHasOptionsMenu(true)
        getAllModes()
    }

    @SuppressLint("SetTextI18n")
    private fun initUi() {
        if (store!!.getProfileDataFromPrefStore() != null) {
            if (!store!!.getProfileDataFromPrefStore()!!.profileFile.isNullOrEmpty()) {

                baseActivity!!.loadRoundImage(baseActivity!!, store!!.getProfileDataFromPrefStore()!!.profileFile!!, R.mipmap.ic_default_profile, profilePicIV)
            } else {
                profilePicIV.setImageResource(R.mipmap.ic_default_profile)
            }
            drNameTV.setText(baseActivity!!.getString(R.string.hi_dr) + " " + store!!.getProfileDataFromPrefStore()!!.fullName)
        }

        setUpcomingAppointmentAdapter()
        resetPendingAppoitments()
        binding!!.waitingRoomTV.setOnClickListener(this)
        binding!!.pendingTV.setOnClickListener(this)
        binding!!.upComingTV.setOnClickListener(this)

    }

    private fun resetPendingAppoitments() {
        pageCountPending = 0
        isSingleHitPending = false
        upcomingArrayList.clear()

    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.waitingRoomTV -> {

            }
            R.id.pendingTV -> {
                apiHitpending()
            }
            R.id.upComingTV -> {
                val call = api!!.apiGetUpcomingAppointmentList(0)
                restFullClient!!.sendRequest(call, this)
            }
            R.id.yesBT -> {
                hitChangeAppoitnmentStateApi()
            }
            R.id.noBT, R.id.close -> {
                alertDialog!!.dismiss()
            }

        }
    }

    private fun hitChangeAppoitnmentStateApi() {
        if (upcomingArrayList.size > 0) {
            val call = api!!.apiChangeState(upcomingArrayList[adapterPos].id, Const.STATE_ACCEPT)
            restFullClient!!.sendRequest(call, this)
        }

    }

    fun apiHitpending() {
        if (!isSingleHitPending) {
            isSingleHitPending = true
            val call = api!!.apiGetPendingAppointmentList(pageCountPending)
            restFullClient!!.sendRequest(call, this)
        }
    }

    private fun setUpcomingAppointmentAdapter() {
        adapterUpcoming = UpcomingAdapter(upcomingArrayList, this)
        binding!!.upcomingAppointmentRV.adapter = adapterUpcoming
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.notification_menu, menu)
    }

    fun getAllModes() {
        if (!isSingleHit) {
            isSingleHit = true
            val call = api!!.getAllModes(pageCount)
            restFullClient!!.sendRequest(call, this)
        }
    }


    override fun onSyncSuccess(responseCode: Int, responseMessage: String, responseUrl: String, response: String?) {
        super.onSyncSuccess(responseCode, responseMessage, responseUrl, response)
        try {
            val jsonObject = JSONObject(response!!)
            when (responseUrl) {
                Const.API_GET_COMMUNICATION_MODE_LIST -> {
                    if (responseCode == Const.STATUS_OK) {
                        pageCount++
                        val totalCount = jsonObject.getJSONObject("_meta").getInt("pageCount")
                        isSingleHit = pageCount >= totalCount

                        val jsonArray = jsonObject.getJSONArray("list")
                        for (i in 0 until jsonArray.length()) {
                            val object1 = jsonArray.getJSONObject(i)
                            val data = Gson().fromJson<ConsulatitionData>(object1.toString(), ConsulatitionData::class.java)
                            arrayList.add(data)
                        }
                        setAdapter()
                    }
                }
                Const.API_SAVING_DOCTOR_LIST -> {
                    if (responseCode == Const.STATUS_OK) {
                        baseActivity!!.showSuccessToast(jsonObject.getString("message"))
                    }
                }
                Const.API_CHANGE_APPOITMENT_STATE -> {
                    if (responseCode == Const.STATUS_OK) {
                        if (jsonObject.has("message")) {
                            baseActivity!!.showSuccessToast(jsonObject.getString("message"))
                        }
                        setUpcomingAppointmentAdapter()

                        alertDialog!!.dismiss()

                    }
                }

                Const.API_PENDING_DOCTOR_APPOINTMENTS_LIST -> {
                    if (responseCode == Const.STATUS_OK) {
                        pageCountPending++
                        val totalCount = jsonObject.getJSONObject("_meta").getInt("pageCount")
                        isSingleHitPending = pageCountPending >= totalCount

                        val jsonArray = jsonObject.getJSONArray("list")
                        for (i in 0 until jsonArray.length()) {
                            val object1 = jsonArray.getJSONObject(i)
                            val data = Gson().fromJson<UpcomingAppointmentData>(object1.toString(), UpcomingAppointmentData::class.java)
                            upcomingArrayList.add(data)
                        }
                        setUpcomingAppointmentAdapter()
                    }
                }
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private fun setAdapter() {
        if (adapter == null) {
            adapter = DoctorConsulateAdapter(arrayList, this)
            adapter!!.setOnItemClickListener(this)
            binding!!.modeRV.adapter = adapter
        } else {
            adapter!!.notifyDataSetChanged()
        }
    }

    override fun onItemClick(vararg itemData: Any) {
        val id = itemData[0] as Int
        when (id) {
            Const.OPEN_PAYMENT -> {
                val pos = itemData[1] as Int
                if (oldPos == -1) {
                    oldPos = pos
                    arrayList[oldPos].isSelected = true
                } else {
                    arrayList[oldPos].isSelected = false
                    oldPos = pos
                    arrayList[oldPos].isSelected = true
                }
                selectedMode(arrayList[oldPos].id)
                setAdapter()
            }
        }
    }

    private fun selectedMode(id: Int) {
        val param = Api3Params()
        param.put("DoctorConsultationAvailability[consultation_id]", id)
        val call = api!!.saveModes(param.getServerHashMap())
        restFullClient!!.sendRequest(call, this)
    }

    fun showAcceptDialog(position: Int) {
        this.adapterPos = position
        val dialogBuilder = AlertDialog.Builder(baseActivity!!)
        val dialogBinding = DataBindingUtil.inflate<DialogAcceptAppointmentBinding>(LayoutInflater.from(baseActivity), R.layout.dialog_accept_appointment, null, false)
        dialogBuilder.setView(dialogBinding!!.root)
        alertDialog = dialogBuilder.create()
        val width = (baseActivity!!.resources.displayMetrics.widthPixels * 0.85).toInt()
        alertDialog!!.show()
        alertDialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        alertDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogBinding.noBT.setOnClickListener(this)
        dialogBinding.yesBT.setOnClickListener(this)
        dialogBinding.close.setOnClickListener(this)
    }

}
