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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.FragmentMyAccountBinding
import com.signalDoc_doctor.model.CategoriesData
import com.signalDoc_doctor.ui.activity.MainActivity
import com.signalDoc_doctor.ui.adapter.doctor.ViewPagerAdapter
import com.signalDoc_doctor.ui.fragment.BaseFragment
import com.signalDoc_doctor.utils.Const
import com.signalDoc_doctor.utils.DataAndTypeEditProfile
import com.signalDoc_doctor.utils.SpecilizationEditProfile
import kotlinx.android.synthetic.main.fragment_my_account.*
import java.util.ArrayList

class MyAccountFragment : BaseFragment(), DataAndTypeEditProfile.CallBack, SpecilizationEditProfile.CallBack {


    private var binding: FragmentMyAccountBinding? = null
    private var pagerAdapter: ViewPagerAdapter? = null
    private var profileUpdate = DataAndTypeEditProfile.getInstance()
    private var specilizationUpdate = SpecilizationEditProfile.getInstance()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.my_account))
        (baseActivity as MainActivity).selectTab(Const.ACCOUNT)
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_account, container, false)
        }
        return binding!!.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        profileUpdate.setListener(this)
        specilizationUpdate.setListener(this)
    }

    private fun initUi() {
        tabLayout.setupWithViewPager(binding!!.viewPager)
        setUpViewPager()

    }

    private fun setUpViewPager() {
        pagerAdapter = ViewPagerAdapter(childFragmentManager)
        pagerAdapter!!.addFragment(MyDetailsFragment(), baseActivity!!.getString(R.string.my_details))
        pagerAdapter!!.addFragment(WorkProfileEditFragment(), baseActivity!!.getString(R.string.work_profile))
        pagerAdapter!!.addFragment(BankDetailAccountFragment(), baseActivity!!.getString(R.string.bank_details))
        binding!!.viewPager.setAdapter(pagerAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding!!.viewPager.removeAllViews()
    }

    override fun whatData(data: String, type: Int) {
        val currentFragment = pagerAdapter?.instantiateItem(binding!!.viewPager, binding!!.viewPager.currentItem)
        if (currentFragment is MyDetailsFragment) {
            currentFragment.whatData(data, type)
        } else if (currentFragment is WorkProfileEditFragment) {
            currentFragment.whatData(data, type)
        }
    }

    override fun whatSpecilizationData(list: ArrayList<CategoriesData>?) {
        val currentFragment = pagerAdapter?.instantiateItem(binding!!.viewPager, binding!!.viewPager.currentItem)
        if (currentFragment is WorkProfileEditFragment) {
            currentFragment.whatSpecilizationData(list)
        }

    }
}
