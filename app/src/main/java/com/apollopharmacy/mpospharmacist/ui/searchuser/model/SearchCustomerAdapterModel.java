package com.apollopharmacy.mpospharmacist.ui.searchuser.model;

public class SearchCustomerAdapterModel {
    private String name;
    private String gender;
    private String phoneNumber;
    private String dob;

    public SearchCustomerAdapterModel(String name,String gender,String phoneNumber,String dob) {
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        dob = dob;
    }
}
