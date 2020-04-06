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
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders


import com.signalDoc_doctor.R
import com.signalDoc_doctor.databinding.FragmentBankDetailBinding
import com.signalDoc_doctor.ui.extensions.replaceFragment
import com.signalDoc_doctor.ui.fragment.BaseFragment
import com.signalDoc_doctor.ui.fragment.SignupLoginPhase.CongratulationFragment
import com.signalDoc_doctor.ui.fragment.SignupLoginPhase.LoginFragment
import com.signalDoc_doctor.utils.ClickHandler
import com.signalDoc_doctor.viewModel.patientViewModel.SignupViewModel


class BankDetailFragment : BaseFragment(), ClickHandler {
    private var binding: FragmentBankDetailBinding? = null
    private lateinit var viewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[SignupViewModel::class.java]
        viewModel.setData(baseActivity!!, restFullClient!!, api!!, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bank_detail, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()

    }

    private fun initUi() {
        binding!!.handleClick = this
        viewModel.setSpanbleText(binding!!.termsOfTV, binding!!.alreadyHaveTV)

    }

    override fun onHandleClick(vararg data: Any) {
        if (data.isNotEmpty()) {
            val view = data[0] as View
            when (view.id) {
                R.id.loginBT -> {
                    baseActivity!!.replaceFragment(CongratulationFragment(), R.id.login_frame)

                }
                R.id.alreadyHaveTV -> {
                    baseActivity!!.replaceFragment(LoginFragment(), R.id.login_frame)
                }
            }
        }
    }


}
