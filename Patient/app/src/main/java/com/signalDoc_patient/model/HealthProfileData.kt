package com.signalDoc_patient.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.json.JSONArray

@Parcelize
class HealthProfileData : Parcelable {
    var medicationsArray: JSONArray? = null
    var medicationsArrayList: ArrayList<ReviewHealthData> = arrayListOf()
    var symptomsList: ArrayList<SymptomsListData> = arrayListOf()
    var medicalConditionIds = ""
    var howLongDay = ""
    var temperature = ""
    var tempLocation = ""
    var howLongDayType = 0
    var drugList: ArrayList<String> = arrayListOf()
    var medicalConditionList: ArrayList<CategoriesData> = arrayListOf()

}