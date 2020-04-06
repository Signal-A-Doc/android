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
import kotlinx.android.parcel.Parcelize
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Parcelize
class MedicalInfomration : Parcelable {
    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("blood_group_title")
    @Expose
    var blood_group_title: String = ""
    @SerializedName("blood_group")
    @Expose
    var bloodGroup: Int? = null
    @SerializedName("weight")
    @Expose
    var weight: Double = 0.0
    @SerializedName("height")
    @Expose
    var height: Double = 0.0
    @SerializedName("state_id")
    @Expose
    var stateId: Int? = null
    @SerializedName("type_id")
    @Expose
    var typeId: Int? = null
    @SerializedName("created_on")
    @Expose
    var createdOn: String? = null
    @SerializedName("allergies")
    @Expose
    var allergies: String? = null
    @SerializedName("updated_on")
    @Expose
    var updatedOn: String? = null
    @SerializedName("created_by_id")
    @Expose
    var createdById: Int? = null
}