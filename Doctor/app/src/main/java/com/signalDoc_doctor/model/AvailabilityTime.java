package com.signalDoc_doctor.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class AvailabilityTime implements Parcelable {

    public final static Parcelable.Creator<AvailabilityTime> CREATOR = new Creator<AvailabilityTime>() {


        @SuppressWarnings({
                "unchecked"
        })
        public AvailabilityTime createFromParcel(Parcel in) {
            return new AvailabilityTime(in);
        }

        public AvailabilityTime[] newArray(int size) {
            return (new AvailabilityTime[size]);
        }

    };

    protected AvailabilityTime(Parcel in) {
    }

    public AvailabilityTime() {
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public int describeContents() {
        return 0;
    }

}