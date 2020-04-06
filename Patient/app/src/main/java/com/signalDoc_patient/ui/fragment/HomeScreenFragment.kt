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
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentHomeScreenBinding
import com.signalDoc_patient.model.ProfileData
import com.signalDoc_patient.model.Symptom
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.adapter.CategoriesAdapter
import com.signalDoc_patient.ui.adapter.DoctorListAdapter
import com.signalDoc_patient.ui.adapter.HomeNewsAdapter
import com.signalDoc_patient.ui.extensions.replaceFragWithArgs
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.extensions.visibleView
import com.signalDoc_patient.ui.fragment.patient.AppointmentInfoFragment
import com.signalDoc_patient.utils.Const
import com.signalDoc_patient.viewModel.commonViewModel.HomeScreenViewModel.HomeViewModel

class HomeScreenFragment : BaseFragment() {
    private var binding: FragmentHomeScreenBinding? = null
    private var doctorAdapter: DoctorListAdapter? = null
    private var newsAdapter: HomeNewsAdapter? = null
    private var categoriesAdapter: CategoriesAdapter? = null
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProviders.of(this)[HomeViewModel::class.java]
        homeViewModel.setData(baseActivity!!, restFullClient!!, api!!, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(true, "", true)
        (baseActivity as MainActivity).selectTab(Const.HOME)

        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_screen, container, false)
            initUi()
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
    }

    private fun initUi() {
        binding!!.searchSignalCV.setOnClickListener(this)
        binding!!.viewBox.setOnClickListener(this)
        val observerObj: Observer<ArrayList<ProfileData>> = Observer {
            doctorAdapter = DoctorListAdapter(baseActivity!!, it, "")
            binding!!.doctorRV.adapter = doctorAdapter
            binding!!.doctorRV.visibleView(it.isNotEmpty())
        }
        homeViewModel.apiGetDoctorsList().observe(this, observerObj)

        val departobserverObj: Observer<ArrayList<Symptom>> = Observer {
            categoriesAdapter = CategoriesAdapter(baseActivity, it)
            binding!!.categoriesRV.adapter = categoriesAdapter
            binding!!.categoriesRV.visibleView(it.isNotEmpty())
        }
        homeViewModel.apiGetDepartmentList().observe(this, departobserverObj)
        setNewsAdapter()

    }

    private fun setNewsAdapter() {
        newsAdapter = HomeNewsAdapter(baseActivity)
        binding!!.newsRV.adapter = newsAdapter
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.searchSignalCV -> {
                val bundle = Bundle()
                bundle.putBoolean(Const.IS_OPEN_FROM_TOP_BUTTON, true)
                baseActivity!!.replaceFragWithArgs(AppointmentInfoFragment(), R.id.frame_container, bundle)
            }
            R.id.viewBox
            -> {
                baseActivity!!.showErrorToast(getString(R.string.under_delvelopment))
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.notification_menu, menu)

    }

}
