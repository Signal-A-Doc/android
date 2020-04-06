package com.signalDoc_patient.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
class SymptomsListData :Parcelable {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("category_id")
    @Expose
    var categoryId: Int? = null
    @SerializedName("state_id")
    @Expose
    var stateId: Int? = null
    @SerializedName("type_id")
    @Expose
    var typeId: Int? = null
    @SerializedName("created_on")
    @Expose
    var createdOn: String? = null
    @SerializedName("created_by_id")
    @Expose
    var createdById: Int? = null
    @SerializedName("symptoms")
    @Expose
    var symptoms: List<Symptom>? = null
}