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
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class ProfileData : Parcelable {

    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("full_name")
    @Expose
    var fullName: String = ""
    @SerializedName("current_place_of_work")
    @Expose
    var current_place_of_work: String? = null
    @SerializedName("last_name")
    @Expose
    var last_name: String = ""
    @SerializedName("first_name")
    @Expose
    var first_name: String = ""
    @SerializedName("email")
    @Expose
    var email: String = ""
    @SerializedName("gender")
    @Expose
    var gender: Int = 0
    @SerializedName("age")
    @Expose
    var age: String = ""
    @SerializedName("date_of_birth")
    @Expose
    var dateOfBirth: String = ""

    @SerializedName("qualification")
    @Expose
    var qualification: String = ""


    @SerializedName("contact_no")
    @Expose
    var contactNo: String = ""

    @SerializedName("country")
    @Expose
    var country: String = ""
    @SerializedName("address")
    @Expose
    var address: String = ""


    @SerializedName("profile_file")
    @Expose
    var profileFile: String = ""

    @SerializedName("is_liked")
    @Expose
    var isLike: Boolean = false

    @SerializedName("is_rated")
    @Expose
    var is_rated: Boolean = false

    @SerializedName("workInformation")
    @Expose
    var workInformation: WorkInformation? = null
    @SerializedName("medicalInfomration")
    @Expose
    var medicalInfomration: MedicalInfomration? = null
    @SerializedName("symptoms", alternate = arrayOf("specializations"))
    @Expose
    var symptoms: List<Symptom>? = null
    @SerializedName("language")
    @Expose
    var language: List<AllLanguageData>? = null
}