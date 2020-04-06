package com.signalDoc_doctor.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class IsPlan implements Parcelable {

    public final static Parcelable.Creator<IsPlan> CREATOR = new Creator<IsPlan>() {


        @SuppressWarnings({
                "unchecked"
        })
        public IsPlan createFromParcel(Parcel in) {
            return new IsPlan(in);
        }

        public IsPlan[] newArray(int size) {
            return (new IsPlan[size]);
        }

    };

    protected IsPlan(Parcel in) {
    }

    public IsPlan() {
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public int describeContents() {
        return 0;
    }

}
