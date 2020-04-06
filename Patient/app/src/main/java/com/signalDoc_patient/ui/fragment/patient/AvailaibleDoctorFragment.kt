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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.signalDoc_patient.BuildConfig

import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentAvailaibleDoctorBinding
import com.signalDoc_patient.ui.adapter.AvailaibleDoctorsAdapter
import com.signalDoc_patient.ui.adapter.BaseAdapter
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.ui.fragment.PaymentSuccessfulFragment


class AvailaibleDoctorFragment : BaseFragment(), BaseAdapter.OnItemClickListener {

    override fun onItemClick(vararg itemData: Any) {
        expandCloseSheet()
    }

    private var binding: FragmentAvailaibleDoctorBinding? = null
    private lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var sheetConfirmBehavior: BottomSheetBehavior<ConstraintLayout>
    private var availDocAdapter: AvailaibleDoctorsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_availaible_doctor, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        binding!!.root.setOnClickListener(this)



        bottomSheet()
        setAvailaibleDocAdapter()

        confirmBottom()


        binding!!.comfirmBottom.cardCV.setOnClickListener(this)
        binding!!.comfirmBottom.confirmPaymentBT.setOnClickListener{
            baseActivity!!.replaceFragment(PaymentSuccessfulFragment(),R.id.frame_container)
        }


    }

    private fun setAvailaibleDocAdapter() {
        availDocAdapter = AvailaibleDoctorsAdapter()
        availDocAdapter!!.setOnItemClickListener(this)
        binding!!.availaibleDoctorsRV.adapter = availDocAdapter

    }

    private fun bottomSheet() {
        sheetBehavior = BottomSheetBehavior.from<LinearLayout>(binding!!.bottom.consultationBottomCL)

        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {


            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (BuildConfig.DEBUG) {
                    Log.e("onStateChanged", "onStateChanged:$newState")

                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (BuildConfig.DEBUG) {
                    Log.e("onSlide", "onSlide")
                }
            }
        })

    }

    private fun expandCloseSheet() {
        if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }


    private fun expandCloseSheetConfirm() {
        if (sheetConfirmBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            sheetConfirmBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            sheetConfirmBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }


    private fun confirmBottom() {
        sheetConfirmBehavior = BottomSheetBehavior.from<ConstraintLayout>(binding!!.comfirmBottom.confirmBottom)

        sheetConfirmBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {


            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (BuildConfig.DEBUG) {
                    Log.e("onStateChanged", "onStateChanged:$newState")
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (BuildConfig.DEBUG) {
                    Log.e("onSlide", "onSlide")
                }
            }
        })

    }


    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.continueBT -> {
                baseActivity!!.hideSoftKeyboard()
                expandCloseSheet()
            }

            R.id.crossIV -> {
                baseActivity!!.hideSoftKeyboard()

                expandCloseSheet()
            }

            R.id.callTV -> {
                expandCloseSheetConfirm()


            }

            R.id.messageTV -> {
                expandCloseSheetConfirm()


            }

           /* R.id.faceTV -> {
                expandCloseSheetConfirm()


            }

            R.id.videoCallTV -> {
                expandCloseSheetConfirm()


            }*/


    }


}}
