package com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model;

public class UserModel {

    public String userType;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return userType;
    }
}
