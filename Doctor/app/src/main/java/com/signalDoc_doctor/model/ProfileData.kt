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
    @SerializedName("last_name")
    @Expose
    var last_name: String = ""
    @SerializedName("first_name")
    @Expose
    var first_name: String = ""
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("gender")
    @Expose
    var gender: Int? = null
    @SerializedName("age")
    @Expose
    var age: String? = null
    @SerializedName("date_of_birth")
    @Expose
    var dateOfBirth: String? = null
    @SerializedName("speciality")
    @Expose
    var speciality: Any? = null
    @SerializedName("qualification")
    @Expose
    var qualification: String? = null
    @SerializedName("specialization")
    @Expose
    var specialization: String? = null
    @SerializedName("files")
    @Expose
    var files: List<Any>? = null
    @SerializedName("about_me")
    @Expose
    var aboutMe: String? = null
    @SerializedName("contact_no")
    @Expose
    var contactNo: String? = null
    @SerializedName("city")
    @Expose
    var city: String? = null
    @SerializedName("country")
    @Expose
    var country: String? = null
    @SerializedName("address")
    @Expose
    var address: String? = null
    @SerializedName("zipcode")
    @Expose
    var zipcode: String? = null
    @SerializedName("profile_file")
    @Expose
    var profileFile: String? = null
    @SerializedName("is_favourite")
    @Expose
    var isFavourite: Boolean? = null
    @SerializedName("state_id")
    @Expose
    var stateId: Int? = null
    @SerializedName("availability")
    @Expose
    var availability: Any? = null
    @SerializedName("reviews")
    @Expose
    var reviews: List<Any>? = null
    @SerializedName("rating")
    @Expose
    var rating: Int? = null
    @SerializedName("notification_count")
    @Expose
    var notificationCount: String? = null
    @SerializedName("notification_settings")
    @Expose
    var notificationSettings: Int? = null
    @SerializedName("email_settings")
    @Expose
    var emailSettings: Int? = null
    @SerializedName("created_by_id")
    @Expose
    var createdById: Any? = null
    @SerializedName("plan_id")
    @Expose
    var planId: Int? = null
    @SerializedName("medicalInfomration")
    @Expose
    var medicalInfomration: MedicalInfomrationOld? = null
    @SerializedName("workInformation")
    @Expose
    var workInformation: WorkInformation? = null
    @SerializedName("symptoms", alternate = arrayOf("specializations"))
    @Expose
    var symptoms: List<CategoriesData>? = null
    @SerializedName("language")
    @Expose
    var language: List<AllLanguageData>? = null
}