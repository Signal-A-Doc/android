package com.signalDoc_patient.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class AvailableTimeData :Parcelable {
    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("date")
    @Expose
    var date: String = ""
    @SerializedName("start_time")
    @Expose
    var start_time: String = ""
    @SerializedName("end_time")
    @Expose
    var end_time: String = ""
    @SerializedName("price")
    @Expose
    var price: String = ""
    @SerializedName("description")
    @Expose
    var description: String = ""
}