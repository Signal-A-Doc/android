/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package mykotlintest.app.ui.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import co.paystack.android.Transaction
import co.paystack.android.model.Charge
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.signalDoc_patient.BuildConfig
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentAppointmentInfoFourBinding
import com.signalDoc_patient.model.ConsulatitionData
import com.signalDoc_patient.model.HealthProfileData
import com.signalDoc_patient.model.ProfileData
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.adapter.BaseAdapter
import com.signalDoc_patient.ui.adapter.DoctorConsulateAdapter
import com.signalDoc_patient.ui.extensions.*
import com.signalDoc_patient.ui.fragment.BaseFragment
import com.signalDoc_patient.ui.fragment.HealthProfileFragment
import com.signalDoc_patient.ui.fragment.patient.AppointmentBookedFragment
import com.signalDoc_patient.utils.Const
import com.signalDoc_patient.utils.payStack.CallBackPayment
import com.signalDoc_patient.utils.payStack.PaymentGateway
import com.toxsl.restfulClient.api.Api3Params
import org.json.JSONObject
import java.lang.Exception


class AppointmentInfoFourFragment : BaseFragment(), BaseAdapter.OnItemClickListener, CallBackPayment.PaymentDeduct {
    private var binding: FragmentAppointmentInfoFourBinding? = null
    private var data: HealthProfileData? = null
    private var isOpenFromTop = true

    private lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var sheetConfirmBehavior: BottomSheetBehavior<ConstraintLayout>
    private var arrayList = ArrayList<ConsulatitionData>()
    private var isSingleHit = false
    private var pageCount = 0
    private var modeId = ""
    private var paymentGateway: PaymentGateway? = null
    private var adapter: DoctorConsulateAdapter? = null
    private var profileData: ProfileData? = null
    private var timeSlotsId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable("data")
            isOpenFromTop = it.getBoolean(Const.IS_OPEN_FROM_TOP_BUTTON)
            profileData = it.getParcelable(Const.PROFILE_DATA)
            timeSlotsId = it.getString(Const.SELECTED_TIME_SLOTS)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (baseActivity as MainActivity).setToolbar(false, baseActivity!!.getString(R.string.appointment_info), false)

        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appointment_info_four, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paymentGateway = PaymentGateway()

