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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.databinding.DataBindingUtil
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentHowLongDaysBinding
import com.signalDoc_patient.model.HealthProfileData
import com.signalDoc_patient.model.ProfileData
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.extensions.replaceFragWithArgs
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.utils.Const
import com.warkiz.tickseekbar.OnSeekChangeListener
import com.warkiz.tickseekbar.SeekParams
import com.warkiz.tickseekbar.TickSeekBar
import kotlinx.android.synthetic.main.fragment_how_long_days.*


class HowLongDaysFragment : BaseFragment() {
    private var binding: FragmentHowLongDaysBinding? = null
    private var type = 0
    private var isOpenFromTop = false
    private var profileData: ProfileData? = null
    private var timeSlotsId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            isOpenFromTop = bundle.getBoolean(Const.IS_OPEN_FROM_TOP_BUTTON)
            profileData = bundle.getParcelable(Const.PROFILE_DATA)
            timeSlotsId = bundle.getString(Const.SELECTED_TIME_SLOTS)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.signaladoc), false)

        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_how_long_days, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        rangeSeekbar.max = 12f
        rangeSeekbar.min = 1f
        dayCountTV.text = rangeSeekbar.min.toInt().toString()


        rangeSeekbar.setOnSeekChangeListener(object : OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams) {
                log("progress>>" + seekParams.progress)
                dayCountTV.text = seekParams.progress.toString()

            }

            override fun onStartTrackingTouch(seekBar: TickSeekBar) {}
            override fun onStopTrackingTouch(seekBar: TickSeekBar) {}
        })

        val adapter: ArrayAdapter<String> = ArrayAdapter(baseActivity!!,
                android.R.layout.simple_spinner_item, resources
                .getStringArray(R.array.time_array)) //setting the country_array to spinner

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinCountry.adapter = adapter
        spinCountry.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>?, arg1: View?,
                                        position: Int, id: Long) {
                log("positon>$position")
                type = position
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        })
        binding!!.continueBT.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.continueBT -> goToAppointmentInfoThree()
        }
    }

    private fun goToAppointmentInfoThree() {
        val bundle = Bundle()
        val data = HealthProfileData()
        data.howLongDay = dayCountTV.text.toString().trim()
        data.howLongDayType = type
        if (arguments != null && arguments!!.containsKey("category_id")) {
            bundle.putInt("category_id", arguments!!.getInt("category_id"))
            bundle.putParcelable("data", data)
            bundle.putBoolean(Const.IS_OPEN_FROM_TOP_BUTTON, isOpenFromTop)
            bundle.putParcelable(Const.PROFILE_DATA, profileData)
            bundle.putString(Const.SELECTED_TIME_SLOTS,timeSlotsId)
            baseActivity!!.replaceFragWithArgs(AppointmentInfoThreeFragment(), R.id.frame_container, bundle)
        }
    }


}
