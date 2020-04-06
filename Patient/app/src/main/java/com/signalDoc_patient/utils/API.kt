/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_patient.utils

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import kotlin.collections.HashMap

interface API {
    @POST(Const.API_PATIENT_SIGNUP)
    fun api_patient_signup(@Body requestBody: RequestBody): Call<String>

    @GET(Const.API_GET_CATEGORIES)
    fun api_getCategoriesList(): Call<String>

    @GET(Const.API_GET_SYMPTOMS_LIST)
    fun api_getSymptomsList(@Query("page") page: Int, @Query("id") id: Int): Call<String>

    @GET(Const.API_GET_MEDICAL_CONDITIONS)
    fun api_getMedicalConditionsList(@Query("page") page: Int): Call<String>

    @GET(Const.API_PROFESSIONAL_STATUS_LIST)
    fun api_getMedicalCategoriesList(@Query("page") page: Int): Call<String>

    @GET(Const.API_LOGOUT)
    fun api_userLogout(): Call<String>

    @FormUrlEncoded
    @POST(Const.API_LOGIN)
    fun apiLogin(@FieldMap map: HashMap<String, Any?>?): Call<String>

    @FormUrlEncoded
    @POST(Const.API_USER_CHECK)
    fun apiUserCheck(@FieldMap params: HashMap<String, Any?>?): Call<String>

    @POST(Const.API_UPDATE_PROFILE)
    fun apiUpdateProfile(@Body params: RequestBody): Call<String>

    @POST(Const.API_SAVE_HEALTH_PROFILE)
    fun apiSaveHealthProfile(@Body params: RequestBody): Call<String>

    @FormUrlEncoded
    @POST(Const.API_RATE_DOCTOR)
    fun apiRateDoctor(@Query("doctor_id") doctor_id: Int, @FieldMap params: HashMap<String, Any?>?): Call<String>


    @GET(Const.API_MY_PROFILE)
    fun apiGetProfile(): Call<String>

    @GET(Const.API_GET_DOCTOR_LIST)
    fun apiGetDoctorsList(@Query("page") page: Int): Call<String>

    @GET(Const.API_MEDICAL_PROFESSIONALS_DOCTORS_LIST)
    fun apiGetMedicalProfessioanlDoctorsList(@Query("id") category_id: Int, @Query("page") page: Int): Call<String>

    @GET(Const.API_GET_DEPARTMENT_LIST)
    fun apiGetDepartmentList(@Query("page") page: Int): Call<String>

    @GET(Const.API_FAV_DOCTOR)
    fun apiFavDoctor(@Query("doctor_id") doctor_id: Int): Call<String>

    @GET(Const.API_GET_QUESTIONS_LIST)
    fun apiGetQuestion(@Query("id") id: Int, @Query("page") page: Int): Call<String>

    @GET(Const.API_GET_AVAILABILITY_MODE_LIST)
    fun apiGetModeAvail(@Query("id") id: Int, @Query("page") page: Int): Call<String>

    @GET(Const.API_GET_DOCTOR_DATE_LIST)
    fun apiGetDoctorDateList(@Query("id") id: Int): Call<String>

    @GET(Const.API_UPCOMING_APPOINTMENT_LIST)
    fun apiUpcomingAppoitnmnetsList(@Query("page") page: Int): Call<String>

    @GET(Const.API_PAST_APPOINTMENT_LIST)
    fun apiPastAppoitnmnetsList(@Query("page") page: Int): Call<String>

    @FormUrlEncoded
    @POST(Const.API_GET_DOCTOR_TIME_SLOTS_LIST)
    fun apiGetDoctorTimeList(@Query("id") id: Int, @FieldMap serverHashMap: HashMap<String, Any?>?): Call<String>

    @FormUrlEncoded
    @POST(Const.API_BOOK_APPOINTMENT_LIST)
    fun apiBookAppointment(@FieldMap serverHashMap: HashMap<String, Any?>?): Call<String>

}