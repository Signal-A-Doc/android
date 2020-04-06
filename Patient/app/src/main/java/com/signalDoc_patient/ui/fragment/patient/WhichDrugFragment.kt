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
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentWhichDrugBinding
import com.signalDoc_patient.model.HealthProfileData
import com.signalDoc_patient.ui.extensions.checkString
import com.signalDoc_patient.ui.extensions.isBlank
import com.signalDoc_patient.ui.extensions.replaceFragWithArgs
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.utils.Const
import kotlinx.android.synthetic.main.fragment_sign_up.*


class WhichDrugFragment : BaseFragment() {
    private var binding: FragmentWhichDrugBinding? = null
    private var data: HealthProfileData? = null
    private var isFirstTime = false
    private var drugList: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable("data")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (binding == null) {
            isFirstTime = true
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_which_drug, container, false)
            initUI()

        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun initUI() {
        binding!!.allergyTwoET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                if (isFirstTime) {
                    isFirstTime = false
                    if (binding!!.allergyOneET.isBlank()) {
                        baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.pls_Fix_in_the_first_box_first))
                        binding!!.allergyTwoET.text!!.clear()
                    }

                }
            }


            override fun afterTextChanged(s: Editable) {

            }
        })

        binding!!.allergyThreeET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                if (isFirstTime) {
                    isFirstTime = false
                    if (binding!!.allergyTwoET.isBlank()) {
                        baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.pls_Fix_in_the_second_box_first))
                        binding!!.allergyThreeET.text!!.clear()
                    }
                }
            }


            override fun afterTextChanged(s: Editable) {

            }
        })
        binding!!.saveBT.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.saveBT -> {
                if (binding!!.allergyOneET.isBlank()) {
                    baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.please_enter_atleast_one_drug))
                } else {
                    gotoAnyHealthFrag()
                }

            }

        }
    }

    private fun gotoAnyHealthFrag() {
        drugList.clear()
        if (!binding!!.allergyOneET.isBlank()) {
            drugList.add(binding!!.allergyOneET.checkString())
        }
        if (!binding!!.allergyTwoET.isBlank()) {
            drugList.add(binding!!.allergyTwoET.checkString())
        }
        if (!binding!!.allergyThreeET.isBlank()) {
            drugList.add(binding!!.allergyThreeET.checkString())

        }
        data!!.drugList = drugList

        val bundle = Bundle()
        bundle.putParcelable("data", data)
        baseActivity!!.replaceFragWithArgs(AnyMedicalHealthFragment(), R.id.frame_container, bundle)

    }

}
