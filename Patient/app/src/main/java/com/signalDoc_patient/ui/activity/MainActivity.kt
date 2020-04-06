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
import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.Fragment
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.signalDoc_patient.BuildConfig
import com.signalDoc_patient.R
import com.signalDoc_patient.ui.adapter.DrawerAdapter
import com.signalDoc_patient.model.DrawerData
import com.signalDoc_patient.ui.extensions.replaceFragment
import com.signalDoc_patient.ui.fragment.*
import com.signalDoc_patient.utils.PrefStore
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.signalDoc_patient.ui.extensions.replaceFragWithArgs
import com.signalDoc_patient.ui.fragment.AppointmentsFragment
import com.signalDoc_patient.utils.Const
import org.json.JSONException


class MainActivity : BaseActivity(), BaseActivity.PermCallback, BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeM -> {
                replaceFragment(HomeScreenFragment(), R.id.frame_container)


            }
            R.id.appointmentsM -> {
                //  showErrorToast(getString(R.string.under_delvelopment))
                replaceFragment(AppointmentsFragment(), R.id.frame_container)
            }

            R.id.professionalM -> {
                replaceFragment(MedicalProfessionFragment(), R.id.frame_container)

            }

            R.id.accountM -> {
                replaceFragment(MyAccountFragment(), R.id.frame_container)

            }
        }
        return true
    }


    private var drawer: DrawerLayout? = null
    internal var seekerDrawerListener: AdapterView.OnItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
        val frag: Fragment? = null
        when (position) {
            0 -> {
                showErrorToast(getString(R.string.under_delvelopment))

            }
            1 -> {
                showErrorToast(getString(R.string.under_delvelopment))
            }
            2 -> {
                showErrorToast(getString(R.string.under_delvelopment))
            }
            3 -> {
                showErrorToast(getString(R.string.under_delvelopment))

            }
            4 -> {

                showErrorToast(getString(R.string.under_delvelopment))
            }
            5 -> {

                showErrorToast(getString(R.string.under_delvelopment))
            }
            6 -> {
                showErrorToast(getString(R.string.under_delvelopment))

            }
            7 -> {
                showErrorToast(getString(R.string.under_delvelopment))

            }
            8 -> {
                showErrorToast(getString(R.string.under_delvelopment))

            }

        }
        if (frag != null)
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, frag)
                    .commit()
        drawer!!.closeDrawers()
    }
    private var headLL: ConstraintLayout? = null
    private var profilePicCIV: ImageView? = null
    private var nameTV: TextView? = null
    private var drawerLV: ListView? = null
    private var toolbar: Toolbar? = null
    private var logoIV: AppCompatImageView? = null
    private var titleTV: AppCompatTextView? = null
    private var toggle: ActionBarDrawerToggle? = null
    private val drawerItems = ArrayList<DrawerData>()
    private var drawerAdapter: DrawerAdapter? = null
    private var drawerDatas = ArrayList<DrawerData>()
    private var prefStore: PrefStore? = null

    private var navBar: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        init()
        initDrawer()

        gotoHomeFragment()
    }

    fun gotoHomeFragment() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        navBar!!.selectedItemId = R.id.homeM
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, HomeScreenFragment()).commitAllowingStateLoss()

    }

    private fun initToolbar() {
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        logoIV = findViewById<View>(R.id.logoIV) as AppCompatImageView
        titleTV = findViewById<View>(R.id.titleTV) as AppCompatTextView
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

    }

    fun setToolbar(showIcon: Boolean, title: String?, showNavigation: Boolean, isShowToolbar: Boolean = true) {
        if (showIcon && title!!.isEmpty()) {
            logoIV!!.visibility = View.VISIBLE
            titleTV!!.visibility = View.GONE
        } else {
            logoIV!!.visibility = View.GONE
            titleTV!!.visibility = View.VISIBLE
            titleTV!!.text = title
        }
        if (showNavigation == true) {
            navBNV!!.visibility = View.VISIBLE
        } else {
            navBNV!!.visibility = View.GONE
        }

        if (isShowToolbar) {
            toolbar!!.visibility = View.VISIBLE
        } else {
            toolbar!!.visibility = View.GONE
        }

    }

    private fun init() {
        navBar = findViewById<View>(R.id.navBNV) as BottomNavigationView
        navBar!!.setOnNavigationItemSelectedListener(this)
        navBNV.isItemHorizontalTranslationEnabled = false
        drawer = findViewById<View>(R.id.drawer) as DrawerLayout

        headLL = findViewById<View>(R.id.headLL) as ConstraintLayout
        profilePicCIV = findViewById<View>(R.id.profilePicCIV) as ImageView
        nameTV = findViewById<View>(R.id.nameTV) as TextView
        headLL!!.requestLayout()
        headLL!!.setOnClickListener(this)
        drawerLV = findViewById<View>(R.id.drawerLV) as ListView

        toggle = object : ActionBarDrawerToggle(this, drawer, null,
                R.string.app_name, R.string.app_name) {

            @SuppressLint("RestrictedApi")
            override fun onDrawerOpened(drawerView: View) {
                invalidateOptionsMenu()
            }

            @SuppressLint("RestrictedApi")
            override fun onDrawerClosed(drawerView: View) {
                invalidateOptionsMenu()
            }
        }
        drawer!!.addDrawerListener(toggle!!)
        updateDrawer()

        profilePicCIV!!.setOnClickListener(this)
        viewTV!!.setOnClickListener(this)
        nameTV!!.setOnClickListener(this)
        closeIV!!.setOnClickListener(this)
        signOutTV!!.setOnClickListener(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle!!.syncState()
    }

    fun updateDrawer() {
        if (restFullClient!!.getLoginStatus() != null && store?.getProfileDataFromPrefStore() != null) {
            if (store?.getProfileDataFromPrefStore()!!.profileFile!!.isNotEmpty()) {
                Glide.with(this).load(store?.getProfileDataFromPrefStore()?.profileFile).apply(RequestOptions().error(R.mipmap.ic_default_profile).placeholder(R.mipmap.ic_default_profile)).into(profilePicCIV!!)
            } else {
                profilePicCIV!!.setImageResource(R.mipmap.ic_default_profile)
            }
            nameTV!!.text = store!!.getProfileDataFromPrefStore()!!.fullName

        }
    }


    private fun initDrawer() {
        val icons = resources.obtainTypedArray(R.array.drawer_icons)
        val names = resources.getStringArray(R.array.drawer_items)
        for (i in names.indices) {
            val drawerData = DrawerData()
            drawerData.icon = icons.getResourceId(i, -1)
            drawerData.name = names[i]
            drawerItems.add(drawerData)
        }
        drawerAdapter = DrawerAdapter(this, 0, drawerItems)
        drawerLV!!.adapter = drawerAdapter
        drawerLV!!.onItemClickListener = seekerDrawerListener
    }

    override fun permGranted(resultCode: Int) {
    }

    override fun permDenied(resultCode: Int) {

    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.profilePicCIV -> {
                gotoAccountScreen()

            }
            R.id.nameTV -> {
                gotoAccountScreen()

            }

            R.id.viewTV -> {
                gotoAccountScreen()

            }
            R.id.closeIV -> {
                drawer!!.closeDrawers()
            }
            R.id.signOutTV -> {
                hitSignoutApi()
            }

        }
    }

    private fun gotoAccountScreen() {
        val bundle = Bundle()
        bundle.putBoolean("showDrawer", true)
        replaceFragWithArgs(MyAccountFragment(), R.id.frame_container, bundle)
        drawer!!.closeDrawers()
    }

    private fun hitSignoutApi() {
        val call = api!!.api_userLogout()
        restFullClient!!.sendRequest(call, this)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame_container)
        val bundle = fragment!!.arguments
        var showDrawer = true
        if (bundle != null) {
            if (bundle.containsKey("showDrawer") && bundle.getBoolean("showDrawer")) {
                showDrawer = false
            }
        }

        if (fragment is HomeScreenFragment || fragment is AppointmentsFragment || fragment is MyAccountFragment && showDrawer || fragment is MedicalProfessionFragment) {
            toggle!!.setDrawerIndicatorEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.mipmap.ic_menu)
            drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        } else {
            toggle!!.setDrawerIndicatorEnabled(false)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            drawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            supportActionBar!!.setHomeAsUpIndicator(R.mipmap.ic_back_grey)
        }

        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideSoftKeyboard()
        if (toggle!!.onOptionsItemSelected(item)) {
            return true
        }
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame_container)
        if (fragment is HomeScreenFragment) {
            backAction()
        } else if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            gotoHomeFragment()
        }
    }

    fun selectTab(pos: Int) {
        try {
            if (navBar != null) {
                navBar!!.getMenu().getItem(pos).setChecked(true)
            }
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        }

    }

    override fun onSyncSuccess(responseCode: Int, responseMessage: String, responseUrl: String, response: String?) {
        super.onSyncSuccess(responseCode, responseMessage, responseUrl, response)
        try {
            if (responseUrl.equals(Const.API_LOGOUT)) {
                if (responseCode == Const.STATUS_OK) {
                    restFullClient!!.setLoginStatus(null)
                    showSuccessToast(getString(R.string.you_are_logged_out))
                    gotoLoginSignUpActivity()
                }
            }
        } catch (e: JSONException) {
            printJsonException(e)
        }
    }

}
