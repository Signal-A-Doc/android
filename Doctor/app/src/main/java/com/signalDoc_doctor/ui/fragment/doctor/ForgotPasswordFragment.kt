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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.FragmentForgotPasswordBinding
import com.signalDoc_doctor.ui.fragment.BaseFragment

class ForgotPasswordFragment : BaseFragment() {

    private var binding:FragmentForgotPasswordBinding?=null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false)
        }

        return binding!!.root
    }


}
