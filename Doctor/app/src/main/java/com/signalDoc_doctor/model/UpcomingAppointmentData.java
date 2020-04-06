package com.signalDoc_doctor.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpcomingAppointmentData implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("doctor_id")
    @Expose
    private Integer doctorId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("health_issue")
    @Expose
    private String healthIssue;
    @SerializedName("reschedule_reason")
    @Expose
    private Object rescheduleReason;
    @SerializedName("cancel_reason")
    @Expose
    private Object cancelReason;
    @SerializedName("documents")
    @Expose
    private List<Object> documents = null;
    @SerializedName("price")
    @Expose
    private Object price;
    @SerializedName("age")
    @Expose
    private Object age;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("date")
    @Expose
    private Object date;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("final_price")
    @Expose
    private Object finalPrice;
    @SerializedName("time")
    @Expose
    private Object time;
    @SerializedName("type_id")
    @Expose
    private Object typeId;
    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("created_by")
    @Expose
    private CreatedBy createdBy;
    @SerializedName("doctor")
    @Expose
    private Doctor doctor;
    @SerializedName("is_prior")
    @Expose
    private Boolean isPrior;
    @SerializedName("is_cancel")
    @Expose
    private Boolean isCancel;
    @SerializedName("prescription_image")
    @Expose
    private String prescriptionImage;
    @SerializedName("availabilityTime")
    @Expose
    private AvailabilityTime availabilityTime;
    @SerializedName("availabilityMode")
    @Expose
    private AvailabilityMode availabilityMode;
    @SerializedName("appointmentillnesses")
    @Expose
    private List<Object> appointmentillnesses = null;
    public final static Parcelable.Creator<UpcomingAppointmentData> CREATOR = new Creator<UpcomingAppointmentData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UpcomingAppointmentData createFromParcel(Parcel in) {
            return new UpcomingAppointmentData(in);
        }

        public UpcomingAppointmentData[] newArray(int size) {
            return (new UpcomingAppointmentData[size]);
        }

    };

    protected UpcomingAppointmentData(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.doctorId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.healthIssue = ((String) in.readValue((String.class.getClassLoader())));
        this.rescheduleReason = ((Object) in.readValue((Object.class.getClassLoader())));
        this.cancelReason = ((Object) in.readValue((Object.class.getClassLoader())));
        in.readList(this.documents, (java.lang.Object.class.getClassLoader()));
        this.price = ((Object) in.readValue((Object.class.getClassLoader())));
        this.age = ((Object) in.readValue((Object.class.getClassLoader())));
        this.gender = ((Object) in.readValue((Object.class.getClassLoader())));
        this.date = ((Object) in.readValue((Object.class.getClassLoader())));
        this.discount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.finalPrice = ((Object) in.readValue((Object.class.getClassLoader())));
        this.time = ((Object) in.readValue((Object.class.getClassLoader())));
        this.typeId = ((Object) in.readValue((Object.class.getClassLoader())));
        this.stateId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.createdOn = ((String) in.readValue((String.class.getClassLoader())));
        this.createdBy = ((CreatedBy) in.readValue((CreatedBy.class.getClassLoader())));
        this.doctor = ((Doctor) in.readValue((Doctor.class.getClassLoader())));
        this.isPrior = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.isCancel = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.prescriptionImage = ((String) in.readValue((String.class.getClassLoader())));
        this.availabilityTime = ((AvailabilityTime) in.readValue((AvailabilityTime.class.getClassLoader())));
        this.availabilityMode = ((AvailabilityMode) in.readValue((AvailabilityMode.class.getClassLoader())));
        in.readList(this.appointmentillnesses, (java.lang.Object.class.getClassLoader()));
    }

    public UpcomingAppointmentData() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHealthIssue() {
        return healthIssue;
    }

    public void setHealthIssue(String healthIssue) {
        this.healthIssue = healthIssue;
    }

    public Object getRescheduleReason() {
        return rescheduleReason;
    }

    public void setRescheduleReason(Object rescheduleReason) {
        this.rescheduleReason = rescheduleReason;
    }

    public Object getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(Object cancelReason) {
        this.cancelReason = cancelReason;
    }

    public List<Object> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Object> documents) {
        this.documents = documents;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

    public Object getAge() {
        return age;
    }

    public void setAge(Object age) {
        this.age = age;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Object getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Object finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
    }

    public Object getTypeId() {
        return typeId;
    }

    public void setTypeId(Object typeId) {
        this.typeId = typeId;
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

    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Boolean getIsPrior() {
        return isPrior;
    }

    public void setIsPrior(Boolean isPrior) {
        this.isPrior = isPrior;
    }

    public Boolean getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(Boolean isCancel) {
        this.isCancel = isCancel;
    }

    public String getPrescriptionImage() {
        return prescriptionImage;
    }

    public void setPrescriptionImage(String prescriptionImage) {
        this.prescriptionImage = prescriptionImage;
    }

    public AvailabilityTime getAvailabilityTime() {
        return availabilityTime;
    }

    public void setAvailabilityTime(AvailabilityTime availabilityTime) {
        this.availabilityTime = availabilityTime;
    }

    public AvailabilityMode getAvailabilityMode() {
        return availabilityMode;
    }

    public void setAvailabilityMode(AvailabilityMode availabilityMode) {
        this.availabilityMode = availabilityMode;
    }

    public List<Object> getAppointmentillnesses() {
        return appointmentillnesses;
    }

    public void setAppointmentillnesses(List<Object> appointmentillnesses) {
        this.appointmentillnesses = appointmentillnesses;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(doctorId);
        dest.writeValue(name);
        dest.writeValue(healthIssue);
        dest.writeValue(rescheduleReason);
        dest.writeValue(cancelReason);
        dest.writeList(documents);
        dest.writeValue(price);
        dest.writeValue(age);
        dest.writeValue(gender);
        dest.writeValue(date);
        dest.writeValue(discount);
        dest.writeValue(finalPrice);
        dest.writeValue(time);
        dest.writeValue(typeId);
        dest.writeValue(stateId);
        dest.writeValue(createdOn);
        dest.writeValue(createdBy);
        dest.writeValue(doctor);
        dest.writeValue(isPrior);
        dest.writeValue(isCancel);
        dest.writeValue(prescriptionImage);
        dest.writeValue(availabilityTime);
        dest.writeValue(availabilityMode);
        dest.writeList(appointmentillnesses);
    }

    public int describeContents() {
        return 0;
    }

}