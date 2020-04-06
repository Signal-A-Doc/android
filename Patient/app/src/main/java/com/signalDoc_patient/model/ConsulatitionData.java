package com.signalDoc_patient.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsulatitionData implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image_file")
    @Expose
    private String imageFile;
    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("type_id")
    @Expose
    private Integer typeId;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("updated_on")
    @Expose
    private String updatedOn;
    @SerializedName("created_by_id")
    @Expose
    private Integer createdById;
    public final static Parcelable.Creator<ConsulatitionData> CREATOR = new Creator<ConsulatitionData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ConsulatitionData createFromParcel(Parcel in) {
            return new ConsulatitionData(in);
        }

        public ConsulatitionData[] newArray(int size) {
            return (new ConsulatitionData[size]);
        }

    };

    protected ConsulatitionData(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.imageFile = ((String) in.readValue((String.class.getClassLoader())));
        this.stateId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.typeId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.createdOn = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedOn = ((String) in.readValue((String.class.getClassLoader())));
        this.createdById = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public ConsulatitionData() {
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

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
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

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Integer getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Integer createdById) {
        this.createdById = createdById;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(imageFile);
        dest.writeValue(stateId);
        dest.writeValue(typeId);
        dest.writeValue(createdOn);
        dest.writeValue(updatedOn);
        dest.writeValue(createdById);
    }

    public int describeContents() {
        return 0;
    }

}