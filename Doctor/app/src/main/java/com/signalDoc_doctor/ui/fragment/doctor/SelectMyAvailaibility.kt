/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.ui.fragment.doctor


import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.prolificinteractive.materialcalendarview.CalendarDay

import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.EndDialogCustomBinding
import com.signalDoc_doctor.databinding.FragmentSelectMyAvailaibilityBinding
import com.signalDoc_doctor.databinding.StartDialogCustomBinding
import com.signalDoc_doctor.ui.adapter.BaseAdapter
import com.signalDoc_doctor.ui.adapter.DoctorLanguageSpokenAdapter
import com.signalDoc_doctor.ui.adapter.DoctorTimeSlotsAdapter
import com.signalDoc_doctor.ui.extensions.handleException
import com.signalDoc_doctor.ui.fragment.BaseFragment
import com.signalDoc_doctor.utils.Const
import com.signalDoc_doctor.utils.RangeDayDecorator
import com.toxsl.restfulClient.api.Api3Params
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.min

class SelectMyAvailaibility : BaseFragment(), BaseAdapter.OnItemClickListener {

    private var binding: FragmentSelectMyAvailaibilityBinding? = null
    private val calenderStart = Calendar.getInstance()
    private val calenderEnd = Calendar.getInstance()
    private var customTime = ""
    private var customShowTime = ""
    private var selectedDates = ArrayList<CalendarDay>()
    private var selectedTimeList = ArrayList<String>()
    private var showTimeList = ArrayList<String>()
    private var adapterTime: DoctorTimeSlotsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_my_availaibility, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.continueBT.setOnClickListener(this)

        binding!!.startTimeLL.setOnClickListener(this)
        binding!!.startTimeTV.setOnClickListener(this)
        binding!!.startTimeET.setOnClickListener(this)

        binding!!.endTimeLL.setOnClickListener(this)
        binding!!.endTimeTV.setOnClickListener(this)
        binding!!.endTimeET.setOnClickListener(this)

        binding!!.timeLL.setOnClickListener(this)
        binding!!.timeTV.setOnClickListener(this)
        binding!!.timeET.setOnClickListener(this)


        binding!!.calenderV.state().edit().setMinimumDate(CalendarDay.today()).commit()
        binding!!.calenderV.setOnRangeSelectedListener { widget, dates: List<CalendarDay> ->
            selectedDates.clear()
            selectedDates.addAll(dates)
        }

        binding!!.calenderV.addDecorator(RangeDayDecorator(baseActivity));

        binding!!.availableRG.setOnCheckedChangeListener { group, checkedId ->
            binding!!.startTimeET.setText("")
            binding!!.endTimeET.setText("")
            binding!!.timeET.setText("")
            showTimeList.clear()
            selectedTimeList.clear()
            adapterTime = null
            if (binding!!.allDayRB.isChecked) {
                binding!!.allDayCL.visibility = View.VISIBLE
                binding!!.customCL.visibility = View.GONE
                binding!!.allDayRB.background = ContextCompat.getDrawable(baseActivity!!, R.drawable.blue_border_box)
                binding!!.customRB.background = ContextCompat.getDrawable(baseActivity!!, R.drawable.grey_border_box)
            } else if (binding!!.customRB.isChecked) {
                binding!!.allDayCL.visibility = View.GONE
                binding!!.customCL.visibility = View.VISIBLE
                binding!!.customRB.background = ContextCompat.getDrawable(baseActivity!!, R.drawable.blue_border_box)
                binding!!.allDayRB.background = ContextCompat.getDrawable(baseActivity!!, R.drawable.grey_border_box)
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.startTimeLL, R.id.startTimeTV, R.id.startTimeET -> {
                customTime = ""
                val calendarDay = Calendar.getInstance()
                val dialog = TimePickerDialog(baseActivity!!, { timePicker, hourOfDay, minute ->

                    calenderStart.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calenderStart.set(Calendar.MINUTE, minute)
                    customTime = baseActivity!!.changeDateFormatFromDate(calenderStart.time, "HH:mm:ss")
                    binding!!.startTimeET.setText(baseActivity!!.changeDateFormatFromDate(calenderStart.time, "hh:mm a"))
                }, calendarDay.get(Calendar.HOUR_OF_DAY), calendarDay.get(Calendar.MINUTE), false)
                dialog.show()
                binding!!.endTimeET.setText("")
            }
            R.id.endTimeLL, R.id.endTimeTV, R.id.endTimeET -> {
                val calendarDay = Calendar.getInstance()
                val dialog = TimePickerDialog(baseActivity!!, { timePicker, hourOfDay, minute ->
                    calenderEnd.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calenderEnd.set(Calendar.MINUTE, minute)
                    if (calenderStart.before(calenderEnd)) {
                        customTime = customTime + " - " + baseActivity!!.changeDateFormatFromDate(calenderEnd.time, "HH:mm:ss")
                        binding!!.endTimeET.setText(baseActivity!!.changeDateFormatFromDate(calenderEnd.time, "hh:mm a"))
                        selectedTimeList.add(customTime)
                    } else {
                        baseActivity!!.showErrorToast(getString(R.string.please_enter_valid_end_date))
                    }
                }, calendarDay.get(Calendar.HOUR_OF_DAY), calendarDay.get(Calendar.MINUTE), false)
                dialog.show()
            }
            R.id.timeLL, R.id.timeTV, R.id.timeET -> {
                startTimeDialog()
            }

            R.id.continueBT -> {
                if (validation()) {
                    val param = Api3Params()
                    param.put("DoctorAvailability[data]", makeJsonObject())
                    val call = api!!.apiHitDateSave(param.getServerHashMap())
                    restFullClient!!.sendRequest(call, this)
                }
            }
        }

    }

