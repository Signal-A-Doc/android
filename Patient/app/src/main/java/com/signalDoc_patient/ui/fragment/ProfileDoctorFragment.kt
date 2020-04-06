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


import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.DialogRatingBinding
import com.signalDoc_patient.databinding.FragmentProfileDoctorBinding
import com.signalDoc_patient.model.AllLanguageData
import com.signalDoc_patient.model.ProfileData
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.adapter.DoctorLanguageSpokenAdapter
import com.signalDoc_patient.ui.extensions.replaceFragWithArgs
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.patient.AvailableAppointmentFragment
import com.signalDoc_patient.utils.ClickHandler
import com.signalDoc_patient.utils.Const
import com.signalDoc_patient.viewModel.commonViewModel.HomeScreenViewModel.HomeViewModel
import com.toxsl.restfulClient.api.Api3Params
import kotlinx.android.synthetic.main.fragment_profile_doctor.*
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception


class ProfileDoctorFragment : BaseFragment(), ClickHandler {
    private var binding: FragmentProfileDoctorBinding? = null
    private var profileData: ProfileData? = null
    private var title: String = ""
    private lateinit var homeViewModel: HomeViewModel
    private var alertDialog: Dialog? = null
    private var ratingBinding: DialogRatingBinding? = null
    private var adapter: DoctorLanguageSpokenAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            profileData = it.getParcelable("data")
        }
        homeViewModel = ViewModelProviders.of(this)[HomeViewModel::class.java]
        homeViewModel.setData(baseActivity!!, restFullClient!!, api!!, this)

        arguments?.let {
            title = it.getString("title")!!
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.profile), false)

        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_doctor, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        binding!!.handleClick = this
        binding!!.data = profileData
        binding!!.languageSpokenRV.layoutManager = LinearLayoutManager(baseActivity!!, RecyclerView.HORIZONTAL, false)
        setData()

    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        if (profileData != null) {
            if (title.isNotEmpty()) {
                binding!!.nameTV.text = title
            } else {
                if (profileData!!.symptoms != null && profileData!!.symptoms!!.isNotEmpty()) {
                    binding!!.nameTV.text = profileData!!.symptoms!![0].title
                } else {
                    binding!!.nameTV.text = baseActivity!!.getString(R.string.not_set)
                }
            }

            if (profileData!!.current_place_of_work != null && profileData!!.symptoms!!.isNotEmpty()) {
                binding!!.placeOfWorkTV.text = profileData!!.current_place_of_work
            } else {
                binding!!.placeOfWorkTV.text = baseActivity!!.getString(R.string.not_set)
            }
            binding!!.likeCB.isChecked = profileData!!.isLike
            binding!!.drTV.isSelected = true
            binding!!.nameTV.isSelected = true
            if (profileData!!.is_rated) {
                rateTV.text = baseActivity!!.getString(R.string.already_rated)

            } else {
                binding!!.rateTV.setText(baseActivity!!.getString(R.string.dr) + " " + profileData!!.fullName)
            }
            if (profileData!!.profileFile.isNotEmpty()) {
                baseActivity!!.loadImage(baseActivity!!, profileData!!.profileFile, R.mipmap.ic_default_profile, binding!!.imageIV)
            } else {
                binding!!.imageIV.setImageResource(R.mipmap.ic_default_profile)
            }
        }

        if (profileData?.language != null && profileData?.language?.size!! > 0) {
            binding!!.errorSetTV.visibility = View.GONE
            binding!!.languageSpokenRV.visibility = View.VISIBLE
        } else {
            binding!!.errorSetTV.visibility = View.VISIBLE
            binding!!.languageSpokenRV.visibility = View.GONE
        }
        setAdapterLanguage()

    }

    override fun onHandleClick(vararg data: Any) {
        if (data.isNotEmpty()) {
            val view = data[0] as View
            when (view.id) {
                R.id.callIV -> {
                    baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.under_delvelopment))
                }
                R.id.messageIV -> {
                    baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.under_delvelopment))
                }
                R.id.vCallIV -> {
                    baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.under_delvelopment))
                }
                R.id.likeCB -> {
                    homeViewModel.hitFavDoctorApi(profileData!!.id)
                }
                R.id.rateTV, R.id.viewV -> {
                    if (!profileData!!.is_rated) {
                        showRatingDialog()
                    }
                }
                R.id.close -> {
                    alertDialog!!.dismiss()
                }
                R.id.submitBT -> {
                    if (ratingBinding!!.ratingBar.getRating() == 0.0.toFloat()) {
                        baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.please_give_rating_doctor))
                    } else {
                        homeViewModel.hitSubmitRatingApi(profileData!!.id, ratingBinding!!.ratingBar.rating)
                    }
                }
                R.id.cardCV -> {

                    val bundle = Bundle()
                    bundle.putParcelable(Const.PROFILE_DATA, profileData)
                    baseActivity!!.replaceFragWithArgs(AvailableAppointmentFragment(), R.id.frame_container, bundle)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showRatingDialog() {
        val builder = AlertDialog.Builder(baseActivity!!)
        ratingBinding = DataBindingUtil.inflate<DialogRatingBinding>(layoutInflater,
                R.layout.dialog_rating, null, false)
        builder.setView(ratingBinding!!.root)
        if (profileData != null) {
            ratingBinding!!.nameTV.setText(baseActivity!!.getString(R.string.rate_dr) + " " + profileData!!.fullName)
        }
        ratingBinding!!.handleClick = this
        val width = (baseActivity!!.resources.displayMetrics.widthPixels * 0.85).toInt()
        alertDialog = builder.create()
        alertDialog!!.show()
        alertDialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        alertDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    override fun onSuccess(responseCode: Int, responseMessage: String, responseUrl: String, jsonObject: JSONObject) {
        super.onSuccess(responseCode, responseMessage, responseUrl, jsonObject)
        try {
            if (responseUrl.equals(Const.API_FAV_DOCTOR)) {
                if (responseCode == Const.STATUS_OK) {
                    if (jsonObject.has("message")) {
                        baseActivity!!.showToastOne(jsonObject.getString("message"))
                    }
                    if (jsonObject.has("is_liked")) {
                        profileData!!.isLike = jsonObject.getBoolean("is_liked")
                        binding!!.likeCB.isChecked = profileData!!.isLike
                    }


                }
            } else if (responseUrl.equals(Const.API_RATE_DOCTOR)) {
                if (responseCode == Const.STATUS_OK) {
                    baseActivity!!.showSuccessToast(baseActivity!!.getString(R.string.thank_you_rating_to_doctor))
                    alertDialog!!.dismiss()
                    baseActivity!!.replaceFragment(HomeScreenFragment(), R.id.frame_container)
                } else {
                    if (jsonObject.has("error")) {
                        baseActivity!!.showErrorToast(jsonObject.getString("error"))
                    }
                }
            }
        } catch (e: JSONException) {
            baseActivity!!.printJsonException(e)
        }
    }

    private fun setAdapterLanguage() {
        if (adapter == null) {
            adapter = DoctorLanguageSpokenAdapter(profileData?.language as ArrayList<AllLanguageData>)
            binding!!.languageSpokenRV.adapter = adapter
        } else {
            adapter!!.notifyDataSetChanged()
        }
    }
}
