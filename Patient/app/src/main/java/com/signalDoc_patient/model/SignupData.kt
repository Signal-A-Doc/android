/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_patient.model

import android.os.Parcelable
import com.signalDoc_patient.utils.Const
import kotlinx.android.parcel.Parcelize
import java.io.File

@Parcelize
class SignupData : Parcelable {
    var email: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var country: String = ""
    var contact_number: String = ""
    var address: String = ""
    var date_of_birth: String = ""
    var gender: Int = Const.MALE
    var blood_type: Int = Const.O_NEGATIVE
    var password: String = ""
    var profileFile: File? = null
    var weight: String = ""
    var height: String = ""
    var categoryIds: String = ""
}