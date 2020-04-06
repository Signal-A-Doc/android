package com.signalDoc_patient.model;

public class SelectSymptomsData {
    public String sub_symptom_id;
    public String sub_symptom_title;
    public String symptomId;
    public String symptomTitle;

    public String getSub_symptom_id() {
        return sub_symptom_id;
    }

    public void setSub_symptom_id(String sub_symptom_id) {
        this.sub_symptom_id = sub_symptom_id;
    }

    public String getSub_symptom_title() {
        return sub_symptom_title;
    }

    public void setSub_symptom_title(String sub_symptom_title) {
        this.sub_symptom_title = sub_symptom_title;
    }

    public String getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(String symptomId) {
        this.symptomId = symptomId;
    }

    public String getSymptomTitle() {
        return symptomTitle;
    }

    public void setSymptomTitle(String symptomTitle) {
        this.symptomTitle = symptomTitle;
    }
}
