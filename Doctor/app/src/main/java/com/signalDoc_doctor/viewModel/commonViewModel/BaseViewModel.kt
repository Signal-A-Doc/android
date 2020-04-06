/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.meetingmaven.app.viewmodel.BaseViewModel

import androidx.lifecycle.ViewModel
import com.signalDoc_doctor.ui.activity.BaseActivity
import com.signalDoc_doctor.utils.API
import com.toxsl.restfulClient.api.RestFullClient
import com.toxsl.restfulClient.api.SyncEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseViewModel : ViewModel(), SyncEventListener {
    lateinit var baseActivity: BaseActivity
    lateinit var callBackInterface: CallBackInterface
    lateinit var restFullClient: RestFullClient
    lateinit var api: API

    fun setData(baseActivity: BaseActivity, restFullClient: RestFullClient, api: API, callBackInterface: CallBackInterface) {
        this.baseActivity = baseActivity
        this.restFullClient = restFullClient
        this.api = api
        this.callBackInterface = callBackInterface

    }

    override fun onSyncSuccess(responseCode: Int, responseMessage: String, responseUrl: String, response: String?) {

    }

    override fun onSyncFailure(errorCode: Int, t: Throwable?, response: Response<String>?, call: Call<String>?, callBack: Callback<String>?) {
        baseActivity.onSyncFailure(errorCode, t, response, call, callBack)
    }

    override fun onSyncStart() {
        baseActivity.onSyncStart()
    }

    override fun onSyncFinish() {
        baseActivity.onSyncFinish()

    }
}
