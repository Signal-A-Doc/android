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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentDocumentsBinding
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.adapter.DocumentsAdapter
import com.signalDoc_patient.ui.extensions.replaceFragment


class DocumentsFragment : BaseFragment() {
    private var binding:FragmentDocumentsBinding?=null
    private var adapterDoc:DocumentsAdapter?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.appointment_info),false)


        if (binding==null) {
            binding = DataBindingUtil.inflate(inflater,R.layout.fragment_documents, container, false)
        }
        return binding!!.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi();

    }

    private fun initUi() {

        setDocAdapter()


        binding!!.addDocTV.setOnClickListener(this)

    }

    private fun setDocAdapter() {
        adapterDoc=DocumentsAdapter()
        binding!!.docRV.adapter=adapterDoc

    }


    override fun onClick(view: View) {
        when(view.id)
        {

            R.id.addDocTV->{
                baseActivity!!.replaceFragment(HomeScreenFragment(),R.id.frame_container)

            }

        }

    }


}
