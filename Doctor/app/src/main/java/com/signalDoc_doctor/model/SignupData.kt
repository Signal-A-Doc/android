/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.model

import android.os.Parcelable
import com.signalDoc_doctor.utils.Const
import kotlinx.android.parcel.Parcelize
import java.io.File

@Parcelize
class SignupData : Parcelable {
    var email: String = ""
    var current_place_of_work: String = ""
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
    var professional_indemnity_certificate: File? = null
    var MDCN_certificate_File: File? = null
    var weight: String = ""
    var height: String = ""
    var categoryIds: String = ""

    var professional_status = ""
    var professional_indemnity = ""
    var MDCN_folio_number:Long = 0
    var MDCN_certificate = ""
    var registerationNumber = ""
    var issued_date = ""
    var expiry_date = ""
    var profile_summary = ""
    var language_selected = ""
}