package com.apollopharmacy.mpospharmacist.ui.newadminloginsetup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminLoginReqModel {
    @Expose
    @SerializedName("USERID")
    private String userId;
    @Expose
    @SerializedName("PASSWORD")
    private String password;
    @Expose
    @SerializedName("USERTYPE")
    private String userType;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}