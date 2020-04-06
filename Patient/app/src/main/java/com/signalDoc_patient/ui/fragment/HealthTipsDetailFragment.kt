package com.signalDoc_patient.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentHealthTipDetailBinding
import com.signalDoc_patient.ui.adapter.CommentsAdapter
import com.signalDoc_patient.ui.fragment.BaseFragment

class HealthTipsDetailFragment : BaseFragment() {

    private var binding: FragmentHealthTipDetailBinding? = null
    private var adapterComments: CommentsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_health_tip_detail, container, false)

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
        adapterComments = CommentsAdapter()
        binding!!.commentRV.adapter = adapterComments

    }

}