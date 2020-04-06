package com.signalDoc_patient.viewModel.BookingViewModell

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.signalDoc_patient.model.AppoitnmemtsListData
import com.signalDoc_patient.model.ProfileData
import com.signalDoc_patient.ui.extensions.handleJSonException
import com.signalDoc_patient.utils.Const
import com.signalDoc_patient.viewModel.commonViewModel.BaseViewModel
import org.json.JSONException
import org.json.JSONObject

class BookingModel : BaseViewModel() {
    private var pageCount = 0
    private var singleHit = false
    private var datas = ArrayList<AppoitnmemtsListData>()
    private var mutableAppoitnmetsList = MutableLiveData<ArrayList<AppoitnmemtsListData>>().apply { value = ArrayList() }
    private var pastPageCount = 0
    private var pastSingleHit = false
    private var pastDatas = ArrayList<AppoitnmemtsListData>()
    private var pastMutableAppoitnmetsList = MutableLiveData<ArrayList<AppoitnmemtsListData>>().apply { value = ArrayList() }


    fun apiUpcomingAppoitnmnetsList(): LiveData<ArrayList<AppoitnmemtsListData>> {
        resetAppoitnmnetsList()
        getUpcomingAppoitnments()
        return mutableAppoitnmetsList

    }

    fun apipastAppoitnmnetsList(): LiveData<ArrayList<AppoitnmemtsListData>> {
        resetPastAppoitnmnetsList()
        getPastAppoitnments()
        return pastMutableAppoitnmetsList

    }

    fun resetPastAppoitnmnetsList() {
        pastPageCount = 0
        pastSingleHit = false
        pastDatas.clear()
    }
    fun resetAppoitnmnetsList() {
        pageCount = 0
        singleHit = false
        datas.clear()
    }

    fun getUpcomingAppoitnments() {
        if (!singleHit) {
            singleHit = true
            val call = api.apiUpcomingAppoitnmnetsList(pageCount)
            restFullClient.sendRequest(call, this)
        }
    }

    fun getPastAppoitnments() {
        if (!pastSingleHit) {
            pastSingleHit = true
            val call = api.apiPastAppoitnmnetsList(pastPageCount)
            restFullClient.sendRequest(call, this)
        }
    }

    override fun onSyncSuccess(responseCode: Int, responseMessage: String, responseUrl: String, response: String?) {
        super.onSyncSuccess(responseCode, responseMessage, responseUrl, response)
        try {
            when {
                responseUrl.equals(Const.API_UPCOMING_APPOINTMENT_LIST) -> {
                    val jsonObject = JSONObject(response!!)
                    pageCount++
                    val totalPage = jsonObject.getJSONObject("_meta").getInt("pageCount")
                    if (totalPage > pageCount) {
                        singleHit = false
                    }
                    val list = Gson().fromJson<ArrayList<AppoitnmemtsListData>>(jsonObject.getJSONArray("list").toString(), object : TypeToken<ArrayList<AppoitnmemtsListData>>() {}.type)
                    datas.addAll(list)
                    mutableAppoitnmetsList.value = datas
                }
                responseUrl.equals(Const.API_PAST_APPOINTMENT_LIST) -> {
                    val jsonObject = JSONObject(response!!)
                    pastPageCount++
                    val totalPage = jsonObject.getJSONObject("_meta").getInt("pageCount")
                    if (totalPage > pastPageCount) {
                        pastSingleHit = false
                    }
                    val list = Gson().fromJson<ArrayList<AppoitnmemtsListData>>(jsonObject.getJSONArray("list").toString(), object : TypeToken<ArrayList<AppoitnmemtsListData>>() {}.type)
                    pastDatas.addAll(list)
                    pastMutableAppoitnmetsList.value = pastDatas
                }
            }

        } catch (e: JSONException) {
            handleJSonException(e)
        }
    }
}