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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import co.paystack.android.Transaction
import co.paystack.android.model.Charge
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.signalDoc_patient.BuildConfig
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentAvailableAppointmentBinding
import com.signalDoc_patient.model.ConsulatitionData
import com.signalDoc_patient.model.ProfileData
import com.signalDoc_patient.model.TimeSlotsData
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.adapter.AvalAppointmentAdapter
import com.signalDoc_patient.ui.adapter.BaseAdapter
import com.signalDoc_patient.ui.adapter.DoctorConsulateAdapter
import com.signalDoc_patient.ui.extensions.handleException
import com.signalDoc_patient.ui.extensions.replaceFragWithArgs
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.utils.Const
import com.signalDoc_patient.utils.DayEnableDecorator
import com.signalDoc_patient.utils.payStack.CallBackPayment
import com.signalDoc_patient.utils.payStack.PaymentGateway
import com.toxsl.restfulClient.api.Api3Params
import kotlinx.android.synthetic.main.fragment_available_appointment.*
import org.json.JSONObject
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class AvailableAppointmentFragment : BaseFragment() {

    private var profileData: ProfileData? = null

    private var binding: FragmentAvailableAppointmentBinding? = null
    private var enabledDates: ArrayList<CalendarDay> = ArrayList()
    private var timeSlotsArrayList = ArrayList<TimeSlotsData>()
    private var slotsAdapter: AvalAppointmentAdapter? = null
    private var oldPosition = -1
    private var timeSlotsId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            profileData = it.getParcelable(Const.PROFILE_DATA)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.available_appointments), false)

        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_available_appointment, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.calenderV.setOnDateChangedListener { widget, date, selected ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(Calendar.YEAR, date.year)
            selectedDate.set(Calendar.MONTH, date.month - 1)
            selectedDate.set(Calendar.DAY_OF_MONTH, date.day)
            log("date_selection : " + baseActivity!!.changeDateFormatFromDate(selectedDate.time, "yyyy-MM-dd"))
            apiGetDoctorSlots(baseActivity!!.changeDateFormatFromDate(selectedDate.time, "yyyy-MM-dd"))
        }

        initUi()
        apiGetDoctorList()
    }

    private fun initUi() {
        binding!!.comfirmBottom.cardCV.setOnClickListener(this)
        binding!!.bookAppoitmentCV.setOnClickListener(this)
        setData()
    }

    private fun setData() {
        binding!!.drTV.setText(getString(R.string.dr) + " " + profileData!!.fullName)
        binding!!.nameTV.setText(getString(R.string.dr) + " " + profileData!!.fullName)
        if (profileData!!.profileFile.isNotEmpty()) {
            baseActivity!!.loadImage(baseActivity!!, profileData!!.profileFile, R.mipmap.ic_default_profile, binding!!.imageIV)
        } else {
            binding!!.imageIV.setImageResource(R.mipmap.ic_default_profile)
        }

    }

    private fun setAvailableAppointmentAdapter() {
        if (slotsAdapter == null) {
            slotsAdapter = AvalAppointmentAdapter(timeSlotsArrayList, this, baseActivity)
            baseActivity!!.timeSlotRV.adapter = slotsAdapter
        } else {
            slotsAdapter!!.notifyDataSetChanged()
        }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.bookAppoitmentCV -> {
                if (validation()) {
                    val bundle = Bundle()
                    bundle.putBoolean(Const.IS_OPEN_FROM_TOP_BUTTON, false)
                    bundle.putParcelable(Const.PROFILE_DATA, profileData)
                    bundle.putString(Const.SELECTED_TIME_SLOTS,timeSlotsId)
                    baseActivity!!.replaceFragWithArgs(AppointmentInfoFragment(), R.id.frame_container, bundle)

                }
            }


        }


    }

    fun selectedSlots(position: Int) {
        if (oldPosition == -1) {
            oldPosition = position
            timeSlotsId = timeSlotsArrayList[oldPosition].id.toString()
            timeSlotsArrayList[oldPosition].isSelected = true
        } else {
            timeSlotsArrayList[oldPosition].isSelected = false
            oldPosition = position
            timeSlotsId = timeSlotsArrayList[oldPosition].id.toString()
            timeSlotsArrayList[oldPosition].isSelected = true
        }
        binding!!.bookAppoitmentTV.setTextColor(ContextCompat.getColor(baseActivity!!, R.color.White))
        binding!!.bookAppoitmentTV.setBackgroundColor(ContextCompat.getColor(baseActivity!!, R.color.colorAccent))
        setAvailableAppointmentAdapter()
    }


    override fun onSyncSuccess(responseCode: Int, responseMessage: String, responseUrl: String, response: String?) {
        super.onSyncSuccess(responseCode, responseMessage, responseUrl, response)
        try {
            val jsonObject = JSONObject(response!!)
            when (responseUrl) {
                Const.API_GET_DOCTOR_DATE_LIST -> {
                    if (responseCode == Const.STATUS_OK) {
                        enabledDates.clear()
                        val jsonArray = jsonObject.getJSONArray("dates")
                        val firstDate = jsonArray.getString(0)
                        val data1 = firstDate.split("-")
                        val selectedDate = CalendarDay.from(data1[0].toInt(), data1[1].toInt(), data1[2].toInt())

                        for (i in 0 until jsonArray.length()) {
                            val object1 = jsonArray.getString(i)
                            val data = object1.split("-")
                            enabledDates.add(CalendarDay.from(data[0].toInt(), data[1].toInt(), data[2].toInt()))
                        }
                        binding!!.calenderV.addDecorator(DayEnableDecorator(enabledDates))
                        binding!!.calenderV.setSelectedDate(selectedDate)
                        slotsAdapter = null
                        apiGetDoctorSlots(firstDate)
                    }
                }
                Const.API_GET_DOCTOR_TIME_SLOTS_LIST -> {
                    if (responseCode == Const.STATUS_OK) {
                        timeSlotsArrayList.clear()
                        val jsonArray = jsonObject.getJSONArray("detail")
                        for (i in 0 until jsonArray.length()) {
                            val object1 = jsonArray.getJSONObject(i)
                            val data = Gson().fromJson<TimeSlotsData>(object1.toString(), TimeSlotsData::class.java)
                            timeSlotsArrayList.add(data)
                        }

                        setAvailableAppointmentAdapter()

                    }
                }

            }
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private fun apiGetDoctorList() {
        val call = api!!.apiGetDoctorDateList(profileData!!.id)
        restFullClient!!.sendRequest(call, this)
    }

    private fun apiGetDoctorSlots(changeDateFormatFromDate: String) {
        slotsAdapter = null
        binding!!.todayTV.setText(changeDateFormatFromDate)
        val param = Api3Params()
        param.put("AvailabilityTime[date]", changeDateFormatFromDate)
        val call = api!!.apiGetDoctorTimeList(profileData!!.id, param.getServerHashMap())
        restFullClient!!.sendRequest(call, this)
    }


    private fun validation(): Boolean {
        when {
            timeSlotsId.isNullOrEmpty() -> showToastOne(getString(R.string.please_select_time_slot_first))
            else -> return true
        }
        return false
    }


}
