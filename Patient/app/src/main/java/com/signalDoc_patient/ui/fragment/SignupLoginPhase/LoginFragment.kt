package com.signalDoc_patient.ui.fragment.SignupLoginPhase

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.signalDoc_patient.BuildConfig
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.DialogRatingBinding
import com.signalDoc_patient.databinding.FragmentLoginBinding
import com.signalDoc_patient.model.ProfileData
import com.signalDoc_patient.ui.activity.LoginSignUpActivity
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.extensions.checkString
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.utils.Const
import com.signalDoc_patient.viewModel.LoginSignupViewModel.SignupViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONException
import org.json.JSONObject

class LoginFragment : BaseFragment() {
    private var binding: FragmentLoginBinding? = null
    private lateinit var signupViewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signupViewModel = ViewModelProviders.of(this)[SignupViewModel::class.java]
        signupViewModel.setData(baseActivity!!, restFullClient!!, api!!, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (baseActivity as LoginSignUpActivity).setToolbar(false)
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        if (BuildConfig.DEBUG) {
            emailET.setText("riya@signal.com")
            passwordET.setText("Admin123")
        }
        passwordET.transformationMethod = PasswordTransformationMethod()
        binding!!.loginBT.setOnClickListener(this)
        binding!!.forgetPassTV.setOnClickListener(this)
        binding!!.createAccountTV.setOnClickListener(this)
        binding!!.hideIV.setOnClickListener(this)
        binding!!.showIV.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.loginBT -> {
                if (signupViewModel.isLoginValidate(emailET, passwordET)) {
                    signupViewModel.hitLoginApi(emailET.checkString(), passwordET.checkString())
                }
            }
            R.id.forgetPassTV -> baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.under_delvelopment))
            R.id.createAccountTV -> baseActivity!!.replaceFragment(SignUpFragment(), R.id.login_frame)
            R.id.showIV -> {
                hideIV.visibility = View.VISIBLE
                showIV.visibility = View.GONE
                passwordET.transformationMethod = null
            }
            R.id.hideIV -> {
                hideIV.visibility = View.GONE
                showIV.visibility = View.VISIBLE
                passwordET.transformationMethod = PasswordTransformationMethod()
            }
        }
    }

    private fun gotoMainActivity() {
        startActivity(Intent(baseActivity, MainActivity::class.java))
        baseActivity!!.finish()
    }


    override fun onSuccess(responseCode: Int, responseMessage: String, responseUrl: String, jsonObject: JSONObject) {
        super.onSuccess(responseCode, responseMessage, responseUrl, jsonObject)
        try {
            if (responseUrl.equals(Const.API_LOGIN)) {
                if (responseCode == Const.STATUS_OK) {
                    if (jsonObject.has("message")) {
                        baseActivity!!.showSuccessToast(jsonObject.getString("message"))
                    }
                    if (jsonObject.has("user_detail")) {
                        val data = Gson().fromJson(jsonObject.getJSONObject("user_detail").toString(), ProfileData::class.java)
                        baseActivity!!.restFullClient!!.setLoginStatus(jsonObject.getString("access-token"))
                        store!!.saveProfileDataInPrefStore(data)
                        gotoMainActivity()

                    }
                }
            }

        } catch (e: JSONException) {
            baseActivity!!.printJsonException(e)
        }
    }

}