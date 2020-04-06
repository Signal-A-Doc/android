/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.ui.fragment.WelcomePhase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.FragmentIntroductionBinding
import com.signalDoc_doctor.ui.activity.LoginSignUpActivity
import com.signalDoc_doctor.ui.adapter.ViewPagerAdapter
import com.signalDoc_doctor.ui.fragment.BaseFragment

class IntroductionFragment : BaseFragment() {
    private var binding: FragmentIntroductionBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        (baseActivity as LoginSignUpActivity).setToolbar(false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_introduction, container, false)
        return binding!!.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        setupViewPager(binding!!.viewPager)

    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(FirstInfroFragment(), "")
        adapter.addFragment(SecondIntroFragment(), "")
        adapter.addFragment(ThirdIntroFragment(), "")
        adapter.addFragment(FourthIntroFragment(), "")
        viewPager.adapter = adapter
    }
}