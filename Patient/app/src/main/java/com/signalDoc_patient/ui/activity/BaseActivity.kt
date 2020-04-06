/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_patient.ui.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import co.paystack.android.PaystackSdk
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.gson.Gson
import com.signalDoc_patient.BuildConfig
import com.signalDoc_patient.R
import com.signalDoc_patient.model.ProfileData
import com.signalDoc_patient.ui.snackBar.ActionClickListener
import com.signalDoc_patient.ui.snackBar.Snackbar
import com.signalDoc_patient.ui.snackBar.SnackbarManager
import com.signalDoc_patient.ui.snackBar.SnackbarType
import com.signalDoc_patient.utils.*
import com.toxsl.restfulClient.api.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.FileNotFoundException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


open class BaseActivity : AppCompatActivity(), SyncEventListener, View.OnClickListener {

    var restFullClient: RestFullClient? = null
    var api: API? = null
    var apiInstance: Retrofit? = null

    var inflater: LayoutInflater? = null
    var store: PrefStore? = null
    var permCallback: PermCallback? = null
    private var progressDialog: Dialog? = null
    private var txtMsgTV: TextView? = null
    private var reqCode: Int = 0
    private var networksBroadcast: NetworksBroadcast? = null
    private val networkAlertDialog: AlertDialog? = null
    private var networkStatus: String? = null
    private var inputMethodManager: InputMethodManager? = null
    private var failureDailog: android.app.AlertDialog.Builder? = null
    private var failureAlertDialog: android.app.AlertDialog? = null
    private var exit: Boolean = false

    val uniqueDeviceId: String
        @SuppressLint("HardwareIds")
        get() = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

    val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager
                    .activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PaystackSdk.initialize(getApplicationContext());
        inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        restFullClient = RestFullClient.getInstance(this)
        apiInstance = restFullClient!!.getRetrofitInstance(Const.SERVER_REMOTE_URL)
        api = apiInstance!!.create(API::class.java)

        this@BaseActivity.overridePendingTransition(R.anim.slide_in,
                R.anim.slide_out)
        inputMethodManager = this
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        store = PrefStore(this)
        initializeNetworkBroadcast()

