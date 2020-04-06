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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentAppointmentInfoBinding
import com.signalDoc_patient.model.CategoriesData
import com.signalDoc_patient.model.ProfileData
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.adapter.DiseaseAdapter
import com.signalDoc_patient.ui.extensions.replaceFragWithArgs
import com.signalDoc_patient.ui.extensions.visibleView
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.utils.Const
import com.signalDoc_patient.viewModel.commonViewModel.HomeScreenViewModel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_appointment_info.*


class AppointmentInfoFragment : BaseFragment() {
    private var binding: FragmentAppointmentInfoBinding? = null
    private var adapterDisease: DiseaseAdapter? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var datas: ArrayList<CategoriesData>
    private var isOpenFromTop = false
    private var profileData: ProfileData? = null
    private var timeSlotsId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            isOpenFromTop = bundle.getBoolean(Const.IS_OPEN_FROM_TOP_BUTTON)
            profileData = bundle.getParcelable(Const.PROFILE_DATA)
            timeSlotsId = bundle.getString(Const.SELECTED_TIME_SLOTS)
        }
        homeViewModel = ViewModelProviders.of(this)[HomeViewModel::class.java]
        homeViewModel.setData(baseActivity!!, restFullClient!!, api!!, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.signal_a_doc), false)

        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appointment_info, container, false)

        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        seeAllTV.setOnClickListener(this)
        seeAllTV.visibility = View.VISIBLE
        seeLessTV.visibility = View.GONE
        seeLessTV.setOnClickListener(this)
        val observerObj: Observer<ArrayList<CategoriesData>> = Observer {
            this.datas = it
            setList(seeAllTV, seeLessTV, false)
        }
        homeViewModel.apiGetCategories().observe(this, observerObj)
    }

    private fun setAdapter(it: ArrayList<CategoriesData>, seeAll: Boolean) {
        adapterDisease = DiseaseAdapter(baseActivity, it, seeAll, this)
        binding!!.diseaseRV.adapter = adapterDisease
        binding!!.diseaseRV.visibleView(it.isNotEmpty())
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.seeAllTV -> {
                setList(seeLessTV, seeAllTV, true)

            }
            R.id.seeLessTV -> {
                setList(seeAllTV, seeLessTV, false)
            }
        }
    }

    private fun setList(visibleView: AppCompatTextView?, hiddenView: AppCompatTextView?, seeAll: Boolean) {
        visibleView!!.visibility = View.VISIBLE
        hiddenView!!.visibility = View.GONE
        setAdapter(datas, seeAll)

    }

    fun openNext(pos: Int) {
        val bundle = Bundle()
        bundle.putInt("category_id", datas[pos].id)
        //  baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.under_delvelopment))
        bundle.putBoolean(Const.IS_OPEN_FROM_TOP_BUTTON, isOpenFromTop)
        bundle.putParcelable(Const.PROFILE_DATA, profileData)
        bundle.putString(Const.SELECTED_TIME_SLOTS, timeSlotsId)
        baseActivity!!.replaceFragWithArgs(HowLongDaysFragment(), R.id.frame_container, bundle)
    }
}


