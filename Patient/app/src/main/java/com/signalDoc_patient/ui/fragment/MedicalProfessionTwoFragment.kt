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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentMedicalProfessionTwoBinding
import com.signalDoc_patient.model.ProfileData
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.adapter.DoctorListAdapter
import com.signalDoc_patient.utils.Const
import com.signalDoc_patient.viewModel.commonViewModel.HomeScreenViewModel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_medical_profession_two.*

class MedicalProfessionTwoFragment : BaseFragment() {
    private var binding: FragmentMedicalProfessionTwoBinding? = null
    private var adapterDoc: DoctorListAdapter? = null
    private lateinit var homeViewModel: HomeViewModel
    private var categoryId: Int = 0
    private var type: Int = 0
    private var title: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProviders.of(this)[HomeViewModel::class.java]
        homeViewModel.setData(baseActivity!!, restFullClient!!, api!!, this)
        arguments?.let {
            categoryId = it.getInt("id")
            type = it.getInt("type")
            title = it.getString("title")!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (type == Const.FIRST_NAME) {
            (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.categories), true)

        } else {
            (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.medical_prof), true)

        }
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_medical_profession_two, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        genralTV.text = title
        val observerObj: Observer<ArrayList<ProfileData>> = Observer {
            adapterDoc = DoctorListAdapter(baseActivity, it, title)
            binding!!.categoriesRV.adapter = adapterDoc
        }
        homeViewModel.apiGetMedicalDoctorsList(categoryId).observe(this, observerObj)


    }

    private fun setDocAdapter() {

    }
}



