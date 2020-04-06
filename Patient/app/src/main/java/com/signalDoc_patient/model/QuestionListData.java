package com.signalDoc_patient.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionListData implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("created_by_id")
    @Expose
    private Integer createdById;
    @SerializedName("type_id")
    @Expose
    private Integer typeId;
    private String ansTitle;
    public final static Parcelable.Creator<QuestionListData> CREATOR = new Creator<QuestionListData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public QuestionListData createFromParcel(Parcel in) {
            return new QuestionListData(in);
        }

        public QuestionListData[] newArray(int size) {
            return (new QuestionListData[size]);
        }

    };

    protected QuestionListData(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.ansTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.categoryId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.stateId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.createdOn = ((String) in.readValue((String.class.getClassLoader())));
        this.createdById = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.typeId = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public QuestionListData() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnsTitle() {
        return ansTitle;
    }

    public void setAnsTitle(String ansTitle) {
        this.ansTitle = ansTitle;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(categoryId);
        dest.writeValue(stateId);
        dest.writeValue(createdOn);
        dest.writeValue(createdById);
        dest.writeValue(typeId);
    }

    public int describeContents() {
        return 0;
    }

}