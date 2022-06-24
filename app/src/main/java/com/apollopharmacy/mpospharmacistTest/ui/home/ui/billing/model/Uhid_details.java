package com.apollopharmacy.mpospharmacistTest.ui.home.ui.billing.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Uhid_details implements Serializable {
    @SerializedName("age")
    private String age;
    @SerializedName("contact")
    private String contact;
    @SerializedName("dateofadmission")
    private String dateofadmission;
    @SerializedName("dischargedate")
    private String dischargedate;
    @SerializedName("doctorname")
    private String doctorname;
    @SerializedName("gender")
    private String gender;
    @SerializedName("inpatientno")
    private String inpatientno;
    @SerializedName("location")
    private String location;
    @SerializedName("patientname")
    private String patientname;
    @SerializedName("registration_number")
    private String registration_number;

    public String getAge() {
        return age;
    }

    public String getContact() {
        return contact;
    }

    public String getDateofadmission() {
        return dateofadmission;
    }

    public String getDischargedate() {
        return dischargedate;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public String getGender() {
        return gender;
    }

    public String getInpatientno() {
        return inpatientno;
    }

    public String getLocation() {
        return location;
    }

    public String getPatientname() {
        return patientname;
    }

    public String getRegistration_number() {
        return registration_number;
    }
}
