/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_patient.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Parcelize
class WorkInformation : Parcelable {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("professional_status")
    @Expose
    var professional_status: Int = 0
    @SerializedName("professionalStatus")
    @Expose
    var professionalStatus: String = ""
    @SerializedName("professional_indemnity")
    @Expose
    var professionalIndemnity: String = ""
    @SerializedName("MDCN_folio_number")
    @Expose
    var mDCNFolioNumber: Int = 0

    @SerializedName("registeration_number")
    @Expose
    var registerationNumber: String = ""
    @SerializedName("issued_date")
    @Expose
    var issuedDate: String? = null
    @SerializedName("expiry_date")
    @Expose
    var expiryDate: String? = null
    @SerializedName("profile_summary")
    @Expose
    var profileSummary: String = ""
    @SerializedName("state_id")
    @Expose
    var stateId: Int? = null
    @SerializedName("type_id")
    @Expose
    var typeId: Any? = null
    @SerializedName("created_on")
    @Expose
    var createdOn: String? = null

    @SerializedName("professional_indemnity_certificate")
    @Expose
    var professional_indemnity_certificate: String? = null
    @SerializedName("MDCN_certificate")
    @Expose
    var MDCN_certificate: String? = null
    @SerializedName("created_by_id")
    @Expose
    var createdById: Int? = null
}