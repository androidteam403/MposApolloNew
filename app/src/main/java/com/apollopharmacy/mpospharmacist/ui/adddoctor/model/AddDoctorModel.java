package com.apollopharmacy.mpospharmacist.ui.adddoctor.model;

public class AddDoctorModel {
    private String doctorRegNo;
    private String doctorName;
    private String speciality;
    private String placeOfPractice;
    private String address;
    private String phoneNumber;

    public String getDoctorRegNo() {
        return doctorRegNo;
    }

    public void setDoctorRegNo(String doctorRegNo) {
        this.doctorRegNo = doctorRegNo;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getPlaceOfPractice() {
        return placeOfPractice;
    }

    public void setPlaceOfPractice(String placeOfPractice) {
        this.placeOfPractice = placeOfPractice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
