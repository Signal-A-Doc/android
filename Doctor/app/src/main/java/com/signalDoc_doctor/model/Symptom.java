package com.signalDoc_doctor.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Symptom implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("type_id")
    @Expose
    private Object typeId;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("created_by_id")
    @Expose
    private Integer createdById;
    public final static Parcelable.Creator<Symptom> CREATOR = new Creator<Symptom>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Symptom createFromParcel(Parcel in) {
            return new Symptom(in);
        }

        public Symptom[] newArray(int size) {
            return (new Symptom[size]);
        }

    };

    protected Symptom(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.categoryId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.stateId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.typeId = ((Object) in.readValue((Object.class.getClassLoader())));
        this.createdOn = ((String) in.readValue((String.class.getClassLoader())));
        this.createdById = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Symptom() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Object getTypeId() {
        return typeId;
    }

    public void setTypeId(Object typeId) {
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
        dest.writeValue(categoryId);
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