        binding!!.armET.setOnClickListener(this)
        binding!!.armLL.setOnClickListener(this)
        initUI()
        if(!isOpenFromTop) {
            binding!!.comfirmBottom.nameTV.setText(getString(R.string.dr) + " " + profileData?.fullName)

            bottomSheet()
            confirmBottom()
            binding!!.bottom.crossIV.setOnClickListener(this)

            binding!!.bottom.communicationModeRV.layoutManager = LinearLayoutManager(baseActivity!!)

            binding!!.comfirmBottom.backIV.setOnClickListener(this)
            binding!!.comfirmBottom.cardCV.setOnClickListener(this)
            binding!!.comfirmBottom.closeConfirmIV.setOnClickListener(this)
            binding!!.comfirmBottom.confirmPaymentBT.setOnClickListener(this)
            getAllAvailableModes()
            apiGetDoctorList()
        }
    }

    private fun initUI() {
        binding!!.nextBT.setOnClickListener(this)
        binding!!.skipTV.setOnClickListener(this)

    }

    fun getAllAvailableModes() {
        if (!isSingleHit) {
            val call = api!!.apiGetModeAvail(profileData!!.id, pageCount)
            restFullClient!!.sendRequest(call, this)
            isSingleHit = true
        }
    }

    private fun apiGetDoctorList() {
        val call = api!!.apiGetDoctorDateList(profileData!!.id)
        restFullClient!!.sendRequest(call, this)
    }

    private fun bottomSheet() {
        sheetBehavior = BottomSheetBehavior.from<LinearLayout>(binding!!.bottom.consultationBottomCL)

        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {


            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (BuildConfig.DEBUG) {
                    Log.e("onStateChanged", "onStateChanged:$newState")

                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (BuildConfig.DEBUG) {
                    Log.e("onSlide", "onSlide")
                }
            }
        })

    }

    private fun expandCloseSheet() {
        if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }


    private fun expandCloseSheetConfirm() {
        if (sheetConfirmBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            sheetConfirmBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            sheetConfirmBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun confirmBottom() {
        sheetConfirmBehavior = BottomSheetBehavior.from<ConstraintLayout>(binding!!.comfirmBottom.confirmBottom)

        sheetConfirmBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {


            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (BuildConfig.DEBUG) {
                    Log.e("onStateChanged", "onStateChanged:$newState")
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (BuildConfig.DEBUG) {
                    Log.e("onSlide", "onSlide")
                }
            }
        })

    }


    override fun onClick(v: View) {
        when (v.id) {

            R.id.continueBT -> {
                baseActivity!!.hideSoftKeyboard()
                expandCloseSheet()
            }

            R.id.crossIV -> {
                baseActivity!!.hideSoftKeyboard()
                expandCloseSheet()
            }

            R.id.callTV -> {
                expandCloseSheetConfirm()


            }
            R.id.closeConfirmIV -> {
                expandCloseSheetConfirm()
            }

            R.id.messageTV -> {
                expandCloseSheetConfirm()
            }

            R.id.backIV -> {
                expandCloseSheetConfirm()
                expandCloseSheet()
            }

            R.id.armET, R.id.armLL -> {
                baseActivity!!.showErrorToast("Under development!!")
            }
            R.id.nextBT -> {
                if (isValidate()) {
                    if (isOpenFromTop) {
                        gotoNextFrag()
                    } else {
                        expandCloseSheet()
                    }
                }

            }
            R.id.skipTV -> {
                if (isOpenFromTop) {
                    gotoNextFrag()
                } else {
                    expandCloseSheet()
                }
            }

            R.id.cardCV, R.id.confirmPaymentBT -> {
                bookAppointment()
            }
        }
    }

    private fun bookAppointment() {
        val card = paymentGateway!!.cardObject("4084084084084081", 11, 22, "408")
        if (card != null) {
            val chargeObject = Charge()
            chargeObject.setCard(card)
            chargeObject.setEmail(baseActivity!!.store!!.getProfileDataFromPrefStore()!!.email)
            chargeObject.setAmount(1)
            paymentGateway!!.deductAmount(baseActivity!!, chargeObject, this)
            apiBookAppointment()
        } else {
            baseActivity!!.showErrorToast(getString(R.string.enter_valid_card))
        }
    }

    private fun isValidate(): Boolean {
        when {
            binding!!.tempET.isBlank() -> baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.pls_enter_temp))
            binding!!.armET.isBlank() -> baseActivity!!.showErrorToast(baseActivity!!.getString(R.string.pls_enter_temp_location))
            else -> {
                return true
            }
        }
        return false

    }

    private fun gotoNextFrag() {
        val bundle = Bundle()
        data!!.temperature = binding!!.tempET.checkString()
        data!!.tempLocation = binding!!.armET.checkString()
        bundle.putParcelable("data", data)
        baseActivity!!.replaceFragWithArgs(HealthProfileFragment(), R.id.frame_container, bundle)
    }

    override fun onItemClick(vararg itemData: Any) {
        val id = itemData[0] as Int
        when (id) {
            Const.OPEN_PAYMENT -> {
                val pos = itemData[1] as Int
                binding!!.comfirmBottom.callTV.setText(arrayList[pos].title)
                modeId = arrayList[pos].id.toString()
                expandCloseSheet()
                expandCloseSheetConfirm()
            }
        }
    }

    override fun onSyncSuccess(responseCode: Int, responseMessage: String, responseUrl: String, response: String?) {
        super.onSyncSuccess(responseCode, responseMessage, responseUrl, response)
        try {
            val jsonObject = JSONObject(response!!)
            when (responseUrl) {
                Const.API_GET_AVAILABILITY_MODE_LIST -> {
                    if (responseCode == Const.STATUS_OK) {
                        pageCount++
                        val totalCount = jsonObject.getJSONObject("_meta").getInt("pageCount")
                        isSingleHit = pageCount >= totalCount

                        val jsonArray = jsonObject.getJSONArray("list")
                        for (i in 0 until jsonArray.length()) {
                            val object1 = jsonArray.getJSONObject(i)
                            val object2 = object1.getJSONObject("consultation")
                            val data = Gson().fromJson<ConsulatitionData>(object2.toString(), ConsulatitionData::class.java)
                            arrayList.add(data)
                        }
                        setAdapter()


                    }
                }
                Const.API_BOOK_APPOINTMENT_LIST -> {
                    if (responseCode == Const.STATUS_OK) {
                        baseActivity!!.replaceFragment(AppointmentBookedFragment(), R.id.frame_container)
                    }
                }
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private fun setAdapter() {
        if (adapter == null) {
            adapter = DoctorConsulateAdapter(arrayList, this)
            adapter!!.setOnItemClickListener(this)
            binding!!.bottom.communicationModeRV.adapter = adapter
        } else {
            adapter!!.notifyDataSetChanged()
        }
    }

    private fun apiBookAppointment() {
        val param = Api3Params()
        param.put("Appointment[doctor_id]", profileData!!.id)
        param.put("Appointment[availability_id]", modeId)//mode id

        param.put("Appointment[availability_time_id]", timeSlotsId!!)//slot id

        val call = api!!.apiBookAppointment(param.getServerHashMap())
        restFullClient!!.sendRequest(call, this)
    }

    override fun onSuccess(transaction: Transaction?) {
       // baseActivity!!.showErrorToast("onSuccess to precess your transaction : " + transaction!!.reference)
        baseActivity!!.log("onSuccess to precess your transaction : " + transaction!!.reference)
    }

    override fun beforeValidate(transaction: Transaction?) {
      //  baseActivity!!.showErrorToast("beforeValidate to precess your transaction")
    }

    override fun onError(error: Throwable?, transaction: Transaction?) {
       // baseActivity!!.showErrorToast("Unable to precess your transaction")
    }

}
