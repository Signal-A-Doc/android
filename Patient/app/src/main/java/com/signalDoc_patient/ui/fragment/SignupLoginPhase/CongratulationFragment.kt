package com.signalDoc_patient.ui.fragment.SignupLoginPhase

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentCongratulationBinding
import com.signalDoc_patient.ui.activity.LoginSignUpActivity
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.BaseFragment

class CongratulationFragment : BaseFragment() {
    private var binding: FragmentCongratulationBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as LoginSignUpActivity).setToolbar(false)
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_congratulation, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.startedBT.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.startedBT -> baseActivity!!.replaceFragment(LoginFragment(), R.id.login_frame)
        }
    }

    private fun gotoMainActivity() {
        startActivity(Intent(baseActivity, MainActivity::class.java))
        baseActivity!!.finish()
    }

}