package com.signalDoc_patient.viewModel.commonViewModel.HomeScreenViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.signalDoc_patient.model.CategoriesData
import com.signalDoc_patient.model.ProfileData
import com.signalDoc_patient.model.Symptom
import com.signalDoc_patient.utils.Const
import com.signalDoc_patient.viewModel.commonViewModel.BaseViewModel
import com.toxsl.restfulClient.api.Api3Params
import org.json.JSONException
import org.json.JSONObject

class HomeViewModel : BaseViewModel() {
    private var mutableDoctorsList = MutableLiveData<ArrayList<ProfileData>>().apply { value = ArrayList() }
    private var doctorsData = ArrayList<ProfileData>()
    private var mutableMedicalDoctorsList = MutableLiveData<ArrayList<ProfileData>>().apply { value = ArrayList() }
    private var medicalDoctorsData = ArrayList<ProfileData>()
    private var departmentsMutableList = MutableLiveData<ArrayList<Symptom>>().apply { value = ArrayList() }
    private var departmentList = ArrayList<Symptom>()
    private var catMutableList = MutableLiveData<ArrayList<CategoriesData>>().apply { value = ArrayList() }
    private var catDatas = ArrayList<CategoriesData>()
    private var pageCount = 0
    private var singleHit = false
    private var departPageCount = 0
    private var departSingleHit = false
    private var catPageCount = 0
    private var catSingleHit = false


    fun apiGetDoctorsList(): LiveData<ArrayList<ProfileData>> {
        resetDoctorsList()
        hitApiDoctorsList()
        return mutableDoctorsList

    }

    fun apiGetMedicalDoctorsList(categoryId: Int): LiveData<ArrayList<ProfileData>> {
        resetMedicalDoctorsList()
        hitApiMedicalDoctorsList(categoryId)
        return mutableMedicalDoctorsList

    }

    private fun resetDoctorsList() {
        pageCount = 0
        singleHit = false
        doctorsData.clear()

    }

    private fun resetMedicalDoctorsList() {
        pageCount = 0
        singleHit = false
        medicalDoctorsData.clear()

    }

    fun apiGetDepartmentList(): LiveData<ArrayList<Symptom>> {
        resetDepartmentList()
        hitApiGetDepartmentList()
        return departmentsMutableList
    }

    fun apiGetCategories(): LiveData<ArrayList<CategoriesData>> {
        resetCategoriesList()
        hitApiGetCategories()
        return catMutableList
    }


    private fun hitApiGetCategories() {
        if (!catSingleHit) {
            catSingleHit = true
            val call = api.api_getCategoriesList()
            restFullClient.sendRequest(call, this)
        }


    }

    private fun hitApiGetDepartmentList() {
        if (!departSingleHit) {
            departSingleHit = true
            val call = api.apiGetDepartmentList(departPageCount)
            restFullClient.sendRequest(call, this)
        }

    }

    private fun resetDepartmentList() {
        departPageCount = 0
        departSingleHit = false
        departmentList.clear()

    }

    private fun resetCategoriesList() {
        catPageCount = 0
        catSingleHit = false
        catDatas.clear()

    }

    private fun hitApiDoctorsList() {
        if (!singleHit) {
            singleHit = true
            val call = api.apiGetDoctorsList(pageCount)
            restFullClient.sendRequest(call, this)
        }

    }

    private fun hitApiMedicalDoctorsList(categoryId: Int) {
        if (!singleHit) {
            singleHit = true
            val call = api.apiGetMedicalProfessioanlDoctorsList(categoryId, pageCount)
            restFullClient.sendRequest(call, this)
        }

    }

    override fun onSyncSuccess(responseCode: Int, responseMessage: String, responseUrl: String, response: String?) {
        super.onSyncSuccess(responseCode, responseMessage, responseUrl, response)
        try {
            when {
                responseUrl.equals(Const.API_GET_DOCTOR_LIST) -> {
                    val jsonObject = JSONObject(response!!)
                    pageCount++
                    val totalPage = jsonObject.getJSONObject("_meta").getInt("pageCount")
                    if (totalPage > pageCount) {
                        singleHit = false
                    }
                    val list = Gson().fromJson<ArrayList<ProfileData>>(jsonObject.getJSONArray("list").toString(), object : TypeToken<ArrayList<ProfileData>>() {}.type)
                    doctorsData.addAll(list)
                    mutableDoctorsList.value = doctorsData
                }
                responseUrl.equals(Const.API_MEDICAL_PROFESSIONALS_DOCTORS_LIST) -> {
                    val jsonObject = JSONObject(response!!)
                    pageCount++
                    val totalPage = jsonObject.getJSONObject("_meta").getInt("pageCount")
                    if (totalPage > pageCount) {
                        singleHit = false
                    }
                    val list = Gson().fromJson<ArrayList<ProfileData>>(jsonObject.getJSONArray("list").toString(), object : TypeToken<ArrayList<ProfileData>>() {}.type)
                    medicalDoctorsData.addAll(list)
                    mutableMedicalDoctorsList.value = medicalDoctorsData
                }
                responseUrl.equals(Const.API_GET_DEPARTMENT_LIST) -> {
                    val jsonObject = JSONObject(response!!)
                    departPageCount++
                    val totalPage = jsonObject.getJSONObject("_meta").getInt("pageCount")
                    if (totalPage > departPageCount) {
                        departSingleHit = false
                    }
                    val list = Gson().fromJson<ArrayList<Symptom>>(jsonObject.getJSONArray("list").toString(), object : TypeToken<ArrayList<Symptom>>() {}.type)
                    departmentList.addAll(list)
                    departmentsMutableList.value = departmentList
                }
                responseUrl.equals(Const.API_GET_CATEGORIES) -> {
                    val jsonObject = JSONObject(response!!)
                    catPageCount++
                    val totalPage = jsonObject.getJSONObject("_meta").getInt("pageCount")
                    if (totalPage > catPageCount) {
                        catSingleHit = false
                    }
                    val list = Gson().fromJson<ArrayList<CategoriesData>>(jsonObject.getJSONArray("list").toString(), object : TypeToken<ArrayList<CategoriesData>>() {}.type)
                    catDatas.addAll(list)
                    catMutableList.value = catDatas
                }

                responseUrl.equals(Const.API_FAV_DOCTOR) || responseUrl.equals(Const.API_RATE_DOCTOR) -> {
                    callBackInterface.onSuccess(responseCode, responseMessage, responseUrl, JSONObject(response!!))
                }
            }
        } catch (e: JSONException) {
            baseActivity.printJsonException(e)
        }
    }

    fun hitFavDoctorApi(id: Int) {
        val call = api.apiFavDoctor(id)
        restFullClient.sendRequest(call, this)


    }

    fun hitSubmitRatingApi(id: Int, rating: Float) {
        val params = Api3Params()
        params.put("Rating[rating]", rating)
        val call = api.apiRateDoctor(id, params.getServerHashMap())
        restFullClient.sendRequest(call, this)


    }
}