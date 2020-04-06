package com.signalDoc_doctor.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatedBy implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("speciality")
    @Expose
    private Object speciality;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("specialization")
    @Expose
    private String specialization;
    @SerializedName("files")
    @Expose
    private List<Object> files = null;
    @SerializedName("about_me")
    @Expose
    private String aboutMe;
    @SerializedName("contact_no")
    @Expose
    private String contactNo;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("language")
    @Expose
    private List<Object> language = null;
    @SerializedName("profile_file")
    @Expose
    private String profileFile;
    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("availability")
    @Expose
    private Object availability;
    @SerializedName("reviews")
    @Expose
    private List<Object> reviews = null;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("notification_count")
    @Expose
    private String notificationCount;
    @SerializedName("notification_settings")
    @Expose
    private Integer notificationSettings;
    @SerializedName("email_settings")
    @Expose
    private Integer emailSettings;
    @SerializedName("created_by_id")
    @Expose
    private Object createdById;
    @SerializedName("current_place_of_work")
    @Expose
    private Object currentPlaceOfWork;
    @SerializedName("plan_id")
    @Expose
    private Integer planId;
    @SerializedName("is_plan")
    @Expose
    private IsPlan isPlan;
    @SerializedName("medicalInfomration")
    @Expose
    private MedicalInfomration medicalInfomration;
    @SerializedName("health_profile_step")
    @Expose
    private Object healthProfileStep;
    @SerializedName("symptoms")
    @Expose
    private List<Symptom> symptoms = null;
    public final static Parcelable.Creator<CreatedBy> CREATOR = new Creator<CreatedBy>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CreatedBy createFromParcel(Parcel in) {
            return new CreatedBy(in);
        }

        public CreatedBy[] newArray(int size) {
            return (new CreatedBy[size]);
        }

    };

    protected CreatedBy(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.fullName = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.gender = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.age = ((String) in.readValue((String.class.getClassLoader())));
        this.dateOfBirth = ((String) in.readValue((String.class.getClassLoader())));
        this.speciality = ((Object) in.readValue((Object.class.getClassLoader())));
        this.qualification = ((String) in.readValue((String.class.getClassLoader())));
        this.specialization = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.files, (java.lang.Object.class.getClassLoader()));
        this.aboutMe = ((String) in.readValue((String.class.getClassLoader())));
        this.contactNo = ((String) in.readValue((String.class.getClassLoader())));
        this.city = ((String) in.readValue((String.class.getClassLoader())));
        this.country = ((String) in.readValue((String.class.getClassLoader())));
        this.address = ((String) in.readValue((String.class.getClassLoader())));
        this.zipcode = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.language, (java.lang.Object.class.getClassLoader()));
        this.profileFile = ((String) in.readValue((String.class.getClassLoader())));
        this.stateId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.availability = ((Object) in.readValue((Object.class.getClassLoader())));
        in.readList(this.reviews, (java.lang.Object.class.getClassLoader()));
        this.rating = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.notificationCount = ((String) in.readValue((String.class.getClassLoader())));
        this.notificationSettings = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.emailSettings = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.createdById = ((Object) in.readValue((Object.class.getClassLoader())));
        this.currentPlaceOfWork = ((Object) in.readValue((Object.class.getClassLoader())));
        this.planId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.isPlan = ((IsPlan) in.readValue((IsPlan.class.getClassLoader())));
        this.medicalInfomration = ((MedicalInfomration) in.readValue((MedicalInfomration.class.getClassLoader())));
        this.healthProfileStep = ((Object) in.readValue((Object.class.getClassLoader())));
        in.readList(this.symptoms, (Symptom.class.getClassLoader()));
    }

    public CreatedBy() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Object getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Object speciality) {
        this.speciality = speciality;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<Object> getFiles() {
        return files;
    }

    public void setFiles(List<Object> files) {
        this.files = files;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public List<Object> getLanguage() {
        return language;
    }

    public void setLanguage(List<Object> language) {
        this.language = language;
    }

    public String getProfileFile() {
        return profileFile;
    }

    public void setProfileFile(String profileFile) {
        this.profileFile = profileFile;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Object getAvailability() {
        return availability;
    }

    public void setAvailability(Object availability) {
        this.availability = availability;
    }

    public List<Object> getReviews() {
        return reviews;
    }

    public void setReviews(List<Object> reviews) {
        this.reviews = reviews;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(String notificationCount) {
        this.notificationCount = notificationCount;
    }

    public Integer getNotificationSettings() {
        return notificationSettings;
    }

    public void setNotificationSettings(Integer notificationSettings) {
        this.notificationSettings = notificationSettings;
    }

    public Integer getEmailSettings() {
        return emailSettings;
    }

    public void setEmailSettings(Integer emailSettings) {
        this.emailSettings = emailSettings;
    }

    public Object getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Object createdById) {
        this.createdById = createdById;
    }

    public Object getCurrentPlaceOfWork() {
        return currentPlaceOfWork;
    }

    public void setCurrentPlaceOfWork(Object currentPlaceOfWork) {
        this.currentPlaceOfWork = currentPlaceOfWork;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public IsPlan getIsPlan() {
        return isPlan;
    }

    public void setIsPlan(IsPlan isPlan) {
        this.isPlan = isPlan;
    }

    public MedicalInfomration getMedicalInfomration() {
        return medicalInfomration;
    }

    public void setMedicalInfomration(MedicalInfomration medicalInfomration) {
        this.medicalInfomration = medicalInfomration;
    }

    public Object getHealthProfileStep() {
        return healthProfileStep;
    }

    public void setHealthProfileStep(Object healthProfileStep) {
        this.healthProfileStep = healthProfileStep;
    }

    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(firstName);
        dest.writeValue(lastName);
        dest.writeValue(fullName);
        dest.writeValue(email);
        dest.writeValue(gender);
        dest.writeValue(age);
        dest.writeValue(dateOfBirth);
        dest.writeValue(speciality);
        dest.writeValue(qualification);
        dest.writeValue(specialization);
        dest.writeList(files);
        dest.writeValue(aboutMe);
        dest.writeValue(contactNo);
        dest.writeValue(city);
        dest.writeValue(country);
        dest.writeValue(address);
        dest.writeValue(zipcode);
        dest.writeList(language);
        dest.writeValue(profileFile);
        dest.writeValue(stateId);
        dest.writeValue(availability);
        dest.writeList(reviews);
        dest.writeValue(rating);
        dest.writeValue(notificationCount);
        dest.writeValue(notificationSettings);
        dest.writeValue(emailSettings);
        dest.writeValue(createdById);
        dest.writeValue(currentPlaceOfWork);
        dest.writeValue(planId);
        dest.writeValue(isPlan);
        dest.writeValue(medicalInfomration);
        dest.writeValue(healthProfileStep);
        dest.writeList(symptoms);
    }

    public int describeContents() {
        return 0;
    }

}