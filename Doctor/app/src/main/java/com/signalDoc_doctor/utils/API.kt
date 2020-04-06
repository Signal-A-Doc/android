/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.utils

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface API {
    @POST(Const.API_DOCTOR_SIGNUP)
    fun api_doctor_signup(@Body requestBody: RequestBody): Call<String>

    @GET(Const.API_GET_CATEGORIES)
    fun api_getCategoriesList(): Call<String>

    @GET(Const.API_AREA_OF_SPECILIZATION)
    fun api_getSpeciliationList(): Call<String>

    @GET(Const.API_LOGOUT)
    fun api_userLogout(): Call<String>

    @FormUrlEncoded
    @POST(Const.API_LOGIN)
    fun apiLogin(@FieldMap map: HashMap<String, Any?>?): Call<String>

    @FormUrlEncoded
    @POST(Const.API_USER_CHECK)
    fun apiUserCheck(@FieldMap params: HashMap<String, Any?>?): Call<String>

    @GET(Const.API_MY_PROFILE)
    fun apiGetProfile(): Call<String>

    @POST(Const.API_UPDATE_PROFILE)
    fun apiUpdateProfile(@Body params: RequestBody): Call<String>

    @GET(Const.API_PROFESSIONAL_STATUS_LIST)
    fun apiDocSkill(): Call<String>

    @GET(Const.API_GET_ALL_LANGUAGE_LIST)
    fun apiGetAllLanguage(@Query("page") page: Int): Call<String>

    @GET(Const.API_GET_COMMUNICATION_MODE_LIST)
    fun getAllModes(@Query("page") id: Int): Call<String>

    @FormUrlEncoded
    @POST(Const.API_SAVING_DOCTOR_LIST)
    fun saveModes(@FieldMap id: HashMap<String, Any?>?): Call<String>

    @FormUrlEncoded
    @POST(Const.API_SAVING_DOCTOR_DATE_LIST)
    fun apiHitDateSave(@FieldMap id: HashMap<String, Any?>?): Call<String>

    @GET(Const.API_PENDING_DOCTOR_APPOINTMENTS_LIST)
    fun apiGetPendingAppointmentList(@Query("page") page: Int): Call<String>

    @GET(Const.API_PAST_APPOITNMENT)
    fun apiGetPastAppointmentList(@Query("page") page: Int): Call<String>

    @GET(Const.API_UPCOMING_DOCTOR_APPOINTMENTS_LIST)
    fun apiGetUpcomingAppointmentList(@Query("page") page: Int): Call<String>

    @GET(Const.API_CHANGE_APPOITMENT_STATE)
    fun apiChangeState(@Query("id") id: Int, @Query("state") state: Int): Call<String>

}