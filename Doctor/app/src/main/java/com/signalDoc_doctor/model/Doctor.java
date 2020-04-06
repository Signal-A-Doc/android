package com.signalDoc_doctor.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Doctor implements Parcelable {

    public final static Parcelable.Creator<Doctor> CREATOR = new Creator<Doctor>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        public Doctor[] newArray(int size) {
            return (new Doctor[size]);
        }

    };

    protected Doctor(Parcel in) {
    }

    public Doctor() {
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public int describeContents() {
        return 0;
    }

}