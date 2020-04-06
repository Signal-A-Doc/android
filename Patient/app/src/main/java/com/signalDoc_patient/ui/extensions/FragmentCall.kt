/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_patient.ui.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity


fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction {
        replace(frameId, fragment).addToBackStack(null)
    }
}

fun AppCompatActivity.replaceFragmentWithoutStack(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction {
        replace(frameId, fragment)
    }
}

fun AppCompatActivity.replaceFragWithArgs(fragment: Fragment, frameId: Int, args: Bundle) {
    fragment.arguments = args
    supportFragmentManager.inTransaction {
        replace(frameId, fragment).addToBackStack(null)
    }
}

fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, backStackTag: String? = null) {
    supportFragmentManager.inTransaction {
        add(frameId, fragment)
        backStackTag?.let {
            addToBackStack(null)
        }!!
    }
}