    private fun startTimeDialog() {
        customTime = ""
        customShowTime = ""
        var hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        var min = Calendar.getInstance().get(Calendar.MINUTE)

        val builder = AlertDialog.Builder(baseActivity!!)
        val dialogCustomBinding = DataBindingUtil.inflate<StartDialogCustomBinding>(LayoutInflater.from(baseActivity), R.layout.start_dialog_custom, null, false)
        builder.setView(dialogCustomBinding.root)
        val dialog = builder.create()
        dialogCustomBinding.timmerTP.setOnTimeChangedListener { view, hourOfDay, minute ->
            log("start_data : " + hourOfDay + " : " + minute)
            hours = hourOfDay
            min = minute
        }
        dialogCustomBinding.timmerTP.setIs24HourView(false)

        dialogCustomBinding.okTV.setOnClickListener {
            calenderStart.set(Calendar.HOUR_OF_DAY, hours)
            calenderStart.set(Calendar.MINUTE, min)
            customTime = baseActivity!!.changeDateFormatFromDate(calenderStart.time, "HH:mm:ss")
            customShowTime = baseActivity!!.changeDateFormatFromDate(calenderStart.time, "hh:mm a")
            dialog.dismiss()
            endTimeDialog()
        }
        dialogCustomBinding.cancelTV.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun endTimeDialog() {
        var hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        var min = Calendar.getInstance().get(Calendar.MINUTE)

        val builder = AlertDialog.Builder(baseActivity!!)
        val dialogCustomBinding = DataBindingUtil.inflate<EndDialogCustomBinding>(LayoutInflater.from(baseActivity), R.layout.end_dialog_custom, null, false)
        builder.setView(dialogCustomBinding.root)
        val dialog = builder.create()
        dialogCustomBinding.timmerTP.setOnTimeChangedListener { view, hourOfDay, minute ->
            log("end_data : " + hourOfDay + " : " + minute)
            hours = hourOfDay
            min = minute
        }
        dialogCustomBinding.timmerTP.setIs24HourView(false)

        dialogCustomBinding.okTV.setOnClickListener {
            calenderEnd.set(Calendar.HOUR_OF_DAY, hours)
            calenderEnd.set(Calendar.MINUTE, min)
            if (calenderStart.before(calenderEnd)) {
                customTime = customTime + " - " + baseActivity!!.changeDateFormatFromDate(calenderEnd.time, "HH:mm:ss")
                customShowTime = customShowTime + " - " + baseActivity!!.changeDateFormatFromDate(calenderEnd.time, "hh:mm a")
                dialog.dismiss()
                selectedTimeList.add(customTime)
                showTimeList.add(customShowTime)
                setAdapter()
            } else {
                baseActivity!!.showErrorToast(getString(R.string.please_enter_valid_end_date))
            }
        }
        dialogCustomBinding.cancelTV.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun validation(): Boolean {
        when {
            selectedDates.size == 0 -> showErrorToastOne(getString(R.string.please_select_date_first))
            binding!!.allDayRB.isChecked && binding!!.startTimeET.text.toString().isEmpty() -> showErrorToastOne(getString(R.string.please_select_start_time))
            binding!!.allDayRB.isChecked && binding!!.endTimeET.text.toString().isEmpty() -> showErrorToastOne(getString(R.string.please_select_end_time))
            binding!!.customRB.isChecked && selectedTimeList.size == 0 -> showErrorToastOne(getString(R.string.please_enter_time_slots))
            else -> return true
        }
        return false
    }

    private fun makeJsonObject(): String {
        val superMainJsonObject = JSONObject()
        val mainJsonArray = JSONArray()

        selectedDates.forEach { dates ->
            val jsonObject = JSONObject()
            val selectedDate = Calendar.getInstance()
            selectedDate.set(Calendar.YEAR, dates.year)
            selectedDate.set(Calendar.MONTH, dates.month-1)
            selectedDate.set(Calendar.DAY_OF_MONTH, dates.day)
            jsonObject.put("date", baseActivity!!.changeDateFormatFromDate(selectedDate.time, "yyyy-MM-dd"))

            val timeJsonArray = JSONArray()
            selectedTimeList.forEach { time ->
                timeJsonArray.put(time)
            }
            jsonObject.put("time_slots", timeJsonArray)

            mainJsonArray.put(jsonObject)
        }
        superMainJsonObject.put("data", mainJsonArray)
        return superMainJsonObject.toString()
    }

    private fun setAdapter() {
        if (adapterTime == null) {
            adapterTime = DoctorTimeSlotsAdapter(showTimeList)
            adapterTime!!.setOnItemClickListener(this)
            binding!!.timeRV.adapter = adapterTime
        } else {
            adapterTime!!.notifyDataSetChanged()
        }
    }

    override fun onItemClick(vararg itemData: Any) {
        val id = itemData[0] as Int
        when (id) {
            Const.REMOVE_TIME -> {
                val pos = itemData[1] as Int
                selectedTimeList.removeAt(pos)
                showTimeList.removeAt(pos)
                setAdapter()
            }
        }
    }

    override fun onSyncSuccess(responseCode: Int, responseMessage: String, responseUrl: String, response: String?) {
        super.onSyncSuccess(responseCode, responseMessage, responseUrl, response)
        try {
            val jsonObject = JSONObject(response!!)
            when {
                responseUrl == Const.API_SAVING_DOCTOR_DATE_LIST -> {
                    if (responseCode == Const.STATUS_OK) {
                        baseActivity!!.showSuccessToast(jsonObject.getString("message"))
                    }
                }
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }

}
