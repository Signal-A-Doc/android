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
import kotlinx.android.parcel.Parcelize
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Parcelize
class SymptomOld : Parcelable {
    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("category_id")
    @Expose
    var categoryId: Int? = null
    @SerializedName("state_id")
    @Expose
    var stateId: Int? = null
    @SerializedName("type_id")
    @Expose
    var typeId: Any? = null
    @SerializedName("created_on")
    @Expose
    var createdOn: String? = null

    @SerializedName("title")
    @Expose
    var title: String = ""
    @SerializedName("updated_on")
    @Expose
    var updatedOn: String? = null
    @SerializedName("created_by_id")
    @Expose
    var createdById: Int? = null
}