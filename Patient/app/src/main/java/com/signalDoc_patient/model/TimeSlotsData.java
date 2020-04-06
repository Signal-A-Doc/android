package com.signalDoc_patient.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeSlotsData implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("doctor_availability_id")
    @Expose
    private Integer doctorAvailabilityId;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("date")
    @Expose
    private String date;

    public boolean isSelected = false;

    public final static Parcelable.Creator<TimeSlotsData> CREATOR = new Creator<TimeSlotsData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TimeSlotsData createFromParcel(Parcel in) {
            return new TimeSlotsData(in);
        }

        public TimeSlotsData[] newArray(int size) {
            return (new TimeSlotsData[size]);
        }

    };

    protected TimeSlotsData(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.doctorAvailabilityId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.startTime = ((String) in.readValue((String.class.getClassLoader())));
        this.endTime = ((String) in.readValue((String.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
    }

    public TimeSlotsData() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctorAvailabilityId() {
        return doctorAvailabilityId;
    }

    public void setDoctorAvailabilityId(Integer doctorAvailabilityId) {
        this.doctorAvailabilityId = doctorAvailabilityId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(doctorAvailabilityId);
        dest.writeValue(startTime);
        dest.writeValue(endTime);
        dest.writeValue(date);
    }

    public int describeContents() {
        return 0;
    }

}