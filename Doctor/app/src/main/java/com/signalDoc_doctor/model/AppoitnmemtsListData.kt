package com.signalDoc_patient.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.signalDoc_doctor.model.ProfileData
import kotlinx.android.parcel.Parcelize

@Parcelize
class AppoitnmemtsListData : Parcelable {
    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("state_id")
    @Expose
    var state_id: Int = 0
    @SerializedName("doctor_id")
    @Expose
    var doctor_id: Int = 0
    @SerializedName("doctor")
    @Expose
    var doctor: ProfileData? = null
    @SerializedName("availabilityTime")
    @Expose
    var availabilityTime: AvailableTimeData? = null
    @SerializedName("availabilityMode")
    @Expose
    var availabilityMode: AvailableModeData? = null

}