        strictModeThread()
        transitionSlideInHorizontal()
        progressDialog()
        failureDailog = android.app.AlertDialog.Builder(this)
    }


    fun initFCM() {
        if (BuildConfig.DEBUG) {
            val refreshedToken = uniqueDeviceId
            hitCheckApi(refreshedToken)
        } else {
            if (checkPlayServices()) {
                val refreshedToken = uniqueDeviceId
                hitCheckApi(refreshedToken)
            }
        }
    }

    private fun hitCheckApi(refreshedToken: String) {
        if (restFullClient!!.getLoginStatus() != null) {
            val params = Api3Params()
            params.put("DeviceDetail[device_token]", refreshedToken)
            params.put("DeviceDetail[device_type]", Const.DEVICE_TYPE)
            params.put("DeviceDetail[device_name]", android.os.Build.MODEL)
            val call = api!!.apiUserCheck(params.getServerHashMap())
            restFullClient!!.sendRequest(call, this)
        } else {
            gotoLoginSignUpActivity()
        }


    }

    fun gotoLoginSignUpActivity() {
        startActivity(Intent(this, LoginSignUpActivity::class.java))
        finish()
    }


    private fun initializeNetworkBroadcast() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
        networksBroadcast = NetworksBroadcast()
        try {
            registerReceiver(networksBroadcast, intentFilter)
        } catch (e: Exception) {
            printException(e)
        }

    }


    private fun unregisterNetworkBroadcast() {
        try {
            if (networksBroadcast != null) {
                unregisterReceiver(networksBroadcast)
            }
        } catch (e: IllegalArgumentException) {
            networksBroadcast = null
        }

    }

    private fun showNoNetworkDialog(status: String?) {
        networkStatus = status
        if (SnackbarManager.currentSnackbar != null) {
            SnackbarManager.currentSnackbar!!.dismiss()
        }
        SnackbarManager.create(
                Snackbar.with(this)
                        .type(SnackbarType.SINGLE_LINE)
                        .text(status!!).duration(com.signalDoc_patient.ui.snackBar.Snackbar
                                .SnackbarDuration.LENGTH_INDEFINITE)
                        .actionLabel(getString(R.string.retry_caps))
                        .actionListener(object : ActionClickListener {
                            override fun onActionClicked(snackbar: com.signalDoc_patient.ui.snackBar.Snackbar) {
                                if (!isNetworkAvailable) {
                                    showNoNetworkDialog(networkStatus)
                                } else
                                    SnackbarManager.currentSnackbar!!.dismiss()
                            }
                        }))!!.show()
    }

    fun changeDateFormat(dateString: String?, sourceDateFormat: String, targetDateFormat: String): String {
        if (dateString == null || dateString.isEmpty()) {
            return ""
        }
        val inputDateFromat = SimpleDateFormat(sourceDateFormat, Locale.getDefault())
        var date = Date()
        try {
            date = inputDateFromat.parse(dateString)
        } catch (e: ParseException) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        }

        val outputDateFormat = SimpleDateFormat(targetDateFormat, Locale.getDefault())
        return outputDateFormat.format(date)
    }

    fun changeDateFormatFromDate(sourceDate: Date?, targetDateFormat: String?): String {
        if (sourceDate == null || targetDateFormat == null || targetDateFormat.isEmpty()) {
            return ""
        }
        val outputDateFormat = SimpleDateFormat(targetDateFormat, Locale.getDefault())
        return outputDateFormat.format(sourceDate)
    }

    protected fun checkDate(checkDate: String) {
        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        var serverDate: Date? = null
        try {
            serverDate = dateFormat.parse(checkDate)
            cal.time = serverDate
            val currentcal = Calendar.getInstance()
            if (currentcal.after(cal)) {
                val builder = androidx.appcompat.app.AlertDialog.Builder(this)
                builder.setMessage(getString(R.string.contact_company_info))
                builder.setTitle(getString(R.string.demo_expired))
                builder.setCancelable(false)
                builder.setNegativeButton(getString(R.string.close)) { _, _ -> exitFromApp() }
                val alert = builder.create()
                alert.show()
            }
        } catch (e: ParseException) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        }

    }

    private fun checkPlayServices(): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = apiAvailability.isGooglePlayServicesAvailable(this)
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, Const.PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show()
            } else {
                log(getString(R.string.this_device_is_not_supported))
                finish()
            }
            return false
        }
        return true
    }

    fun checkPermissions(perms: Array<String>, requestCode: Int, permCallback: PermCallback): Boolean {
        this.permCallback = permCallback
        this.reqCode = requestCode
        val permsArray = ArrayList<String>()
        var hasPerms = true
        for (perm in perms) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                permsArray.add(perm)
                hasPerms = false
            }
        }
        if (!hasPerms) {
            val permsString = arrayOfNulls<String>(permsArray.size)
            for (i in permsArray.indices) {
                permsString[i] = permsArray[i]
            }
            ActivityCompat.requestPermissions(this@BaseActivity, permsString, Const.PERMISSION_ID)
            return false
        } else {
            return true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        var permGrantedBool = false
        when (requestCode) {
            Const.PERMISSION_ID -> {
                for (grantResult in grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        showToast(getString(R.string.not_sufficient_permissions)
                                + getString(R.string.app_name)
                                + getString(R.string.permissionss))
                        permGrantedBool = false
                        break
                    } else {
                        permGrantedBool = true
                    }
                }
                if (permCallback != null) {
                    if (permGrantedBool) {
                        permCallback!!.permGranted(reqCode)
                    } else {
                        permCallback!!.permDenied(reqCode)
                    }
                }
            }
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun exitFromApp() {
        finish()
        finishAffinity()
    }

    fun hideSoftKeyboard(): Boolean {
        try {
            if (currentFocus != null) {
                inputMethodManager!!.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
                return true
            }
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        }

        return false
    }

    fun isValidMail(email: String): Boolean {
        return email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex())
    }

    fun isValidPassword(password: String): Boolean {
        return password.matches("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=\\S+$).{2,}$".toRegex())
    }

    fun keyHash() {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                if (BuildConfig.DEBUG) {
                    Log.e("KeyHash:>>>>>>>>>>>>>>>", "" + Base64.encodeToString(md.digest(), Base64.DEFAULT))
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        } catch (e: NoSuchAlgorithmException) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        }

    }

    fun log(string: String) {
        if (BuildConfig.DEBUG) {
            Log.e("BaseActivity", string)
        }
    }


    fun log(title: String, message: String?) {
        if (BuildConfig.DEBUG) {
            Log.e(title, message ?: "")
        }
    }

    private fun progressDialog() {
        progressDialog = Dialog(this)
        val view = View.inflate(this, R.layout.progress_dialog, null)
        progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog!!.setContentView(view)
        progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        txtMsgTV = view.findViewById<View>(R.id.txtMsgTV) as TextView
        progressDialog!!.setCancelable(false)
    }

    fun startProgressDialog() {
        if (progressDialog != null && !progressDialog!!.isShowing) {
            try {
                progressDialog!!.show()
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace()
                }
            }

        }
    }

    fun stopProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            try {
                progressDialog!!.dismiss()
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace()
                }
            }

        }
    }

    override fun onSyncStart() {
        startProgressDialog()
    }

    override fun onSyncFinish() {
        stopProgressDialog()
    }

    open fun errorSnackBar(errorString: String, call: Call<String>?, callBack: Callback<String>?): SnackbarManager? {
        val buttontext: String
        buttontext = if (call != null && callBack != null) {
            getString(R.string.retry_cap)
        } else {
            getString(R.string.exit_caps)
        }
        val snackBar: Snackbar = Snackbar.with(this)
                .type(SnackbarType.MULTI_LINE)
                .text(errorString)
                .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                .actionLabel(buttontext)
                .actionListener(object : ActionClickListener {
                    override fun onActionClicked(snackbar: Snackbar) {
                        if (call != null && callBack != null) {
                            onSyncStart()
                            call.clone().enqueue(callBack)
                        } else {
                            finish()
                        }
                    }

                })
        return SnackbarManager.create(snackBar)
    }

    override fun onSyncFailure(errorCode: Int, t: Throwable?, response: Response<String>?, call: Call<String>?, callBack: Callback<String>?) {
        log("error_message", if (response != null) response.message() else "")
        log("error_code", errorCode.toString())
        if (this.isFinishing) return
        if (failureAlertDialog != null && failureAlertDialog!!.isShowing) {
            failureAlertDialog!!.dismiss()
        }
        if (errorCode == HTTPS_RESPONSE_CODE.FORBIDDEN_ERROR || errorCode == HTTPS_RESPONSE_CODE.UN_AUTHORIZATION) {
            log(getString(R.string.error), getString(R.string.session_timeout_redirecting))
            showToast(getString(R.string.session_timeout_redirecting))
            restFullClient!!.setLoginStatus(null)
            gotoLoginSignUpActivity()
            //--------------------------------goToLogin--------------------------
        } else if (errorCode == HTTPS_RESPONSE_CODE.SERVER_ERROR) {
            showToast(getString(R.string.problem_connecting_to_the_server))
        }else if (t is SocketTimeoutException || t is ConnectException) {
            log(getString(R.string.error), getString(R.string.request_timeout_slow_connection))
            errorSnackBar(getString(R.string.request_timeout_slow_connection), call, callBack)!!.show()
        } else if (t is AppInMaintenance) {
            log(getString(R.string.error), getString(R.string.api_is_in_maintenance))
            failureErrorDialog(t.message!!, call, callBack)!!.show()
        } else if (t is AppExpiredError) {
            log(getString(R.string.error), getString(R.string.api_is_expired))
            checkDate(t.message!!)
        } else {
            log(getString(R.string.error), if (response != null) response.message() else if (t != null) t.message else "")
            var message = getString(R.string.problem_connecting_to_the_server)
            try {
                val json = JSONObject(response?.body()
                        ?: response?.errorBody()?.string() ?: "{'message':'$message'}")
                if (json.has("message")) message = json.getString("message") else if (json.has("error")) message = json.getString("error")
            } catch (e: java.lang.Exception) {
                handelException(e)
            }
            showErrorToast(message)
        }

    }


    private fun failureErrorDialog(errorString: String, call: Call<String>?, callBack: Callback<String>?): android.app.AlertDialog? {
        if (call != null && callBack != null) {
            failureDailog!!.setMessage(errorString).setCancelable(false).setNegativeButton(getString(R.string.exit_caps)) { dialog, which -> finish() }.setPositiveButton(getString(R.string.retry_cap)) { dialog, which ->
                onSyncStart()
                call.clone().enqueue(callBack)
            }
        } else failureDailog!!.setMessage(errorString).setCancelable(false).setPositiveButton(getString(R.string.exit_caps)) { dialog, which -> finish() }
        failureAlertDialog = failureDailog!!.create()
        return failureAlertDialog
    }

    override fun onSyncSuccess(responseCode: Int, responseMessage: String, responseUrl: String, response: String?) {
        try {
            if (responseUrl.equals(Const.API_USER_CHECK)) {
                if (responseCode == Const.STATUS_OK) {
                    val jsonObject = JSONObject(response!!)
                    if (jsonObject.has("detail")) {
                        val data = Gson().fromJson(jsonObject.getJSONObject("detail").toString(), ProfileData::class.java)
                        store!!.saveProfileDataInPrefStore(data)
                        gotoMainActivity()
                    }

                } else {
                    restFullClient!!.setLoginStatus(null)
                    gotoLoginSignUpActivity()
                }

            }

        } catch (e: JSONException) {
            printJsonException(e)
        }
    }

    fun showToast(msg: String) {
        SnackbarManager.create(
                Snackbar.with(this).duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                        .type(SnackbarType.MULTI_LINE)
                        .text(msg))!!.show()
    }

    fun showToastOne(msg: String) {
        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_SHORT

        val inflater = applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.custom_toast, null)
        val titleTV = view.findViewById(R.id.titleTV) as TextView
        titleTV.text = msg
        toast.view = view
        toast.show()

    }


    fun showSuccessToast(msg: String) {
        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_SHORT

        val inflater = applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.success_custom_toast, null)
        val titleTV = view.findViewById(R.id.successToastTV) as TextView
        titleTV.text = msg
        toast.view = view
        toast.show()

    }


    fun showErrorToast(msg: String) {
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.error_custom_toast, null)
        val errorToastTV = view.findViewById(R.id.errorToastTV) as TextView
        val topLL = view.findViewById(R.id.topLL) as LinearLayout
        topLL.visibility = View.VISIBLE
        errorToastTV.text = msg
        val builder = AlertDialog.Builder(this, R.style.toastDialog)
        builder.setView(view)
        val dialog = builder.create()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull<Window>(dialog.window).setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } else {
            if (dialog.window != null) {
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }

        dialog.show()

        val handler = Handler()
        handler.postDelayed({
            dialog.dismiss()
        }, Const.TOAST_TIMEOUT.toLong())

    }


    fun loadUriImage(baseActivity: BaseActivity, uri: Uri, errorImage: Int, imageView: ImageView) {
        try {
            Glide.with(baseActivity).load(uri).apply(RequestOptions().placeholder(errorImage).error(errorImage)).into(imageView)

        } catch (e: FileNotFoundException) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        }
    }


    private fun strictModeThread() {
        val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    private fun transitionSlideInHorizontal() {
        this.overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left)
    }

    override fun onClick(v: View) {

    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterNetworkBroadcast()
    }

    fun backAction() {
        if (exit) {
            finishAffinity()
        } else {
            showToastOne(getString(R.string.press_one_more_time))
            exit = true
            Handler().postDelayed({ exit = false }, (2 * 1000).toLong())
        }
    }

    interface PermCallback {
        fun permGranted(resultCode: Int)

        fun permDenied(resultCode: Int)
    }

    inner class NetworksBroadcast : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val status = NetworkUtil.getConnectivityStatusString(context)
            if (status == null && networkAlertDialog != null) {
                networkStatus = null
                networkAlertDialog.dismiss()
            } else if (status != null && networkStatus == null)
                showNoNetworkDialog(status)
        }
    }

    open fun handelException(e: java.lang.Exception) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        checkDate(Const.DATE_CHECK)
    }


    fun printJsonException(e: JSONException) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace()
        }
    }

    fun printException(e: Exception) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace()
        }
    }

    fun gotoMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun loadImage(baseActivity: BaseActivity, profileFile: String, errorImage: Int, imageView: ImageView) {
        try {
            if (profileFile.isNotEmpty()) {
                Glide.with(baseActivity).load(profileFile).apply(RequestOptions().placeholder(errorImage).error(errorImage)).into(imageView)
            } else {
                imageView.setImageResource(errorImage)
            }
        } catch (e: FileNotFoundException) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        }
    }

    fun getDateFromUTCToLocal(sendTime: String, sourceDateFormat: String, targetDateFormat: String): String? {
        var ourDate: String? = null
        try {
            val formatter = SimpleDateFormat(sourceDateFormat)
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val value = formatter.parse(sendTime)
            val dateFormatter = SimpleDateFormat(targetDateFormat) //this format changeable
            dateFormatter.timeZone = TimeZone.getDefault()
            ourDate = dateFormatter.format(value!!)
        } catch (e: java.lang.Exception) {
            ourDate = null
        }
        return ourDate
    }
}

