package com.signalDoc_patient.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentCallBinding
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.ui.fragment.HomeScreenFragment

class CallFragment : BaseFragment() {

    private var binding: FragmentCallBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(false, "",false)

        if (binding==null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_call, container, false)
        }
        return binding!!.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi();
        binding!!.callEndIV.setOnClickListener(this)
    }

    private fun initUi() {


    }


    override fun onClick(view: View) {
        when(view.id)
        {

            R.id.callEndIV->{
                baseActivity!!.replaceFragment(HomeScreenFragment(),R.id.frame_container)

            }

        }

    }

}