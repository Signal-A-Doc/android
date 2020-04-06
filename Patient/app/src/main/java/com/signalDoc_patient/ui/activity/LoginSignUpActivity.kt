/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_patient.ui.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentManager
import com.signalDoc_patient.R
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.CardPhase.AddCardFragment
import com.signalDoc_patient.ui.fragment.ForgotPasswordFragment
import com.signalDoc_patient.ui.fragment.SignupLoginPhase.LoginFragment
import com.signalDoc_patient.ui.fragment.SignupLoginPhase.SignUpFragment
import com.signalDoc_patient.ui.fragment.WelcomePhase.IntroductionFragment
import kotlinx.android.synthetic.main.activity_login.*


class LoginSignUpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setToolbar()
        initUi()
        gotoIntroductionFragment()
    }

    private fun initUi() {
        backIV.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.backIV -> {
                val fragment = supportFragmentManager.findFragmentById(R.id.login_frame)
                if (fragment is AddCardFragment || fragment is SignUpFragment) {
                    gotoLoginFragment()
                } else if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                } else {
                    gotoLoginFragment()
                }
            }
        }
    }

    private fun gotoIntroductionFragment() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        replaceFragment(IntroductionFragment(), R.id.login_frame)
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }
    }

    private fun gotoLoginFragment() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        replaceFragment(LoginFragment(), R.id.login_frame)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


    fun setToolbar(isShow: Boolean) {
        if (isShow) {
            toolbar.visibility = View.VISIBLE
        } else {
            toolbar.visibility = View.GONE
        }


    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.login_frame)
        if (fragment is LoginFragment || fragment is IntroductionFragment ) {
            backAction()
        } else if (fragment is AddCardFragment || fragment is SignUpFragment || fragment is ForgotPasswordFragment) {
            gotoLoginFragment()
        } else if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            gotoLoginFragment()
        }
    }
}