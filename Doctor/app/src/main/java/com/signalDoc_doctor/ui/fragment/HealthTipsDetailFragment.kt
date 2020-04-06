/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.FragmentHealthTipDetailBinding
import com.signalDoc_doctor.ui.adapter.CommentsAdapter

class HealthTipsDetailFragment : Fragment() {

    private var binding: FragmentHealthTipDetailBinding?=null
    private var adapterComments:CommentsAdapter?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if(binding==null){
            binding= DataBindingUtil.inflate(inflater,R.layout.fragment_health_tip_detail, container, false)

        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        initUi()
    }

    private fun initUi() {

        setCommentsAdapter()

    }

    private fun setCommentsAdapter() {
        adapterComments= CommentsAdapter()
        binding!!.commentRV.adapter=adapterComments

    }


}
