package com.signalDoc_doctor.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllLanguageData implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("type_id")
    @Expose
    private Integer typeId;
    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    public final static Parcelable.Creator<AllLanguageData> CREATOR = new Creator<AllLanguageData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public AllLanguageData createFromParcel(Parcel in) {
            return new AllLanguageData(in);
        }

        public AllLanguageData[] newArray(int size) {
            return (new AllLanguageData[size]);
        }

    };
    public boolean isSelected = false;

    protected AllLanguageData(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.value = ((String) in.readValue((String.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.typeId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.stateId = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public AllLanguageData() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(value);
        dest.writeValue(code);
        dest.writeValue(typeId);
        dest.writeValue(stateId);
    }

    public int describeContents() {
        return 0;
    }

}