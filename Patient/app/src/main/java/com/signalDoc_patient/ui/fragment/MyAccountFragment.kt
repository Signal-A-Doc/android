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
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentMyAccountBinding
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.adapter.ViewPagerAdapter
import com.signalDoc_patient.ui.fragment.patient.MedicalInfoFragment
import com.signalDoc_patient.utils.Const
import com.signalDoc_patient.utils.DataAndTypeEditProfile


class MyAccountFragment : BaseFragment(), DataAndTypeEditProfile.CallBack {
    private var binding: FragmentMyAccountBinding? = null
    private var profileUpdate = DataAndTypeEditProfile.getInstance()
    private var pagerAdapter: ViewPagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.my_account),true)
        (baseActivity as MainActivity).selectTab(Const.ACCOUNT)
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_account, container, false)
        }
        return binding!!.root;

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        profileUpdate.setListener(this)

    }

    private fun initUi() {
        binding!!.tabLayout.setupWithViewPager(binding!!.viewPager)
        setUpViewPager()


    }

    private fun setUpViewPager() {
        pagerAdapter = ViewPagerAdapter(childFragmentManager)
        pagerAdapter!!.addFragment(MyDetailsFragment(), baseActivity!!.getString(R.string.my_details))
        pagerAdapter!!.addFragment(MedicalInfoFragment(), baseActivity!!.getString(R.string.health_profile))
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
        } else if (currentFragment is MedicalInfoFragment) {
            currentFragment.whatData(data, type)
        }
    }

}


