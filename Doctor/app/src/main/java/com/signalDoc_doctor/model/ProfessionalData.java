/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfessionalData implements Parcelable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("state_id")
    @Expose
    public Integer stateId;
    @SerializedName("type_id")
    @Expose
    public Integer typeId;
    @SerializedName("created_on")
    @Expose
    public String createdOn;
    @SerializedName("created_by_id")
    @Expose
    public Integer createdById;
    public final static Parcelable.Creator<ProfessionalData> CREATOR = new Creator<ProfessionalData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ProfessionalData createFromParcel(Parcel in) {
            return new ProfessionalData(in);
        }

        public ProfessionalData[] newArray(int size) {
            return (new ProfessionalData[size]);
        }

    };

    protected ProfessionalData(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.stateId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.typeId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.createdOn = ((String) in.readValue((String.class.getClassLoader())));
        this.createdById = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public ProfessionalData() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(stateId);
        dest.writeValue(typeId);
        dest.writeValue(createdOn);
        dest.writeValue(createdById);
    }

    public int describeContents() {
        return 0;
    }

}