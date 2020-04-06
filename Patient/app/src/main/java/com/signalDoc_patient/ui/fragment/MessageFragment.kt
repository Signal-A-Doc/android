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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.signalDoc_patient.BuildConfig

import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentMessageBinding
import com.signalDoc_patient.ui.adapter.ChatAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MessageFragment : BaseFragment() {

    private var binding: FragmentMessageBinding?=null
    private var adapterChat: ChatAdapter?=null
    private lateinit var sheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        baseActivity!!.navBNV.visibility=View.GONE
        if(binding==null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, container, false)
        }
        return binding!!.root;

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        setCategoriesAdapter()
        bottomSheet()
        binding!!.addIV.setOnClickListener(this)
    }

    private fun setCategoriesAdapter() {
        adapterChat= ChatAdapter()
        binding!!.messageRV.adapter=adapterChat

    }



    private fun bottomSheet() {
        sheetBehavior = BottomSheetBehavior.from<ConstraintLayout>(binding!!.bottomAdd.addBottom)

        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {


            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if(BuildConfig.DEBUG) {
                    Log.e("onStateChanged", "onStateChanged:$newState")
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if(BuildConfig.DEBUG){
                Log.e("onSlide", "onSlide")
            }}
        })

    }

    private fun expandCloseSheet() {
        if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    override fun onClick(v: View) {
        when(v.id)
        {
            R.id.addIV-> expandCloseSheet()
        }
    }

}
