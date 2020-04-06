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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentMedicalInfoBinding
import com.signalDoc_patient.model.ProfileData
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.utils.ClickHandler
import com.signalDoc_patient.utils.Const
import com.signalDoc_patient.viewModel.LoginSignupViewModel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_medical_info.*
import org.json.JSONException
import org.json.JSONObject


class MedicalInfoFragment : BaseFragment(), ClickHandler {


    private var binding: FragmentMedicalInfoBinding? = null
    private lateinit var profileViewModel: ProfileViewModel
    private var profileData: ProfileData? = null
    private var title: StringBuilder = StringBuilder()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel = ViewModelProviders.of(this)[ProfileViewModel::class.java]
        profileViewModel.setData(baseActivity!!, restFullClient!!, api!!, this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_medical_info, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()

    }

    private fun initUi() {
        binding!!.handleClick = this
        profileViewModel.getUserProfile()

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onSuccess(responseCode: Int, responseMessage: String, responseUrl: String, jsonObject: JSONObject) {
        super.onSuccess(responseCode, responseMessage, responseUrl, jsonObject)
        try {
            if (responseUrl.equals(Const.API_MY_PROFILE)) {
                if (responseCode == Const.STATUS_OK) {
                    if (jsonObject.has("detail")) {
                        profileData = Gson().fromJson(jsonObject.getJSONObject("detail").toString(), ProfileData::class.java)
                        setData()
                    }
                }
            } else if (responseUrl.equals(Const.API_UPDATE_PROFILE)) {
                if (responseCode == Const.STATUS_OK) {
                    if (jsonObject.has("message")) {
                        baseActivity!!.showSuccessToast(jsonObject.getString("message"))
                    }
                    if (jsonObject.has("detail")) {
                        val data = Gson().fromJson(jsonObject.getJSONObject("detail").toString(), ProfileData::class.java)
                        store!!.saveProfileDataInPrefStore(data)
                        (baseActivity as MainActivity).updateDrawer()
                    }

                }
            }

        } catch (e: JSONException) {
            baseActivity!!.printJsonException(e)

        }
    }

    override fun onHandleClick(vararg data: Any) {
        if (data.isNotEmpty()) {
            val view = data[0] as View
            when (view.id) {
                R.id.bloodTypeTV -> {
                    profileViewModel.showEditDialog(profileData!!.medicalInfomration!!.blood_group_title, Const.BLOOD_TYPE, baseActivity!!.getString(R.string.blood_type))
                }
                R.id.weightEditTV -> {
                    profileViewModel.showEditDialog(profileData!!.medicalInfomration!!.weight.toString(), Const.WEIGHT, baseActivity!!.getString(R.string.weight))

                }
                R.id.heightWeightTV -> {
                    profileViewModel.showEditDialog(profileData!!.medicalInfomration!!.height.toString(), Const.HEIGHT, baseActivity!!.getString(R.string.height))

                }
                R.id.editTV -> {
                    profileViewModel.showEditDialog("Peanuts, groundnuts, Oil", Const.ALERGIES, baseActivity!!.getString(R.string.allergies))

                }
                R.id.recentEditTV -> {
                    profileViewModel.showEditDialog(title.toString(), Const.MEDITATIONS, baseActivity!!.getString(R.string.recent_medications))

                }
                R.id.reSheduleBT -> {
                    if (isValid()) {

                        profileViewModel.updateMedication(binding!!.weightTypeTV.text.toString(), binding!!.heightTypeTV.text.toString(), binding!!.alergyTV.text.toString().trim())

                    }
                }
            }
        }

    }

    private fun setData() {
        if (profileData != null) {
            weightTypeTV.setText(profileData!!.medicalInfomration!!.weight.toString())
            heightTypeTV.setText(profileData!!.medicalInfomration!!.height.toString())
            BloodGrpPTV.setText(profileData!!.medicalInfomration!!.blood_group_title)
            if (profileData!!.symptoms != null && profileData!!.symptoms!!.size > 0) {
                for (i in 0 until profileData!!.symptoms!!.size) {
                    if (i == 0) {
                        title = StringBuilder(profileData!!.symptoms!!.get(i).title + "," + " ")
                    } else {
                        title.append(profileData!!.symptoms!!.get(i).title).append("," + " ")
                    }

                }
                if (title.length > 0) {
                    title = StringBuilder(title.substring(0, title.length - 2))
                }
                recentPTV.setText(title.toString())

            }

        }
    }

    fun whatData(data: String?, type: Int) {
        when (type) {
            Const.BLOOD_TYPE -> {
                binding!!.BloodGrpPTV.setText(data)
            }
            Const.WEIGHT -> {
                binding!!.weightTypeTV.setText(data)
            }
            Const.HEIGHT -> {
                binding!!.heightTypeTV.setText(data)
            }
            Const.ALERGIES -> {
                binding!!.medicationPTV.setText(data)
            }
            Const.MEDITATIONS -> {
                binding!!.recentPTV.setText(data)
            }
        }
    }

    private fun isValid(): Boolean {
        when {
            binding!!.BloodGrpPTV.text.toString().trim().isEmpty() -> showToastOne(getString(R.string.please_enter_blood_group))
            binding!!.weightTypeTV.text.toString().trim().isEmpty() -> showToastOne(getString(R.string.please_enter_weight))
            binding!!.heightTypeTV.text.toString().trim().isEmpty() -> showToastOne(getString(R.string.please_enter_height))
            binding!!.medicationPTV.text.toString().trim().isEmpty() -> showToastOne(getString(R.string.please_enter_allergies))
            binding!!.recentPTV.text.toString().trim().isEmpty() -> showToastOne(getString(R.string.please_enter_recent_medications))
            else -> return true
        }
        return false
    }


}
