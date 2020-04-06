package com.signalDoc_patient.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class AvailableModeData :Parcelable {
    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("title")
    @Expose
    var title: String = ""
    @SerializedName("image_file")
    @Expose
    var image_file: String = ""
}