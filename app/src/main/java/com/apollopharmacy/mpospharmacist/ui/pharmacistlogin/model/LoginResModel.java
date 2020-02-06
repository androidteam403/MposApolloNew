package com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResModel {


    @Expose
    @SerializedName("UserName")
    private String UserName;
    @Expose
    @SerializedName("UserId")
    private String UserId;
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
    @Expose
    @SerializedName("LastPasswordChanged")
    private String LastPasswordChanged;

    public String getUserName() {
        return UserName;
    }

    public String getUserId() {
        return UserId;
    }

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public String getLastPasswordChanged() {
        return LastPasswordChanged;
    }
}
