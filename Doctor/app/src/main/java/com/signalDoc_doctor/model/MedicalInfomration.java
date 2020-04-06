package com.signalDoc_doctor.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicalInfomration implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("blood_group")
    @Expose
    private Integer bloodGroup;
    @SerializedName("blood_group_title")
    @Expose
    private String bloodGroupTitle;
    @SerializedName("weight")
    @Expose
    private Double weight;
    @SerializedName("height")
    @Expose
    private Double height;
    @SerializedName("allergies")
    @Expose
    private Object allergies;
    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("type_id")
    @Expose
    private Integer typeId;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("created_by_id")
    @Expose
    private Integer createdById;
    public final static Parcelable.Creator<MedicalInfomration> CREATOR = new Creator<MedicalInfomration>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MedicalInfomration createFromParcel(Parcel in) {
            return new MedicalInfomration(in);
        }

        public MedicalInfomration[] newArray(int size) {
            return (new MedicalInfomration[size]);
        }

    };

    protected MedicalInfomration(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.bloodGroup = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.bloodGroupTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.weight = ((Double) in.readValue((Double.class.getClassLoader())));
        this.height = ((Double) in.readValue((Double.class.getClassLoader())));
        this.allergies = ((Object) in.readValue((Object.class.getClassLoader())));
        this.stateId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.typeId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.createdOn = ((String) in.readValue((String.class.getClassLoader())));
        this.createdById = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public MedicalInfomration() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(Integer bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getBloodGroupTitle() {
        return bloodGroupTitle;
    }

    public void setBloodGroupTitle(String bloodGroupTitle) {
        this.bloodGroupTitle = bloodGroupTitle;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Object getAllergies() {
        return allergies;
    }

    public void setAllergies(Object allergies) {
        this.allergies = allergies;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Integer createdById) {
        this.createdById = createdById;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(bloodGroup);
        dest.writeValue(bloodGroupTitle);
        dest.writeValue(weight);
        dest.writeValue(height);
        dest.writeValue(allergies);
        dest.writeValue(stateId);
        dest.writeValue(typeId);
        dest.writeValue(createdOn);
        dest.writeValue(createdById);
    }

    public int describeContents() {
        return 0;
    }

}