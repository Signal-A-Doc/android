package com.signalDoc_patient.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class ReviewHealthData : Parcelable {
    @SerializedName("medicine_name")
    @Expose
    var medicine_name: String = ""
    @SerializedName("how_long")
    @Expose
    var how_long: String = ""
    var drugList=""
}