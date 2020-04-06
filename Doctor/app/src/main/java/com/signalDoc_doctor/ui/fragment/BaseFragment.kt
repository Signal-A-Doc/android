/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import com.meetingmaven.app.viewmodel.BaseViewModel.CallBackInterface
import com.toxsl.restfulClient.api.RestFullClient
import com.toxsl.restfulClient.api.SyncEventListener
import com.signalDoc_doctor.ui.activity.BaseActivity
import com.signalDoc_doctor.utils.API
import com.signalDoc_doctor.utils.Const
import com.signalDoc_doctor.utils.PrefStore
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import toxsl.imagebottompicker.ImageBottomPicker


open class BaseFragment : Fragment(), AdapterView.OnItemClickListener, View.OnClickListener, SyncEventListener, AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener
        , ImageBottomPicker.OnImageSelectedListener, ImageBottomPicker.OnErrorListener, CallBackInterface {


    var baseActivity: BaseActivity? = null
    var store: PrefStore? = null
    var restFullClient: RestFullClient? = null
    var api: API? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivity = activity as BaseActivity?
        if (baseActivity!!.restFullClient == null) {
            baseActivity!!.restFullClient = RestFullClient.getInstance(baseActivity!!)
            baseActivity!!.apiInstance = baseActivity!!.restFullClient!!.getRetrofitInstance(Const.SERVER_REMOTE_URL)
            baseActivity!!.api = baseActivity!!.apiInstance!!.create(API::class.java)
        }
        restFullClient = baseActivity!!.restFullClient
        api = baseActivity!!.api
        store = baseActivity!!.store

    }

    override fun onResume() {
        super.onResume()
        baseActivity!!.hideSoftKeyboard()
        activity!!.invalidateOptionsMenu()
    }

    override fun onClick(v: View) {

    }

    fun showToast(msg: String) {
        baseActivity!!.showToast(msg)
    }



    fun showErrorToastOne(s: String) {
        baseActivity!!.showErrorToast(s)
    }

    fun showSuccessToastOne(s: String) {
        baseActivity!!.showSuccessToast(s)
    }

    override fun onSyncStart() {
        baseActivity!!.onSyncStart()
    }

    override fun onSyncFinish() {
        baseActivity!!.onSyncFinish()
    }

    override fun onSyncFailure(errorCode: Int, t: Throwable?, response: Response<String>?, call: Call<String>?, callBack: Callback<String>?) {
        baseActivity!!.onSyncFailure(errorCode, t, response, call, callBack)
    }

    fun log(s: String) {
        baseActivity!!.log(s)
    }

    override fun onSyncSuccess(responseCode: Int, responseMessage: String, responseUrl: String, response: String?) {
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {

    }

    fun chooseImagePicker(requestCode: Int, showRemove: Boolean) {
        val bottomPicker = ImageBottomPicker.Builder(baseActivity!!, requestCode)
                .setOnImageSelectedListener(this).setOnErrorListener(this).showRemoved(showRemove)
                .create()
        bottomPicker.show(baseActivity?.supportFragmentManager)
    }

    fun chooseImagePicker(requestCode: Int, showRemove: Boolean, showGallery: Boolean, showCamera: Boolean) {
        val bottomPicker = ImageBottomPicker.Builder(baseActivity!!, requestCode)
                .setOnImageSelectedListener(this).setOnErrorListener(this).showRemoved(showRemove)
                .showGalleryTile(showGallery)
                .showCameraTile(showCamera)
                .setCrop(true)
                .create()
        bottomPicker.show(baseActivity?.supportFragmentManager)
    }

    override fun onImageSelected(uri: Uri?, requestCode: Int) {

    }

    override fun onImageRemoved(isRemoved: Boolean, requestCode: Int) {
    }

    override fun onError(message: String?) {
    }

    override fun onSuccess(responseCode: Int, responseMessage: String, responseUrl: String, jsonObject: JSONObject) {
    }
}
